package refactoring.lab2.server.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import refactoring.lab2.server.dto.UserDTO;
import refactoring.lab2.server.entity.User;
import refactoring.lab2.server.exception.LoginInUseException;
import refactoring.lab2.server.mapper.UserMapper;
import refactoring.lab2.server.repository.UserRepository;

import javax.validation.ConstraintViolationException;
import java.util.Date;

@Service
@AllArgsConstructor
public class UserService {

    private UserMapper userMapper;
    private UserRepository userRepository;

    public long addUser(UserDTO user) throws LoginInUseException {
        User entity = userMapper.dtoToEntity(user);
        Date registrationTime = new Date();
        entity.setLastLogout(registrationTime);
        try {
            long userId = userRepository.save(entity).getId();
            return userId;
        } catch (Throwable e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new LoginInUseException("Login isn't unique", e.getCause());
            } else {
                throw e;
            }
        }
    }

}
