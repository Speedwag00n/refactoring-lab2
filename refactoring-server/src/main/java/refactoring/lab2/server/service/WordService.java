package refactoring.lab2.server.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import refactoring.lab2.server.entity.Root;
import refactoring.lab2.server.entity.User;
import refactoring.lab2.server.entity.Word;
import refactoring.lab2.server.exception.WordAlreadyExists;
import refactoring.lab2.server.exception.WordNotFoundException;
import refactoring.lab2.server.repository.UserRepository;
import refactoring.lab2.server.repository.WordRepository;
import refactoring.lab2.server.util.JwtUtil;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WordService {
    private final WordRepository wordRepository;
    private final UserRepository userRepository;
    private JwtUtil jwtUtil;

    public List<Word> getTheSameRootWords(String wordToFind, String tokenHeader) {
        String token = jwtUtil.getTokenFromHeader(tokenHeader);

        Word word = getWordByFullWord(wordToFind, tokenHeader);
        Root root = word.getRoot();

        return wordRepository.findAllByRootAndOwnerIdOrderByPartsNumber(root, Integer.parseInt(jwtUtil.getUserIdFromToken(token)));
    }

    private Word getWordByFullWord(String word, String tokenHeader) {
        String token = jwtUtil.getTokenFromHeader(tokenHeader);

        return wordRepository.findByWordAndOwnerId(word, Integer.parseInt(jwtUtil.getUserIdFromToken(token)))
                .orElseThrow(() -> new WordNotFoundException(word + " is not found"));
    }

    public void saveNewWord(Word word, String tokenHeader) {
        String token = jwtUtil.getTokenFromHeader(tokenHeader);
        User owner = userRepository.findById(Integer.parseInt(jwtUtil.getUserIdFromToken(token)));

        Optional<Word> wordFromRepo = wordRepository.findByWordAndOwnerId(word.getWord(), owner.getId());

        if (wordFromRepo.isPresent()) {
            throw new WordAlreadyExists(word.getWord() + " already exists");
        } else {
            word.setOwner(owner);
            wordRepository.save(word);
        }
    }
}
