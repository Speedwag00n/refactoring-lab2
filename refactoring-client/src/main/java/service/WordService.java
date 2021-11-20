package service;

import dto.WordDTO;
import exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Data
@AllArgsConstructor
public class WordService {
    private String currentWord;
    private WordDTO wordDTO;
    private Scanner scanner;
    public static final String SAME_ROOT_WORDS_MSG = "Известные однокоренные слова:";
    public static final String EMPTY_STRING_MSG = "Вводимое слово не должно быть пустой строкой";
    public static final String YES = "да";
    public static final String QUIT = "q";

    public WordService(Scanner scanner) {
        this.scanner = scanner;
        this.wordDTO = new WordDTO();
    }

    public String readNewWord() throws ValidationException {
        currentWord = printMsgAndReadLine("Введите слово: ");

        if (currentWord.trim().equals("")) {
            throw new ValidationException(EMPTY_STRING_MSG);
        }

        return currentWord;
    }

    private List<String> readPrefixes() {
        List<String> prefixes = new ArrayList<>();
        boolean continueToRead = true;

        while (continueToRead) {
            String prefix = printMsgAndReadLine("Приставка: ");
            if (!prefix.trim().equals("")) {
                prefixes.add(prefix);
            } else {
                continueToRead = false;
            }
        }

        return prefixes;
    }

    private String readRoot() {
        return printMsgAndReadLine("Корень: ");
    }

    private List<String> readSuffixes() {
        List<String> suffixes = new ArrayList<>();
        boolean continueToRead = true;

        while (continueToRead) {
            String suffix = printMsgAndReadLine("Суффикс или окончание: ");
            if (!suffix.trim().equals("")) {
                suffixes.add(suffix);
            } else {
                continueToRead = false;
            }
        }

        return suffixes;
    }

    private String printMsgAndReadLine(String msg) {
        System.out.print(msg);
        return scanner.nextLine();
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public void printMsgWordIsAdded(String word) {
        System.out.println("Слово " + word + " добавлено.");
    }

    public WordDTO readWordByParts() {
        wordDTO.setWord(currentWord);
        wordDTO.setPrefixes(readPrefixes());
        wordDTO.setRoot(readRoot());
        wordDTO.setSuffixes(readSuffixes());

        return wordDTO;
    }

    public void printSameRootWords(WordDTO[] wordDTOS) {
        for (WordDTO s : wordDTOS) {
            System.out.println(s.toString());
        }
    }

}
