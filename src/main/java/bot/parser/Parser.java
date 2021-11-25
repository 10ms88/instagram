package bot.parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Parser {


    private static String FILE_PATH = "accounts.txt";


    public static  List<Account> getAccounts() {

        List<Account> accountList = new ArrayList<>();


        try {
            File file = new File(FILE_PATH);
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



}
