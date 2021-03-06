package bot.service;

import bot.config.Props;
import bot.model.BotUserLite;
import bot.model.MaxId;
import bot.parser.Account;
import bot.parser.Parser;
import bot.repository.BotUserLiteRepository;
import bot.repository.BotUserRepository;
import bot.repository.MaxIdRepository;
import bot.utils.BotUserCreator;
import bot.utils.BotUserLiteExcluder;
import lombok.extern.log4j.Log4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.*;
import org.brunocvcunha.instagram4j.requests.payload.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Log4j
public class BotUserService {

    @Autowired
    private final BotUserRepository botUserRepository;

    @Autowired
    private final BotUserLiteRepository botUserLiteRepository;

    @Autowired
    private final MaxIdRepository maxIdRepository;


    private final LinkedList<Instagram4j> instagramQueue = new LinkedList<>();

    private final LinkedList<List<BotUserLite>> botUserLiteListsQueue = new LinkedList<>();

    boolean flag = false;

    private Map<String, BotUserCreator> botUserCreatorMap = new HashMap<>();


    public BotUserService(BotUserRepository botUserRepository, BotUserLiteRepository botUserLiteRepository, MaxIdRepository maxIdRepository) {
        this.botUserRepository = botUserRepository;
        this.botUserLiteRepository = botUserLiteRepository;
        this.maxIdRepository = maxIdRepository;
    }

//    public String login() throws Exception {
//        List<Account> accountList = Parser.getAccounts();
//        if (counter != accountList.size()) {
//            // Setup
//            Instagram4j instagram = getInstagram4j();
//
//            instagram = Instagram4j.builder()
//                    .username(accountList.get(counter).getUserName())
//                    .password(accountList.get(counter).getInstagramPassword())
//                    .build();
//            instagram.setup();
//
//            // Get login response
//            InstagramLoginResult instagramLoginResult = instagram.login();
//            // Check login response
//            checkInstagramLoginResult(instagram, instagramLoginResult, true);
//            instagramQueue.add(instagram);
//            counter++;
//            String NOTIFICATION_ = "Instagram4j created";
//            return NOTIFICATION_ + " " + accountList.get(counter - 1).getUserName();
//        }
//        createInstagram4j();
//
//        return "There is no Instagram4j";
//    }
//


    public void login(int withProxy) throws Exception {
        List<Account> accountList = Parser.getAccounts();
        Account account = accountList.get(0);

        HttpHost proxy = new HttpHost(account.getProxyAddress(), account.getPort(), "http");
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(new AuthScope(AuthScope.ANY),
                new UsernamePasswordCredentials(account.getProxyUserName(), account.getProxyPassword()));

        Instagram4j instagram = Instagram4j.builder()
                .username(account.getUserName())
                .password(account.getInstagramPassword())
                .proxy(proxy)
                .credentialsProvider(credentialsProvider)
                .build();

        if (withProxy == 0) {
            instagram = Instagram4j.builder()
                    .username(account.getUserName())
                    .password(account.getInstagramPassword())
                    .build();
        }
        instagram.setup();
        // Get login response
        InstagramLoginResult instagramLoginResult = instagram.login();
        // Check login response
        checkInstagramLoginResult(instagram, instagramLoginResult, true);
        instagramQueue.add(instagram);
    }


    public void getFollowersList(String maxId) {

        InstagramGetUserFollowersResult followersResult = null;

        List<InstagramUserSummary> followers = new ArrayList<>();

        if (maxIdRepository.findLastMaxId() != null && maxId.equals(""))
            maxId = maxIdRepository.findLastMaxId().getMaxId();
        int followersListOldSize;

        Instagram4j instagram4j = getInstagram4j();


        while (followersResult == null || maxId != null && instagram4j.isLoggedIn()) {
            followersListOldSize = followers.size();
            try {
                log.info(instagram4j.getUsername() + " followers.size: " + followers.size());
                MaxId maxId1 = MaxId.builder()
                        .maxId(maxId)
                        .createdTs(System.currentTimeMillis())
                        .build();
                maxIdRepository.save(maxId1);

                try {
                    followersResult = instagram4j.sendRequest(new InstagramGetUserFollowersRequest(Props.getUserIdForScanFollowers(), maxId));
                } catch (NullPointerException nullPointerException) {
                    System.out.println(" Try another instagram4j");
                    break;
                }

//                Thread.sleep(1500);
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

                    if (followers.size() >= Props.getFollowersSize() || followersListOldSize - followers.size() == 0)
                        break;
                }
            } catch (IOException e) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        instagramQueue.add(instagram4j);

    }


