package bot.repository;

import bot.model.BotUserLite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotUserLiteRepository extends JpaRepository<BotUserLite, String> {

}
