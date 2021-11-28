package bot.parser;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.models.user.Profile;
import org.apache.http.HttpHost;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {


    private static String FILE_PATH_ACCOUNTS = "accounts.txt";
    private static String FILE_PATH_PROXIES = "proxies.txt";

    public static List<Account> getAccounts() {

        List<Account> accountList = new ArrayList<>();

        try {
            File file = new File(FILE_PATH_ACCOUNTS);
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            while (line != null) {
                // считываем остальные строки в цикле
                String[] lineArray = line.split(":");
                Account account = Account.builder()
                        .userName(lineArray[0]).
                        instagramPassword(lineArray[1])
                        .email(lineArray[2])
                        .emailPassword(lineArray[3])
                        .build();

                accountList.add(account);

                line = reader.readLine();
            }
            reader.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    public static List<HttpHost> getHttpHost() {

        List<HttpHost> httpHosts = new ArrayList<>();

        try {
            File file = new File(FILE_PATH_PROXIES);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                String[] lineArray = line.split(":");
                HttpHost httpHost = new HttpHost(lineArray[0], Integer.parseInt(lineArray[1]), lineArray[2]);
                httpHosts.add(httpHost);
                line = reader.readLine();
            }
            reader.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpHosts;
    }





}
