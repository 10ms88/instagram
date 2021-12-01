package bot.controller;

import bot.service.BotUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class DefaultController {

    @Autowired
    private BotUserService botUserService;


    @PostMapping("/getFollowersList")
    public void getFollowersList(@RequestParam String maxId) {
        botUserService.getFollowersList(maxId);
    }

    @PostMapping("/login")
    public void login(@RequestParam String withProxy) throws Exception {
         botUserService.login(Integer.parseInt(withProxy));
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


    @PostMapping("/interruptThread")
    public boolean interruptThread(@RequestParam String threadName) {
        return botUserService.interruptThread(threadName);
    }

    @GetMapping("/getThreadsMap")
    public String getThreadsMap() {
        return botUserService.getThreadsMap();
    }
    }
