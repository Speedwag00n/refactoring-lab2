package refactoring.lab2.server.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import refactoring.lab2.server.dto.UserDTO;
import refactoring.lab2.server.entity.User;
import refactoring.lab2.server.repository.UserRepository;
import refactoring.lab2.server.util.JwtUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class JwtService implements UserDetailsService {

    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User with login " + login + " not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }

    public String login(UserDTO user) {
        User entity = userRepository.findByLogin(user.getLogin());
        if (entity != null) {
            long id = entity.getId();
            return login(user, id);
        } else {
            throw new BadCredentialsException("Bad login");
        }
    }

    public String login(UserDTO user, long id) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));
        UserDetails userDetails = loadUserByUsername(user.getLogin());
        String token = jwtUtil.generateToken(userDetails.getUsername(), id);
        return token;
    }

    public void logout(String login) {
        User entity = userRepository.findByLogin(login);

        Date lastLogout = new Date();
        entity.setLastLogout(lastLogout);

        userRepository.save(entity);
    }

    public Date loadLastLogout(String login) {
        User entity = userRepository.findByLogin(login);
        return entity.getLastLogout();
    }

}