    public void excludeBotUserLiteFromCreatingList() {
        BotUserLiteExcluder.excludeBotUserLiteFromCreatingList();
    }

    private Instagram4j getInstagram4j() {
        Instagram4j instagram;
        if (instagramQueue.size() != 0) {
            instagram = instagramQueue.getFirst();
            instagramQueue.remove();
            return instagram;
        }
        return null;
    }


    public String createBotUsers() {
        List<BotUserLite> botUserLiteList;

        if (botUserLiteListsQueue.size() != 0) {
            botUserLiteList = botUserLiteListsQueue.getFirst();
            botUserLiteListsQueue.remove();


            if (instagramQueue.size() != 0) {
                BotUserCreator botUserCreator = new BotUserCreator(instagramQueue.getFirst(), botUserLiteList, 100 + Math.round(Math.random() * 50));
                botUserCreator.start();
                instagramQueue.remove();
                botUserCreatorMap.put(botUserCreator.getName(), botUserCreator);
            } else {
                return "instagramQueue is empty";
            }
        } else {
            return "botUserLiteListsQueue is empty";
        }
        return null;
    }

    public boolean interruptThread(String threadName) {
        BotUserCreator botUserCreator = botUserCreatorMap.get(threadName);
        botUserCreator.interrupt();
        return botUserCreator.isInterrupted();

    }

