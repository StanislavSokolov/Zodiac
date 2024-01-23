package ru.sokolov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sokolov.models.Person;
import ru.sokolov.models.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByEmail(String email);
    List<User> findByEmailAndPassword(String email, String password);

    List<User> findByTokenStandartWB(String tokenStandartWB);
    List<User> findByTokenClientOzon(String tokenClientOzon);
    List<User> findByTokenStatisticOzon(String tokenStatisticOzon);

    @Modifying
    @Query("update User u set u.tokenStandartWB = ?1 where u.id = ?2")
    int setTokenWB(String tokenStandartWB, int id);

    @Modifying
    @Query("update User u set u.tokenClientOzon = ?1, u.tokenStatisticOzon = ?2 where u.id = ?3")
    int setTokenOzon(String tokenClientOzon, String tokenStatisticOzon, int id);
}
