package refactoring.lab2.server.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String word;

    @ManyToMany
    @JoinTable(name = "words_prefixes",
            joinColumns = {@JoinColumn(name = "word_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "prefix", referencedColumnName = "prefix")})
    protected List<Prefix> prefixes;

    @ManyToOne
    private Root root;

    @ManyToMany
    @JoinTable(name = "words_suffixes",
            joinColumns = {@JoinColumn(name = "word_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "suffix", referencedColumnName = "suffix")})
    protected List<Suffix> suffixes;

    @Column
    private Integer partsNumber;

    public Word(String word, List<Prefix> prefixes, Root root, List<Suffix> suffixes) {
        this.word = word;
        this.prefixes = prefixes;
        this.root = root;
        this.suffixes = suffixes;
    }

}
