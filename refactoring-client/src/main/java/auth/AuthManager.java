package auth;

import dto.AuthDTO;
import exception.NotFoundException;
import exception.ServerException;
import lombok.Getter;
import org.springframework.web.client.ResourceAccessException;
import service.RequestService;

import java.util.Scanner;

public class AuthManager {

    public static String TO_SHORT_LOGIN = "Логин должен быть не короче 6 символов";
    public static String TO_SHORT_PASSWORD = "Пароль должен быть не короче 6 символов";
    public static String DIFFERENT_PASSWORDS = "Введенные пароли не совпали";
    private static String REGISTER_COMMAND = "register";
    private static String LOGIN_COMMAND = "login";

    private final RequestService requestService;
    private final Scanner scanner;

    public AuthManager(RequestService requestService, Scanner scanner) {
        this.requestService = requestService;
        this.scanner = scanner;
    }

    @Getter
    private boolean isInited = false;

    public void init() {
        while (true) {
            String input = printMsgAndReadLine("Введите " + REGISTER_COMMAND + ", чтобы зарегистрировать, или " + LOGIN_COMMAND + ", чтобы авторизоваться: ");

            if (input.equals("register")) {
                register();
                break;
            } else if (input.equals("login")) {
                login();
                break;
            }
        }
    }

    public void login() {
        AuthDTO authDTO = new AuthDTO();

        authDTO.setLogin(readLogin());
        authDTO.setPassword(readPassword());

        try {
            requestService.login(authDTO);
            isInited = true;
        } catch (ResourceAccessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServerException(ex.getMessage(), ex);
        }
    }

    public void register() {
        AuthDTO authDTO = new AuthDTO();

        authDTO.setLogin(readLogin());

        boolean passwordConfirmed = false;

        while (!passwordConfirmed) {
            authDTO.setPassword(readPassword());
            passwordConfirmed = confirmPassword(authDTO.getPassword());
        }

        try {
            requestService.register(authDTO);
            isInited = true;
        } catch (ResourceAccessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServerException(ex.getMessage(), ex);
        }
    }

    private String readLogin() {
        String result = null;

        while (result == null) {
            String login = printMsgAndReadLine("Введите логин: ");

            if (login.length() < 6) {
                System.out.println(TO_SHORT_LOGIN);
            } else {
                result = login;
            }
        }

        return result;
    }

    private String readPassword() {
        String result = null;

        while (result == null) {
            String password = printMsgAndReadLine("Введите пароль: ");

            if (password.length() < 6) {
                System.out.println(TO_SHORT_PASSWORD);
            } else {
                result = password;
            }
        }

        return result;
    }

    private boolean confirmPassword(String targetPassword) {
        boolean confirmed = false;

        String password = printMsgAndReadLine("Подтвердите пароль: ");

        if (targetPassword.equals(password)) {
            confirmed = true;
        } else {
            System.out.println(DIFFERENT_PASSWORDS);
        }

        return confirmed;
    }

    private String printMsgAndReadLine(String msg) {
        System.out.print(msg);
        return scanner.nextLine();
    }

}
