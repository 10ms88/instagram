package bot.utils;

import bot.config.ApplicationContextProvider;
import bot.config.Props;
import bot.model.BotUser;
import bot.model.BotUserLite;
import bot.repository.BotUserLiteRepository;
import bot.repository.BotUserRepository;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//Total botUserLites 261293
//        Total botUser 41663
//        To creating  219783

@Log4j
public class BotUserLiteExcluder {


    public static List<BotUserLite> excludeBotUserLiteFromCreatingList() {


        Map<String, BotUserLite> botUserLiteMap = ApplicationContextProvider
                .getApplicationContext()
                .getBean(BotUserLiteRepository.class)
                .findAll()
                .stream()
                .collect(Collectors.toMap(BotUserLite::getUsername, botUserLite -> botUserLite, (a, b) -> b));

        Map<String, BotUser> botUserMap = ApplicationContextProvider
                .getApplicationContext()
                .getBean(BotUserRepository.class)
                .findAll()
                .stream()
                .collect(Collectors.toMap(BotUser::getUsername, botUser -> botUser, (a, b) -> b));

        log.info( "Total botUserLites " + botUserLiteMap.size());
        log.info("Total botUser " + botUserMap.size());

        List<BotUserLite> resultUserNamesList = new ArrayList<>();

        for (Map.Entry<String, BotUserLite> entry : botUserLiteMap.entrySet()) {
            if (!botUserMap.containsKey(entry.getKey()))
                resultUserNamesList.add(entry.getValue());
        }
        log.info("To creating  " + resultUserNamesList.size());


        return resultUserNamesList;
    }
}
