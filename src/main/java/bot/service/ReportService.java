package bot.service;

import bot.config.ApplicationContextProvider;
import bot.config.Props;
import bot.repository.BotUserRepository;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.springframework.stereotype.Service;


@Service
public class ReportService {

    private static int findAll;
    private final int LIMIT_BOT_USER_LIST = 200000;
    public String getReport() {
        BotUserRepository botUserRepository = ApplicationContextProvider.getApplicationContext().getBean(BotUserRepository.class);

        findAll = botUserRepository.findAll().size();

        int findByNoPublications = botUserRepository.findByNoPublications(LIMIT_BOT_USER_LIST).size();
        int findByPublicationsOver1000 = botUserRepository.findByPublicationsOver1000(LIMIT_BOT_USER_LIST).size();
        int findByNoAvatar = botUserRepository.findByNoAvatar(LIMIT_BOT_USER_LIST).size();
        int findBySubscriptionsOver1000 = botUserRepository.findBySubscriptionsOver1000(LIMIT_BOT_USER_LIST).size();
        int findBySubscriptionsOver2000 = botUserRepository.findBySubscriptionsOver2000(LIMIT_BOT_USER_LIST).size();
        int findBySubscriptionsOver3000 = botUserRepository.findBySubscriptionsOver3000(LIMIT_BOT_USER_LIST).size();
        int findBySubscribersOver1000 = botUserRepository.findBySubscribersOver1000(LIMIT_BOT_USER_LIST).size();
        int findBySubscribersOver2000 = botUserRepository.findBySubscribersOver2000(LIMIT_BOT_USER_LIST).size();
        int findBySubscribersOver3000 = botUserRepository.findBySubscribersOver3000(LIMIT_BOT_USER_LIST).size();
        int findBySubscriptionsOverSubscribers = botUserRepository.findBySubscriptionsOverSubscribers(LIMIT_BOT_USER_LIST).size();
        int findBySubscribersOverSubscriptions = botUserRepository.findBySubscribersOverSubscriptions(LIMIT_BOT_USER_LIST).size();
        int findByIsBusiness = botUserRepository.findByIsBusiness(LIMIT_BOT_USER_LIST).size();
        int findByIsPrivate = botUserRepository.findByIsPrivate(LIMIT_BOT_USER_LIST).size();
        int findByPhoneNumberSet = botUserRepository.findByPhoneNumberSet(LIMIT_BOT_USER_LIST).size();
        int findByLinkSet = botUserRepository.findByLinkSet(LIMIT_BOT_USER_LIST).size();
        int findByEmailSet = botUserRepository.findByEmailSet(LIMIT_BOT_USER_LIST).size();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("???????????????????? ??????????????????????.................").append(findAll).append("\n")
                .append("???? ??????:\n")
                .append("\n")
                .append("?????????????????????? ????????????????????................. ").append(getProportion(findByNoPublications)).append("%\n")
                .append("???????????????????? > 1000...................... ").append(getProportion(findByPublicationsOver1000)).append("%\n")
                .append("?????????????????????? ????????????..................... ").append(getProportion(findByNoAvatar)).append("%\n")
                .append("???????????????? ?????????? 1000.................... ").append(getProportion(findBySubscriptionsOver1000)).append("%\n")
                .append("???????????????? ?????????? 2000.................... ").append(getProportion(findBySubscriptionsOver2000)).append("%\n")
                .append("???????????????? ?????????? 3000.................... ").append(getProportion(findBySubscriptionsOver3000)).append("%\n")
                .append("?????????????????????? ?????????? 1000................. ").append(getProportion(findBySubscribersOver1000)).append("%\n")
                .append("?????????????????????? ?????????? 2000................. ").append(getProportion(findBySubscribersOver2000)).append("%\n")
                .append("?????????????????????? ?????????? 3000................. ").append(getProportion(findBySubscribersOver3000)).append("%\n")
                .append("???????????????? > ??????????????????????................. ").append(getProportion(findBySubscriptionsOverSubscribers)).append("%\n")
                .append("???????????????? < ??????????????????????................. ").append(getProportion(findBySubscribersOverSubscriptions)).append("%\n")
                .append("?????????????? ??????????????........................ ").append(getProportion(findByIsBusiness)).append("%\n")
                .append("?????????????????? ??????????????...................... ").append(getProportion(findByIsPrivate)).append("%\n")
                .append("???????????? ?????????? ????????????????.................. ").append(getProportion(findByPhoneNumberSet)).append("%\n")
                .append("???????????? ???? ???????? ?? ????????????????.............. ").append(getProportion(findByLinkSet)).append("%\n")
                .append("???????????? email........................... ").append(getProportion(findByEmailSet)).append("%\n")
                .append("...");


        System.out.println(Props.getUserIdForScanFollowers());
        System.out.println(Props.getWaitingTimes());
        System.out.println(Props.getPartitionSize());
        System.out.println(Props.getFollowersSize());

        return stringBuilder.toString();
    }

    private int getProportion(int findBy) {
        return Math.round((findBy * 100) / findAll);
    }



}