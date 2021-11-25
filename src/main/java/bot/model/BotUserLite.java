package bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Entity
@Table(name = "BOT_USER_LITE")
@Data
@AllArgsConstructor
@ToString
public class BotUserLite {
    public BotUserLite() {
    }


    @Id
    public String username;

    @Column
    @Nullable
    public long pk;
}
