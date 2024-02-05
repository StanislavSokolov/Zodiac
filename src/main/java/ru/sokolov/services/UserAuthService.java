package ru.sokolov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sokolov.models.Stock;
import ru.sokolov.models.UserAuth;
import ru.sokolov.repositories.UserAuthRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserAuthService {

    private final UserAuthRepository userAuthRepository;

    @Autowired
    public UserAuthService(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }
    public List<UserAuth> findByAuthorizationAndUser_idAndDeviceAndIp(String authorization, String user_id, String device, String ip) {
        return userAuthRepository.findByAuthorizationAndUser_idAndDeviceAndIp(authorization, user_id, device, ip);
    }

    public boolean checkUserAuth(String authorization, String user_id, String device, String ip) {
        if (userAuthRepository.findByAuthorizationAndUser_idAndDeviceAndIp(authorization, user_id, device, ip).isEmpty())
        return false;
        return true;
    }

    public boolean checkUserAuth(int user_id, String remoteAddr, String header) {
    }
}
