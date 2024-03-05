package ru.sokolov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sokolov.models.UserAuth;

import java.util.List;

public interface UserAuthRepository extends JpaRepository<UserAuth, Integer> {
    List<UserAuth> findByAuthorizationAndDeviceAndIp(String authorization, String device, String ip);

    List<UserAuth> findByDeviceAndIp(String ip, String device);
}
