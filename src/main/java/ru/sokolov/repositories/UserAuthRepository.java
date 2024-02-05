package ru.sokolov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sokolov.models.UserAuth;

import java.util.List;

public interface UserAuthRepository extends JpaRepository<UserAuth, Integer> {
    List<UserAuth> findByAuthorizationAndUser_idAndDeviceAndIp(String authorization, String user_id, String device, String ip);
}
