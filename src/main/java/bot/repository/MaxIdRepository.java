package bot.repository;

import bot.model.MaxId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MaxIdRepository extends JpaRepository<MaxId, Integer> {


    @Query(value = "select * from max_id where id = ( select max(id) from max_id where max_id.max_id <> '') ", nativeQuery = true)
    MaxId findLastMaxId();

}
