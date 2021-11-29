package bot.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Props {

    private static final String PATH_TO_PROPERTIES = "C:\\Users\\mmmss\\Desktop\\insta\\i\\instagram\\src\\main\\resources\\config.properties";


    public static long getFollowersSize() {
        return getLongTypeProperty("followersSize");
    }

    public static long getPartitionSize() {
        return getLongTypeProperty("partitionSize");
    }

    public static long getUserIdForScanFollowers() {
        return getLongTypeProperty("userIdForScanFollowers");
    }

    public static synchronized long getWaitingTimes() {
        return getLongTypeProperty("waitingTimes");
    }


    private static long getLongTypeProperty(String propertyName) {
        Properties prop = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            prop.load(fileInputStream);
            propertyName = prop.getProperty(propertyName);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Long.parseLong(propertyName);
    }
}