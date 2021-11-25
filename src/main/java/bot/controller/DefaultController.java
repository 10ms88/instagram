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


    @PostMapping("/getFollowersList")
    public void getFollowersList(@RequestParam String userName) throws IOException, InterruptedException, ClassNotFoundException {
        botUserService.getFollowersList(userName);
    }

    @PostMapping("/login")
    public void login(@RequestParam String name, @RequestParam String pass) throws Exception {
        botUserService.login(name, pass);
    }

    @PostMapping("/createBotUsers")
    public String createBotUsers() {
        return botUserService.createBotUsers();
    }

    @PostMapping("/createBotUserLiteLists")
    public String createBotUserLiteLists() {
        return botUserService.createBotUserLiteLists();
    }
}
