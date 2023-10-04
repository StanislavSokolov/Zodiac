package ru.sokolov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sokolov.models.Person;
import ru.sokolov.models.User;
import ru.sokolov.repositories.PeopleRepository;
import ru.sokolov.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(int id) {
        Optional<User> foundUser = userRepository.findById(id);

        return foundUser.orElse(null);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void updateTokenWB(int id, User updatedUser) {
        userRepository.setTokenWB(updatedUser.getTokenStandartWB(), updatedUser.getTokenStatisticWB(), updatedUser.getTokenAdvertisingWB(), id);
    }

    @Transactional
    public void updateTokenOzon(int id, User updatedUser) {
        userRepository.setTokenOzon(updatedUser.getTokenClientOzon(), updatedUser.getTokenStatisticOzon(), id);
    }

    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    public User checkEmail(String email) {
        List<User> users = userRepository.findByEmail(email);
        if (users.isEmpty()) return null; else return users.get(0);
    }

    public User checkAuthorization(String email, String password) {
        List<User> users = userRepository.findByEmailAndPassword(email, password);
        if (users.isEmpty()) return null; else return users.get(0); // надо проверять, возможно такой будет и не один
    }
}
