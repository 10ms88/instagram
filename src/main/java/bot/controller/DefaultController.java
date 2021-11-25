package bot.controller;

import bot.service.BotUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class DefaultController {

    @Autowired
    private BotUserService botUserService;


    @RequestMapping("/")
    public String index() {

        return "index";
    }


    @PostMapping("/bot_user")
    public void addBotUser(@RequestParam String userName) throws IOException, InterruptedException {
        botUserService.getFollowersList(userName);
    }

    @PostMapping("/login")
    public void login(@RequestParam String name,
                        @RequestParam String pass) throws Exception {
         botUserService.login(name, pass);
    }

    @PostMapping("/create")
    public void login() throws Exception {
        botUserService.createBotUsers();
    }
}
