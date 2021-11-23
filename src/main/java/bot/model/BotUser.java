package bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "BOT_USER")
@Data
@AllArgsConstructor
public class BotUser {
    public BotUser() {
    }


    @Id
    public String username;

    @Column
    public String fullName;
    @Column
    private String bio;
    @Column
    public boolean isVerified;
    @Column
    public boolean isPrivate;
    @Column
    public long pk;
    @Column()
    public Integer postCount;
    @Column
    public Integer followersCount;
    @Column
    public Integer subscribeCount;
}
