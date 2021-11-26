package bot.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Props {

    private static final String PATH_TO_PROPERTIES = "src/main/resources/config.properties";
    private static FileInputStream fileInputStream;


//    public static int getFollowersSize() {
////        partitionSize = 10000
////        userId = 5552563905L #fitness_rezinka
////                waitingTimes = 15
//        Properties prop = new Properties();
//        String followersSize = "0";
//        try {
//            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
//            prop.load(fileInputStream);
//            followersSize = prop.getProperty("followersSize");
//        } catch (IOException e) {
//            System.out.println("Ошибка в программе: файл " + PATH_TO_PROPERTIES + " не обнаружено");
//            e.printStackTrace();
//        }
//        return Integer.parseInt(followersSize);
//    }
}