    public String getThreadsMap() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, BotUserCreator> entry : botUserCreatorMap.entrySet()) {
            stringBuilder
                    .append(entry.getKey())
                    .append(" : ")
                    .append(entry.getValue().getInstagram().getUsername())
                    .append(" : LoggedIn: ")
                    .append(entry.getValue().getInstagram().isLoggedIn())
                    .append(" : Created ")
                    .append(entry.getValue().getCreatingCount())
                    .append(" : Next request in ")
                    .append(entry.getValue().getTimer())
                    .append(" : Waiting times ")
                    .append(entry.getValue().getWaitingTimes())
                    .append("\n");
        }


        return stringBuilder.toString();
    }

    public String createBotUserLiteLists() {
        List<BotUserLite> botUserLiteList = BotUserLiteExcluder.excludeBotUserLiteFromCreatingList();
        int totalDotUsersCount = botUserLiteList.size();

        if (totalDotUsersCount > 1000 && !flag) {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("Total users = ").append(botUserLiteList.size());

            int partitionSize = (int) Props.getPartitionSize();
            for (int i = 0; i < botUserLiteList.size(); i += partitionSize) {
                botUserLiteListsQueue.add(botUserLiteList.subList(i,
                        Math.min(i + partitionSize, botUserLiteList.size())));
            }
            botUserLiteListsQueue.forEach(l -> {
                stringBuilder.append("\n list size =").append(l.size());
            });
            flag = true;
            return stringBuilder.toString();

        } else if (!flag) {
            botUserLiteListsQueue.add(botUserLiteList);
            flag = true;
            return "Total users =" + botUserLiteList.size();
        }
        return "BotUserLiteLists was created";
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
            System.out.println("???Login success.");
            System.out.println(instagramLoginResult.getLogged_in_user());
        } else if (Objects.equals(instagramLoginResult.getStatus(), "ok")) {
            // Logged in user not exists
            System.out.println("???Logged in user not exists.");

            // TODO
        } else if (Objects.equals(instagramLoginResult.getStatus(), "fail")) {
            // Login failed

            // Check error type
            if (Objects.equals(instagramLoginResult.getError_type(), "checkpoint_challenge_required")) {
                System.out.println("???Challenge URL : " + instagramLoginResult.getChallenge().getUrl());

                // If do re-authentication
                if (doReAuthentication) {
                    // Get challenge URL
                    String challengeUrl = instagramLoginResult.getChallenge().getApi_path().substring(1);

                    // Reset
                    String resetUrl = challengeUrl.replace("challenge", "challenge/reset");
                    InstagramGetChallengeResult getChallengeResult = instagram4j
                            .sendRequest(new InstagramResetChallengeRequest(resetUrl));
                    System.out.println("???Reset result : " + getChallengeResult);

                    // if "close"
                    if (Objects.equals(getChallengeResult.getAction(), "close")) {
                        // Get challenge response
                        getChallengeResult = instagram4j
                                .sendRequest(new InstagramGetChallengeRequest(challengeUrl));
                        System.out.println("???Challenge response : " + getChallengeResult);
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
                            System.out.println("???Challenge was closed : " + postChallengeResult);

                            // End
                            return;
                        }

                        // Security code has been sent
                        System.out.println("???Security code has been sent : " + postChallengeResult);

                        // Please input Security code
                        System.out.println("Please input Security code");
                        String securityCode = null;
                        boolean isValid = false;
                        while (!isValid) {
                            Scanner scanner = new Scanner(System.in);
                            String validate = scanner.nextLine();
                            if (validate.length() == 6) {
                                securityCode = validate;
                                isValid = true;
                            } else {
                                System.out.println("Please input Security code");
                            }

                        }

                        // Send security code
                        InstagramLoginResult securityCodeInstagramLoginResult = instagram4j
                                .sendRequest(new InstagramSendSecurityCodeRequest(challengeUrl, securityCode));

                        // Check login response
                        checkInstagramLoginResult(instagram4j, securityCodeInstagramLoginResult, false);
                    } else if (Objects.equals(getChallengeResult.getStep_name(), "verify_email")) {
                        // Security code has been sent to E-mail
                        System.out.println("???Security code has been sent to E-mail");

                        // TODO
                    } else if (Objects.equals(getChallengeResult.getStep_name(), "verify_code")) {
                        // Security code has been sent to phone
                        System.out.println("???Security code has been sent to phone");

                        // TODO
                    } else if (Objects.equals(getChallengeResult.getStep_name(), "submit_phone")) {
                        // Unknown
                        System.out.println("???Unknown.");

                        // TODO
                    } else if (Objects.equals(getChallengeResult.getStep_name(), "delta_login_review")) {
                        // Maybe showing security confirmation view?
                        System.out.println("???Maybe showing security confirmation view?");

                        // FIXME Send request with choice
                        InstagramSelectVerifyMethodResult instagramSelectVerifyMethodResult = instagram4j
                                .sendRequest(new InstagramSelectVerifyMethodRequest(challengeUrl,
                                        getChallengeResult.getStep_data().getChoice()));
                        System.out.println(instagramSelectVerifyMethodResult);

                        // TODO
                    } else if (Objects.equals(getChallengeResult.getStep_name(), "change_password")) {
                        // Change password needed
                        System.out.println("???Change password needed.");
                    } else if (Objects.equals(getChallengeResult.getAction(), "close")) {
                        // Maybe already challenge closed at other device
                        System.out.println("???Maybe already challenge closed at other device.");

                        // TODO
                    } else {
                        // TODO Other
                        System.out.println("???Other.");
                    }
                }
            } else if (Objects.equals(instagramLoginResult.getError_type(), "bad_password")) {
                System.out.println("???Bad password.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getError_type(), "rate_limit_error")) {
                System.out.println("???Too many request.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getError_type(), "invalid_parameters")) {
                System.out.println("???Invalid parameter.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getError_type(), "needs_upgrade")) {
                System.out.println("???App upgrade needed.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getError_type(), "sentry_block")) {
                System.out.println("???Sentry block.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getError_type(), "inactive user")) {
                System.out.println("???Inactive user.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getError_type(), "unusable_password")) {
                System.out.println("???Unusable password.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (instagramLoginResult.getTwo_factor_info() != null) {
                System.out.println("???Two factor authentication needed.");
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
                System.out.println("???Invalid security code.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getMessage(),
                    "This code has expired. Go back to request a new one.")) {
                System.out.println("???Security code has expired.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (instagramLoginResult.getChallenge() != null) {
                System.out.println("???Challenge : " + instagramLoginResult.getChallenge());
                System.out.println(instagramLoginResult.getMessage());
                if (instagramLoginResult.getChallenge().getLock() != null
                        && instagramLoginResult.getChallenge().getLock()) {
                    // Login locked
                    System.out.println("???Login locked.");
                } else {
                    // Logged in user exists, or maybe showing security code
                    // view on other device
                    System.out.println("???Logged in user exists, or maybe showing security code view on other device.");
                }
            } else {
                System.out.println("???Unknown error : " + instagramLoginResult.getError_type());
                System.out.println(instagramLoginResult.getMessage());
            }
        } else {
            System.out.println("???Unknown status : " + instagramLoginResult.getStatus());
            System.out.println(instagramLoginResult.getMessage());
        }
    }


}