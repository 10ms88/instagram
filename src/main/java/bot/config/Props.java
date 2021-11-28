package bot.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Props {

    private static final String PATH_TO_PROPERTIES = "src/main/resources/config.properties";
    private static FileInputStream fileInputStream;


    public static long getFollowersSize() {
        return getLongTypeProperty("followersSize");
    }

    public static long getPartitionSize() {
        return getLongTypeProperty("partitionSize");
    }

    public static long getUserIdForScanFollowers() {
        return getLongTypeProperty("userIdForScanFollowers");
    }

    public static long getWaitingTimes() {
        return getLongTypeProperty("waitingTimes");
    }


    private static long getLongTypeProperty(String propertyName) {
        Properties prop = new Properties();
        try {
            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            prop.load(fileInputStream);
            propertyName = prop.getProperty(propertyName);
            fileInputStream.close();
        } catch (IOException e) {
            System.out.println("Ошибка в программе: файл " + PATH_TO_PROPERTIES + " не обнаружено");
            e.printStackTrace();
        }
        return Long.parseLong(propertyName);
    }
}