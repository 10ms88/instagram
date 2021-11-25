package bot.repository;

import bot.model.BotUserLite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


public interface BotUserLiteRepository extends JpaRepository<BotUserLite, String> {

}
