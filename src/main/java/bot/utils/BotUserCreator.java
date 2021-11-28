package bot.utils;

import bot.config.ApplicationContextProvider;
import bot.config.Props;
import bot.model.BotUser;
import bot.model.BotUserLite;
import bot.repository.BotUserRepository;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;

import java.io.IOException;
import java.util.List;

public class BotUserCreator extends Thread {


    private final Instagram4j instagram;
    private final List<BotUserLite> botUserLiteList;
    private final long waiting;


    public BotUserCreator(Instagram4j instagram, List<BotUserLite> botUserLiteList, long waiting) {
        this.instagram = instagram;
        this.botUserLiteList = botUserLiteList;
        this.waiting = waiting;
    }

    @Override
    public void run() {
        int usersCount = 0;
        int waitingTimes = 0;
        String NOT_FOUND = "User not found";

        for (BotUserLite botUserLite : botUserLiteList) {
//            try {
//                Thread.sleep(1500 + Math.round(Math.random() * 5000));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            if (waitingTimes == Props.getWaitingTimes()) break;

            InstagramSearchUsernameResult instagramSearchUsernameResult = null;
            try {
                instagramSearchUsernameResult = instagram.sendRequest(new InstagramSearchUsernameRequest(botUserLite.username));
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert instagramSearchUsernameResult != null;
            if (instagramSearchUsernameResult.getStatus().equals("ok")) {
                InstagramUser instagramUser = instagramSearchUsernameResult.getUser();

                BotUser botUser = BotUser.builder()
                        .username(checkStringLength(instagramUser.username))
                        .is_private(instagramUser.is_private)
                        .is_verified(instagramUser.is_verified)
                        .has_chaining(instagramUser.has_chaining)
                        .is_business(instagramUser.is_business)
                        .media_count(instagramUser.media_count)
                        .profile_pic_id(checkStringLength(instagramUser.profile_pic_id))
                        .external_url(checkStringLength(instagramUser.external_url))
                        .full_name(checkStringLength(instagramUser.full_name))
                        .has_biography_translation(instagramUser.has_biography_translation)
                        .has_anonymous_profile_picture(instagramUser.has_anonymous_profile_picture)
                        .is_favorite(instagramUser.is_favorite)
                        .public_phone_country_code(checkStringLength(instagramUser.public_phone_country_code))
                        .public_phone_number(checkStringLength(instagramUser.public_phone_number))
                        .public_email(checkStringLength(instagramUser.public_email))
                        .pk(instagramUser.pk)
                        .geo_media_count(instagramUser.geo_media_count)
                        .usertags_count(instagramUser.usertags_count)
                        .address_street(checkStringLength(instagramUser.address_street))
                        .city_name(checkStringLength(instagramUser.city_name))
                        .zip(checkStringLength(instagramUser.zip))
                        .direct_messaging(checkStringLength(instagramUser.direct_messaging))
                        .business_contact_method(checkStringLength(instagramUser.business_contact_method))
                        .biography(instagramUser.biography.length() > 1999 ? instagramUser.biography.substring(0, 1999) : instagramUser.biography)
                        .follower_count(instagramUser.follower_count)
                        .following_count(instagramUser.following_count)
                        .latitude(instagramUser.latitude)
                        .longitude(instagramUser.longitude)
                        .category(checkStringLength(instagramUser.category))
                        .build();

                ApplicationContextProvider.getApplicationContext().getBean(BotUserRepository.class).save(botUser);

                usersCount++;
                System.out.println(instagram.getUsername() + " сreated: " + usersCount);
            } else if (instagramSearchUsernameResult.getMessage() != null && instagramSearchUsernameResult.getMessage().equals(NOT_FOUND)) {
                BotUser botUser = BotUser.builder()
                        .username(botUserLite.username)
                        .profile_pic_id(NOT_FOUND)
                        .external_url(NOT_FOUND)
                        .full_name(NOT_FOUND)
                        .category(checkStringLength(NOT_FOUND))
                        .build();

                ApplicationContextProvider.getApplicationContext().getBean(BotUserRepository.class).save(botUser);
                System.out.println(botUserLite.username + " " + NOT_FOUND);
            } else if (waitingTimes < Math.round(waitingTimes / 2)) {
                waitingTimes++;
                System.out.println(instagram.getUsername() + " waitingTimes " + waitingTimes + " " + currentThread().getName());
                timer(waiting);
            } else {
                waitingTimes++;
                System.out.println(instagram.getUsername() + " waitingTimes " + waitingTimes + " " + currentThread().getName());
                timer(waiting * 5);
            }
        }
    }


    private static void timer(long i) {
        while (i > 0) {
            if (i % 10 == 0)
                System.out.println("Next request in: " + i + " seconds " + currentThread().getName());
            try {
                i--;
                Thread.sleep(1000L);    // 1000L = 1000ms = 1 second
            } catch (InterruptedException e) {
                //I don't think you need to do anything for your particular problem
            }
        }
    }

    private static String checkStringLength(String string) {
        if (string != null)
            return string.length() > 250 ? string.substring(0, 250) : string;
        return null;
    }

}
