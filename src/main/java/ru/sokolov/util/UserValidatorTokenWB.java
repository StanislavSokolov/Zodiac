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
//        if (userService.checkToken(user.getTokenStandartWB() != null) errors.rejectValue("email", "", "Такой адрес электронной почты уже существует");
//        else if (!person.getAddress().matches("[A-Z]\\w+, [A-Z]\\w+, \\d{6}")) errors.rejectValue("address", "", "Your address should be in this format: Country, City, Postal Code (6 digits)");
//        else if (user.getPassword().length() < 7) errors.rejectValue("password", "", "Пароль не должен быть меньше 8 символов");
//        else if (!user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")) errors.rejectValue("password", "", "Пароль должен быть длиной от 8-20 символов, иметь в своем состваве не менее одной цифры 0-9, не менее одного символа из нижнего регистра a-z, не менее одного символа из верхнего регистра A-Z и не менее одного специального символа !@#&()–[{}]:;',?/*~$^+=<>");
    }
}
