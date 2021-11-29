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

    public String getReport() {
        BotUserRepository botUserRepository = ApplicationContextProvider.getApplicationContext().getBean(BotUserRepository.class);

        findAll = botUserRepository.findAll().size();

        int findByNoPublications = botUserRepository.findByNoPublications().size();
        int findByPublicationsOver1000 = botUserRepository.findByPublicationsOver1000().size();
        int findByNoAvatar = botUserRepository.findByNoAvatar().size();
        int findBySubscriptionsOver1000 = botUserRepository.findBySubscriptionsOver1000().size();
        int findBySubscriptionsOver2000 = botUserRepository.findBySubscriptionsOver2000().size();
        int findBySubscriptionsOver3000 = botUserRepository.findBySubscriptionsOver3000().size();
        int findBySubscribersOver1000 = botUserRepository.findBySubscribersOver1000().size();
        int findBySubscribersOver2000 = botUserRepository.findBySubscribersOver2000().size();
        int findBySubscribersOver3000 = botUserRepository.findBySubscribersOver3000().size();
        int findBySubscriptionsOverSubscribers = botUserRepository.findBySubscriptionsOverSubscribers().size();
        int findBySubscribersOverSubscriptions = botUserRepository.findBySubscribersOverSubscriptions().size();

        int findByIsBusiness = botUserRepository.findByIsBusiness().size();
        int findByIsPrivate = botUserRepository.findByIsPrivate().size();
        int findByPhoneNumberSet = botUserRepository.findByPhoneNumberSet().size();
        int findByLinkSet = botUserRepository.findByLinkSet().size();
        int findByEmailSet = botUserRepository.findByEmailSet().size();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("Количество подписчиков.................").append(findAll).append("\n")
                .append("из них:\n")
                .append("\n")
                .append("отсутствуют публикации................. ").append(getProportion(findByNoPublications)).append("%\n")
                .append("публикаций > 1000...................... ").append(getProportion(findByPublicationsOver1000)).append("%\n")
                .append("отсутствует аватар..................... ").append(getProportion(findByNoAvatar)).append("%\n")
                .append("подписок более 1000.................... ").append(getProportion(findBySubscriptionsOver1000)).append("%\n")
                .append("подписок более 2000.................... ").append(getProportion(findBySubscriptionsOver2000)).append("%\n")
                .append("подписок более 3000.................... ").append(getProportion(findBySubscriptionsOver3000)).append("%\n")
                .append("подписчиков более 1000................. ").append(getProportion(findBySubscribersOver1000)).append("%\n")
                .append("подписчиков более 2000................. ").append(getProportion(findBySubscribersOver2000)).append("%\n")
                .append("подписчиков более 3000................. ").append(getProportion(findBySubscribersOver3000)).append("%\n")
                .append("подписок > подписчиков................. ").append(getProportion(findBySubscriptionsOverSubscribers)).append("%\n")
                .append("подписок < подписчиков................. ").append(getProportion(findBySubscribersOverSubscriptions)).append("%\n")

                .append("бизнесс аккаунт........................ ").append(getProportion(findByIsBusiness)).append("%\n")
                .append("приватный аккаунт...................... ").append(getProportion(findByIsPrivate)).append("%\n")
                .append("указан номер телефона.................. ").append(getProportion(findByPhoneNumberSet)).append("%\n")
                .append("ссылка на сайт в описании.............. ").append(getProportion(findByLinkSet)).append("%\n")
                .append("указан email........................... ").append(getProportion(findByEmailSet)).append("%\n")
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