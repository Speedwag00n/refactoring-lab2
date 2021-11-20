package refactoring.lab2.server.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import refactoring.lab2.server.dto.UserDTO;
import refactoring.lab2.server.exception.LoginInUseException;
import refactoring.lab2.server.service.JwtService;
import refactoring.lab2.server.service.UserService;


@CrossOrigin
@RestController

@RequestMapping("/users")
@AllArgsConstructor
@Validated
public class UserController {

    private UserService userService;
    private JwtService jwtService;

    @PostMapping()
    public ResponseEntity<String> register(@Validated @RequestBody UserDTO dto) {
        try {
            long id = userService.addUser(dto);
            String token = jwtService.login(dto, id);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (LoginInUseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
