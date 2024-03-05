package ru.sokolov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sokolov.models.Stock;
import ru.sokolov.models.User;
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

    public List<UserAuth> findByAuthorizationAndDeviceAndIp(String authorization, String device, String ip) {
        List<UserAuth> userAuths = userAuthRepository.findByAuthorizationAndDeviceAndIp(authorization, device, ip);
        if (userAuths.isEmpty())
            return null;
        return userAuths;
    }

    @Transactional
    public void save(UserAuth userAuth) {
        userAuthRepository.save(userAuth);
    }
}
