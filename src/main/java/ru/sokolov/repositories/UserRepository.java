package ru.sokolov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sokolov.models.Person;
import ru.sokolov.models.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByEmail(String email);
    List<User> findByLoginAndPassword(String login, String password);
}
