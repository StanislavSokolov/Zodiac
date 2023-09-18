package ru.sokolov.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sokolov.models.User;
import ru.sokolov.services.UserService;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (user.getLogin().equals("")) errors.rejectValue("login", "", "Must have at least one character");
//        else if (user.getPassword()) errors.rejectValue("age", "", "Must be more 0");
        else if (userService.checkEmail(user.getEmail()) != null) errors.rejectValue("email", "", "This email is already used");
//        else if (!person.getAddress().matches("[A-Z]\\w+, [A-Z]\\w+, \\d{6}")) errors.rejectValue("address", "", "Your address should be in this format: Country, City, Postal Code (6 digits)");
    }
}
