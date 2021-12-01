package bot.parser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Account {

    private String userName;

    private String instagramPassword;

    private String email;

    private String emailPassword;

    private String proxyAddress;

    private int port;

    private String proxyUserName;

    private String proxyPassword;


}

