package service;

import dto.AuthDTO;
import dto.WordDTO;
import error.RestTemplateResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class RequestService {

    private RestTemplate restTemplate;
    private final String serverResourceUrl = "http://localhost:8080";
    public static final String SERVER_IS_NOT_AVAILABLE_MSG = "Сервер недоступен";

    private String authToken;

    public RequestService() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    public WordDTO[] getSameRootWords(String word) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + authToken);

        HttpEntity<WordDTO> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<WordDTO[]> response = restTemplate.exchange(serverResourceUrl + "/words/" + word, HttpMethod.GET, requestEntity, WordDTO[].class);
        return response.getBody();
    }

    public void saveNewWord(WordDTO wordDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + authToken);

        HttpEntity<WordDTO> requestEntity = new HttpEntity<>(wordDTO, headers);

        restTemplate.exchange(serverResourceUrl + "/words/", HttpMethod.POST, requestEntity, String.class);
    }

    public void register(AuthDTO authDTO) {
        authToken = restTemplate.postForEntity(serverResourceUrl + "/users", authDTO, String.class).getBody();
    }

    public void login(AuthDTO authDTO) {
        authToken = restTemplate.postForEntity(serverResourceUrl + "/auth/login", authDTO, String.class).getBody();
    }

}
