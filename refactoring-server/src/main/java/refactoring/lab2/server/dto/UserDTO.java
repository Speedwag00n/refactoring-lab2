package refactoring.lab2.server.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    @NotNull(message = "login should not be null")
    @Size(min=6, max=16)
    private String login;

    @NotNull(message = "password should not be null")
    @Size(min=6, max=16)
    private String password;

}
