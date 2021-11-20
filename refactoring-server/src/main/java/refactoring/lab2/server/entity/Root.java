package refactoring.lab2.server.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Root {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String root;

    @OneToMany(mappedBy = "root")
    private List<Word> words;

}
