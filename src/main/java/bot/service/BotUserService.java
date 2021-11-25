package bot.service;

import bot.model.BotUser;
import bot.model.BotUserLite;
import bot.model.MaxId;
import bot.repository.BotUserLiteRepository;
import bot.repository.BotUserRepository;
import bot.repository.MaxIdRepository;
import com.github.instagram4j.instagram4j.IGClient;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.*;
import org.brunocvcunha.instagram4j.requests.payload.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
public class BotUserService {

    @Autowired
    private final BotUserRepository botUserRepository;

    @Autowired
    private final BotUserLiteRepository botUserLiteRepository;

    @Autowired
    private final MaxIdRepository maxIdRepository;

    private String maxId = null;

    private final String NOT_FOUND = "User not found";

    private Instagram4j instagram = Instagram4j.builder()
            .username(null)
            .password(null)
            .build();


    public BotUserService(BotUserRepository botUserRepository, BotUserLiteRepository botUserLiteRepository, MaxIdRepository maxIdRepository) {
        this.botUserRepository = botUserRepository;
        this.botUserLiteRepository = botUserLiteRepository;
        this.maxIdRepository = maxIdRepository;
    }

    public void login(String name, String pass) throws Exception {

        // Setup
        instagram = Instagram4j.builder()
                .username(name)
                .password(pass)
                .build();
        instagram.setup();

        // Get login response
        InstagramLoginResult instagramLoginResult = instagram.login();

        // Check login response
        checkInstagramLoginResult(instagram, instagramLoginResult, true);



    }

    public void getFollowersList(String userName) throws IOException, InterruptedException {
        InstagramSearchUsernameResult usernameResult = instagram.sendRequest(new InstagramSearchUsernameRequest(userName));

        InstagramGetUserFollowersResult followersResult = null;

        List<InstagramUserSummary> followers = new ArrayList<>();

        while (followersResult == null || maxId != null) {
            try {
                System.out.println("followers.size: " + followers.size());
                MaxId maxId1 = MaxId.builder()
                        .maxId(maxId)
                        .createdTs(System.currentTimeMillis())
                        .build();

                maxIdRepository.save(maxId1);

                followersResult = instagram.sendRequest(new
                        InstagramGetUserFollowersRequest(usernameResult.getUser().getPk(), maxId));

                Thread.sleep(1500);
                if (followersResult != null) {
                    if (followersResult.getStatus().equals("ok")) {
                        maxId = followersResult.getNext_max_id();
                        followers.addAll(followersResult.getUsers());

                        for (InstagramUserSummary user : followersResult.getUsers()) {
                            BotUserLite botUserLite = BotUserLite.builder()
                                    .username(user.username)
                                    .pk(user.pk)
                                    .build();

                            botUserLiteRepository.save(botUserLite);
                        }

                    }
                    if (followers.size() >= 50000) break;
                }
            } catch (IOException e) {
                Thread.sleep(5000);
            }
        }

    }


