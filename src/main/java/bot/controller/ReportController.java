package bot.controller;

import bot.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;


    @GetMapping("/report")
    public String getReport() {
        return reportService.getReport();
    }

}
