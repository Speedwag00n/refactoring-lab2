package refactoring.lab2.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import refactoring.lab2.server.entity.Root;
import refactoring.lab2.server.entity.User;
import refactoring.lab2.server.entity.Word;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {
    List<Word> findAllByRootOrderByPartsNumber(Root root);

    List<Word> findAllByRootAndOwnerIdOrderByPartsNumber(Root root, Integer ownerId);

    Optional<Word> findByWord(String word);

    Optional<Word> findByWordAndOwnerId(String word, Integer ownerId);
}
