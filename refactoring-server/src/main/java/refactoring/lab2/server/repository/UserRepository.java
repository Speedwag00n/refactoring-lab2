package refactoring.lab2.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import refactoring.lab2.server.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

    User findById(Integer id);

}
