package refactoring.lab2.server.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String login;

    private String password;

    private Date lastLogout;

    @OneToMany(mappedBy = "owner")
    private List<Word> words;

}
