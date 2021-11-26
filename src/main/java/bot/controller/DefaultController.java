package bot.controller;

import bot.service.BotUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void getFollowersList(@RequestParam long  userId, @RequestParam String maxId) throws IOException, InterruptedException, ClassNotFoundException {
        botUserService.getFollowersList(userId, maxId);
    }

    @PostMapping("/login")
    public String login(@RequestParam String name, @RequestParam String pass) throws Exception {
        return botUserService.login(name, pass);
    }

    @PostMapping("/createBotUsers")
    public String createBotUsers() {
        return botUserService.createBotUsers();
    }

    @PostMapping("/createBotUserLiteLists")
    public String createBotUserLiteLists() {
        return botUserService.createBotUserLiteLists();
    }

    @GetMapping("/exclude_BotUserLite_From_Creating_List")
    public void excludeBotUserLiteFromCreatingList() {
         botUserService.excludeBotUserLiteFromCreatingList();
    }



}
