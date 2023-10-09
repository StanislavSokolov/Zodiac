package ru.sokolov.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sokolov.models.User;
import ru.sokolov.services.UserService;

@Component
public class UserValidatorTokenOzon implements Validator {

    private final UserService userService;

    public UserValidatorTokenOzon(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (userService.checkTokenClientOzon(user.getTokenClientOzon()) != null)
            errors.rejectValue("tokenClientOzon", "", "Токен не подходит");
        if (userService.checkTokenStatisticOzon(user.getTokenStatisticOzon()) != null)
            errors.rejectValue("tokenStatisticOzon", "", "Токен не подходит");
    }
}
