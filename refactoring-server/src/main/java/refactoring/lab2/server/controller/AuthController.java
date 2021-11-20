package refactoring.lab2.server.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import refactoring.lab2.server.dto.UserDTO;
import refactoring.lab2.server.service.JwtService;
import refactoring.lab2.server.util.JwtUtil;


@CrossOrigin
@RestController

@RequestMapping("/auth")
@AllArgsConstructor
@Validated
public class AuthController {

    private JwtService jwtService;
    private JwtUtil jwtUtil;

    @PostMapping(path = "/login")
    public String login(@Validated @RequestBody UserDTO dto) {
        return jwtService.login(dto);
    }

    @PostMapping(path = "/logout")
    public void logout(@RequestHeader(value = "Authorization") String tokenHeader) {
        String token = jwtUtil.getTokenFromHeader(tokenHeader);
        String login = jwtUtil.getUsernameFromToken(token);

        jwtService.logout(login);
    }

}
