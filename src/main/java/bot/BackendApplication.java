package bot;

import bot.service.BotUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class BackendApplication {

    @Autowired
    private BotUserService botUserService;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void testJpaMethods() {

//        BotUser botUser = new BotUser();
//        botUser.setBio("address");
//        botUser.setName("botUser");
//        botUser.setLogin("botUser_login");
//        botUserService.createBotUser(botUser);
//
//
//        BotUser botUser1 = new BotUser();
//        botUser1.setBio("address1");
//        botUser1.setName("botUser1");
//        botUser1.setLogin("botUser1_login");
//        botUserService.createBotUser(botUser1);

    }
}

