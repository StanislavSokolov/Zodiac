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
        if (user.getTokenClientOzon().length() < 6) errors.rejectValue("tokenClientOzon", "", "Уникальный ключ (API Key) не должен быть меньше 6 символов и должен состоять из цифр 0-9");
//        else if (!user.getTokenStatisticOzon().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")) errors.rejectValue("password", "", "Пароль должен быть длиной от 8-20 символов, иметь в своем состваве не менее одной цифры 0-9, не менее одного символа из нижнего регистра a-z, не менее одного символа из верхнего регистра A-Z и не менее одного специального символа !@#&()–[{}]:;',?/*~$^+=<>");
    }
}
