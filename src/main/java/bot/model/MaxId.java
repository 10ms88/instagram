package bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "MAX_ID")
@Data
@AllArgsConstructor
@ToString
public class MaxId {

    public MaxId() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column
    private String maxId;

    @Column
    private long createdTs;


}
