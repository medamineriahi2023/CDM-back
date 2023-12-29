package oga.microservice.athentification.validator;

import lombok.AllArgsConstructor;
import oga.microservice.athentification.entities.User;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
public class RegisterRequestValidator {

     MessageSource messageSource;
    public List<String> validator(User user, Locale locale){
        List<String> errors = new ArrayList<>();

//        if (!StringUtils.hasLength(user.getRole())){
//            errors.add(messageSource.getMessage("empty.username", null, locale));
//        }

        if (!StringUtils.hasLength(user.getPassword())){
            errors.add(messageSource.getMessage("empty.password", null , locale));
        }

        if (!StringUtils.hasLength(user.getEmail())){
            errors.add(messageSource.getMessage("empty.email", null, locale));
        }

        if (!StringUtils.hasLength(user.getLastname())){
            errors.add(messageSource.getMessage("empty.lastname", null, locale));
        }
        if (!StringUtils.hasLength(user.getFirstname())){
            errors.add(messageSource.getMessage("empty.firstname", null, locale));
        }

        return errors;
    }
}
