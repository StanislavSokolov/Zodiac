package ru.sokolov.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
//import ru.sokolov.dao.PersonDAO;
import ru.sokolov.models.Person;
import ru.sokolov.services.PeopleService;

@Component
public class PersonValidator implements Validator {

//    private final PersonDAO personDAO;

//    public PersonValidator(PersonDAO personDAO) {
//        this.personDAO = personDAO;
//    }

    private final PeopleService peopleService;

    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (person.getAge() < 0) errors.rejectValue("age", "", "Must be more 0");
        else if (peopleService.checkEmail(person.getEmail()) != null) errors.rejectValue("email", "", "This email is already used");
//        else if (!person.getAddress().matches("[A-Z]\\w+, [A-Z]\\w+, \\d{6}")) errors.rejectValue("address", "", "Your address should be in this format: Country, City, Postal Code (6 digits)");
    }
}