    public void createBotUsers() throws IOException, InterruptedException {

        int usersCount = 0;
        int waitingTimes = 0;

        List<BotUser> botUserList = botUserRepository.findAll();
        if (botUserList.size() > 0)

            for (BotUser botUser : botUserList) {
                if (botUserLiteRepository.existsById(botUser.getUsername()))
                    botUserLiteRepository.deleteById(botUser.getUsername());
            }

        List<BotUserLite> botUserLiteList = botUserLiteRepository.findAll();


        for (BotUserLite botUserLite : botUserLiteList) {

            InstagramSearchUsernameResult instagramSearchUsernameResult = instagram.sendRequest(new InstagramSearchUsernameRequest(botUserLite.username));

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
                        .biography(instagramUser.biography.length() > 1999 ? instagramUser.biography.substring(0, 250) : instagramUser.biography)
                        .follower_count(instagramUser.follower_count)
                        .following_count(instagramUser.following_count)
                        .latitude(instagramUser.latitude)
                        .longitude(instagramUser.longitude)
                        .category(checkStringLength(instagramUser.category))
                        .build();

                botUserRepository.save(botUser);
                botUserLiteRepository.deleteById(botUser.getUsername());

                usersCount++;
                System.out.println("Created: " + usersCount);
            } else if (instagramSearchUsernameResult.getMessage() != null && instagramSearchUsernameResult.getMessage().equals(NOT_FOUND)) {
                BotUser botUser = BotUser.builder()
                        .username(botUserLite.username)
                        .profile_pic_id(NOT_FOUND)
                        .external_url(NOT_FOUND)
                        .full_name(NOT_FOUND)
                        .category(checkStringLength(NOT_FOUND))
                        .build();

                botUserRepository.save(botUser);
                System.out.println(botUserLite.username + " " + NOT_FOUND);
            } else {
                waitingTimes++;
                timer(125);
            }
        }
    }

    private void timer(int i) {
               while (i > 0) {
            System.out.println("Remaining: " + i + " seconds");
            try {
                i--;
                Thread.sleep(1000L);    // 1000L = 1000ms = 1 second
            } catch (InterruptedException e) {
                //I don't think you need to do anything for your particular problem
            }
        }
    }

    private String checkStringLength(String string) {
        if (string != null)
            return string.length() > 250 ? string.substring(0, 250) : string;
        return null;
    }


    /**
     * Check login response.
     *
     * @param instagram4j
     * @param instagramLoginResult
     * @param doReAuthentication
     * @throws Exception
     */
    public static void checkInstagramLoginResult(Instagram4j instagram4j, InstagramLoginResult instagramLoginResult,
                                                 boolean doReAuthentication) throws Exception {
        if (Objects.equals(instagramLoginResult.getStatus(), "ok")
                && instagramLoginResult.getLogged_in_user() != null) {
            // Login success
            System.out.println("■Login success.");
            System.out.println(instagramLoginResult.getLogged_in_user());
        } else if (Objects.equals(instagramLoginResult.getStatus(), "ok")) {
            // Logged in user not exists
            System.out.println("■Logged in user not exists.");

            // TODO
        } else if (Objects.equals(instagramLoginResult.getStatus(), "fail")) {
            // Login failed

            // Check error type
            if (Objects.equals(instagramLoginResult.getError_type(), "checkpoint_challenge_required")) {
                System.out.println("■Challenge URL : " + instagramLoginResult.getChallenge().getUrl());

                // If do re-authentication
                if (doReAuthentication) {
                    // Get challenge URL
                    String challengeUrl = instagramLoginResult.getChallenge().getApi_path().substring(1);

                    // Reset
                    String resetUrl = challengeUrl.replace("challenge", "challenge/reset");
                    InstagramGetChallengeResult getChallengeResult = instagram4j
                            .sendRequest(new InstagramResetChallengeRequest(resetUrl));
                    System.out.println("■Reset result : " + getChallengeResult);

                    // if "close"
                    if (Objects.equals(getChallengeResult.getAction(), "close")) {
                        // Get challenge response
                        getChallengeResult = instagram4j
                                .sendRequest(new InstagramGetChallengeRequest(challengeUrl));
                        System.out.println("■Challenge response : " + getChallengeResult);
                    }

                    // Check step name
                    if (Objects.equals(getChallengeResult.getStep_name(), "select_verify_method")) {
                        // Select verify method

                        // Get select verify method result
                        InstagramSelectVerifyMethodResult postChallengeResult = instagram4j
                                .sendRequest(new InstagramSelectVerifyMethodRequest(challengeUrl,
                                        getChallengeResult.getStep_data().getChoice()));

                        // If "close"
                        if (Objects.equals(postChallengeResult.getAction(), "close")) {
                            // Challenge was closed
                            System.out.println("■Challenge was closed : " + postChallengeResult);

                            // End
                            return;
                        }

                        // Security code has been sent
                        System.out.println("■Security code has been sent : " + postChallengeResult);

                        // Please input Security code
                        System.out.println("Please input Security code");
                        String securityCode = null;
                        try (Scanner scanner = new Scanner(System.in)) {
                            securityCode = scanner.nextLine();
                        }

                        // Send security code
                        InstagramLoginResult securityCodeInstagramLoginResult = instagram4j
                                .sendRequest(new InstagramSendSecurityCodeRequest(challengeUrl, securityCode));

                        // Check login response
                        checkInstagramLoginResult(instagram4j, securityCodeInstagramLoginResult, false);
                    } else if (Objects.equals(getChallengeResult.getStep_name(), "verify_email")) {
                        // Security code has been sent to E-mail
                        System.out.println("■Security code has been sent to E-mail");

                        // TODO
                    } else if (Objects.equals(getChallengeResult.getStep_name(), "verify_code")) {
                        // Security code has been sent to phone
                        System.out.println("■Security code has been sent to phone");

                        // TODO
                    } else if (Objects.equals(getChallengeResult.getStep_name(), "submit_phone")) {
                        // Unknown
                        System.out.println("■Unknown.");

                        // TODO
                    } else if (Objects.equals(getChallengeResult.getStep_name(), "delta_login_review")) {
                        // Maybe showing security confirmation view?
                        System.out.println("■Maybe showing security confirmation view?");

                        // FIXME Send request with choice
                        InstagramSelectVerifyMethodResult instagramSelectVerifyMethodResult = instagram4j
                                .sendRequest(new InstagramSelectVerifyMethodRequest(challengeUrl,
                                        getChallengeResult.getStep_data().getChoice()));
                        System.out.println(instagramSelectVerifyMethodResult);

                        // TODO
                    } else if (Objects.equals(getChallengeResult.getStep_name(), "change_password")) {
                        // Change password needed
                        System.out.println("■Change password needed.");
                    } else if (Objects.equals(getChallengeResult.getAction(), "close")) {
                        // Maybe already challenge closed at other device
                        System.out.println("■Maybe already challenge closed at other device.");

                        // TODO
                    } else {
                        // TODO Other
                        System.out.println("■Other.");
                    }
                }
            } else if (Objects.equals(instagramLoginResult.getError_type(), "bad_password")) {
                System.out.println("■Bad password.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getError_type(), "rate_limit_error")) {
                System.out.println("■Too many request.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getError_type(), "invalid_parameters")) {
                System.out.println("■Invalid parameter.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getError_type(), "needs_upgrade")) {
                System.out.println("■App upgrade needed.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getError_type(), "sentry_block")) {
                System.out.println("■Sentry block.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getError_type(), "inactive user")) {
                System.out.println("■Inactive user.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getError_type(), "unusable_password")) {
                System.out.println("■Unusable password.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (instagramLoginResult.getTwo_factor_info() != null) {
                System.out.println("■Two factor authentication needed.");
                System.out.println(instagramLoginResult.getMessage());

                // If do re-authentication
//                if (doReAuthentication) {
//                    // Two factor authentication
//                    InstagramLoginResult twoFactorInstagramLoginResult = instagram4j.login(VERIFICATION_CODE);
//
//                    // Check login result
//                    checkInstagramLoginResult(instagram4j, twoFactorInstagramLoginResult, false);
//                }
            } else if (Objects.equals(instagramLoginResult.getMessage(),
                    "Please check the code we sent you and try again.")) {
                System.out.println("■Invalid security code.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getMessage(),
                    "This code has expired. Go back to request a new one.")) {
                System.out.println("■Security code has expired.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (instagramLoginResult.getChallenge() != null) {
                System.out.println("■Challenge : " + instagramLoginResult.getChallenge());
                System.out.println(instagramLoginResult.getMessage());
                if (instagramLoginResult.getChallenge().getLock() != null
                        && instagramLoginResult.getChallenge().getLock()) {
                    // Login locked
                    System.out.println("■Login locked.");
                } else {
                    // Logged in user exists, or maybe showing security code
                    // view on other device
                    System.out.println("■Logged in user exists, or maybe showing security code view on other device.");
                }
            } else {
                System.out.println("■Unknown error : " + instagramLoginResult.getError_type());
                System.out.println(instagramLoginResult.getMessage());
            }
        } else {
            System.out.println("■Unknown status : " + instagramLoginResult.getStatus());
            System.out.println(instagramLoginResult.getMessage());
        }
    }


}
