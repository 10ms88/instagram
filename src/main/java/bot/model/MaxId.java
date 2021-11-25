package bot.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Entity
@Table(name = "MAX_ID")
@Data
@AllArgsConstructor
@ToString
public class MaxId {

    public MaxId() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column
    private String maxId;

    @Column
    private long createdTs;


}
