package bot.service;

import bot.model.BotUser;
import bot.repository.BotUserRepository;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BotUserService {

    @Autowired
    private final BotUserRepository botUserRepository;

    private String maxId = null;
    Instagram4j instagram = Instagram4j.builder()
            .username(null)
            .password(null)
            .build();
    ;

    public BotUserService(BotUserRepository botUserRepository) {
        this.botUserRepository = botUserRepository;
    }

    public String login(String name, String pass) throws IOException, InterruptedException {
        instagram.setUsername(name);
        instagram.setPassword(pass);
        instagram.setup();
        instagram.login();
        return instagram.toString();
    }

    public String getFollowersList(String userName) throws IOException, InterruptedException {

        String s = instagram.getUsername();
        String ss = instagram.getPassword();
        InstagramSearchUsernameResult usernameResult = instagram.sendRequest(new InstagramSearchUsernameRequest(userName));

        InstagramGetUserFollowersResult followersResult = null;

        List<InstagramUserSummary> followers = new ArrayList<>();

//        while (followersResult == null || maxId != null) {
        try {
            followersResult = instagram.sendRequest(new
                    InstagramGetUserFollowersRequest(usernameResult.getUser().getPk(), maxId));
            if (followersResult != null) {
                if (followersResult.getStatus().equals("ok")) {
                    maxId = followersResult.getNext_max_id();
                    followers.addAll(followersResult.getUsers());
                } else {
                    System.out.println("maxId = " + maxId);
                }
            }
        } catch (IOException e) {
            Thread.sleep(5000);
        }
//        }

        for (InstagramUserSummary user : followers) {
            InstagramUser instagramUser = instagram.sendRequest(new InstagramSearchUsernameRequest(user.getUsername())).getUser();

            BotUser botUser = new BotUser();
            botUser.setUsername(instagramUser.username);
            botUser.setFullName(instagramUser.full_name);
            botUser.setPk(instagramUser.pk);
            botUser.setPrivate(instagramUser.is_private);
            botUser.setVerified(instagramUser.is_verified);
            botUser.setFollowersCount(instagramUser.follower_count);
            botUser.setBio(instagramUser.biography);
            botUser.setSubscribeCount(instagramUser.following_count);
            botUser.setPostCount(instagramUser.media_count);
        }
        return s + " " + ss;
    }

    public void createBotUser(BotUser user) {
        botUserRepository.save(user);
    }

    public List<BotUser> findAll() {
        return botUserRepository.findAll();
    }

    public BotUser findById(String userId) {
        return botUserRepository.findById(userId).orElse(null);
    }

    public static long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

}
