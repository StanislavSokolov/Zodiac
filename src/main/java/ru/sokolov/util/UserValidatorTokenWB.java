package ru.sokolov.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sokolov.models.User;
import ru.sokolov.services.UserService;

@Component
public class UserValidatorTokenWB implements Validator {

    private final UserService userService;

    public UserValidatorTokenWB(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (userService.checkTokenStandartWB(user.getTokenStandartWB()) != null)
            errors.rejectValue("tokenStandartWB", "", "Токен не подходит");
        if (userService.checkTokenStatisticWB(user.getTokenStatisticWB()) != null)
            errors.rejectValue("tokenStatisticWB", "", "Токен не подходит");
        if (userService.checkTokenAdvertisingWB(user.getTokenAdvertisingWB()) != null)
            errors.rejectValue("tokenAdvertisingWB", "", "Токен не подходит");
    }
}
