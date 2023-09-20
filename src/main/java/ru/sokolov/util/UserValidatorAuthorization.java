package ru.sokolov.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sokolov.models.User;
import ru.sokolov.services.UserService;

@Component
public class UserValidatorAuthorization implements Validator {

    private final UserService userService;

    public UserValidatorAuthorization(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (userService.checkAuthorization(user.getLogin(), user.getPassword()) == null) errors.rejectValue("login", "", "Логин и пароль не совпадают");
    }
}
