package bot.repository;

import bot.model.MaxId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaxIdRepository extends JpaRepository<MaxId, Integer> {
}
