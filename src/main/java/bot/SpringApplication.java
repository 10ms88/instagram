package bot;

import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.vaadin.artur.helpers.LaunchUtil;

@SpringBootApplication
@Theme(value = "gridpaging")
@PWA(name = "Grid Paging", shortName = "Grid Paging", offlineResources = {"images/logo.png"})
public class SpringApplication {

    public static void main(String[] args) {
        LaunchUtil.launchBrowserInDevelopmentMode(org.springframework.boot.SpringApplication.run(SpringApplication.class, args));
    }

}

