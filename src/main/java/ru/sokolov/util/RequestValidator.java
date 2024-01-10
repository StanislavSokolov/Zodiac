package ru.sokolov.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sokolov.models.Product;
import ru.sokolov.models.Request;
import ru.sokolov.models.User;
import ru.sokolov.services.RequestService;
import ru.sokolov.services.UserService;

@Component
public class RequestValidator implements Validator {

    private final RequestService requestService;
    private final UserService userService;


    public RequestValidator(RequestService requestService, UserService userService) {
        this.requestService = requestService;
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Product.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Request request = (Request) o;
        if (!request.getDataToChange().matches("^(?=.*[0-9]).{0,5}$")) errors.rejectValue("dataToChange", "", "Поле должно состоять из цифр, которых не может быть больше пяти");
//        User user = userService.findOne(request.getClientId());
//        if (user == null) errors.rejectValue("dataToChange", "", "Ошибка идентификатора клиента");
//        else {
//            if (request.getShop().equals("wb")) {
//                if (user.getTokenStandartWB() == null) errors.rejectValue("dataToChange", "", "Ошибка токена магазина");
//            }
//            if (request.getShop().equals("ozon")) {
//                if (user.getTokenStatisticOzon() == null) errors.rejectValue("dataToChange", "", "Ошибка токена магазина");
//            }
//        }


    }
}
