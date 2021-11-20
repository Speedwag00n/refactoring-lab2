package refactoring.lab2.server.mapper;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import refactoring.lab2.server.dto.UserDTO;
import refactoring.lab2.server.entity.User;

@Component
@AllArgsConstructor
public class UserMapper {

    private PasswordEncoder passwordEncoder;

    public User dtoToEntity(UserDTO dto) {
        User entity = new User();

        entity.setLogin(dto.getLogin());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));

        return entity;
    }

}
