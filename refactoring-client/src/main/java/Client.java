import auth.AuthManager;
import dto.WordDTO;
import exception.*;
import org.springframework.web.client.ResourceAccessException;
import service.RequestService;
import service.WordService;

import java.util.Scanner;

import static service.RequestService.SERVER_IS_NOT_AVAILABLE_MSG;
import static service.WordService.SAME_ROOT_WORDS_MSG;
import static service.WordService.YES;
import static service.WordService.QUIT;

public class Client {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RequestService requestService = new RequestService();
        AuthManager authManager = new AuthManager(requestService, scanner);

        try {
            authManager.init();
        } catch (ResourceAccessException ex) {
            System.out.println(SERVER_IS_NOT_AVAILABLE_MSG);
        } catch (ServerException ex) {
            System.out.println(ex.getMessage());
        }

        boolean running = authManager.isInited();

        while (running) {

            WordService wordService = new WordService(scanner);

            String newWord;

            try {
                newWord = wordService.readNewWord();
            } catch (ValidationException ex) {
                System.out.println(ex.getMessage());
                continue;
            }

            if (newWord.equalsIgnoreCase(QUIT)) {
                running = false;
                continue;
            }

            try {
                WordDTO[] dtos = requestService.getSameRootWords(newWord);
                System.out.println(SAME_ROOT_WORDS_MSG);
                wordService.printSameRootWords(dtos);
            } catch (ValidationException | ServerException ex) {
                System.out.println(ex.getMessage());
            } catch (ResourceAccessException ex) {
                System.out.println(SERVER_IS_NOT_AVAILABLE_MSG);
            } catch (NotFoundException ex) {
                System.out.println(ex.getMessage());
                processNotFoundWord(wordService, requestService);
            }
        }
    }

    private static void processNotFoundWord(WordService wordService, RequestService requestService) {
        if (wordService.readLine().equalsIgnoreCase(YES)) {
            WordDTO wordDTO = wordService.readWordByParts();
            try {
                requestService.saveNewWord(wordDTO);
                wordService.printMsgWordIsAdded(wordDTO.toString());
            } catch (ValidationException | ServerException | ComposedWordIsNotValid | WordAlreadyExists exp) {
                System.out.println(exp.getMessage());
            } catch (ResourceAccessException exp) {
                System.out.println(SERVER_IS_NOT_AVAILABLE_MSG);
            }
        }
    }

}
