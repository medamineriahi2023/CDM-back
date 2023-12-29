package oga.microservice.athentification.validator;

import lombok.AllArgsConstructor;
import oga.microservice.athentification.httpRequests.LoginRequest;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
@AllArgsConstructor
public class LoginRequestValidator {
   MessageSource messageSource;


    public List<String> validator(LoginRequest loginRequest, Locale locale){
        List<String> errors = new ArrayList<>();

        if (!StringUtils.hasLength(loginRequest.getUsername())){
            errors.add(messageSource.getMessage("empty.username", null, locale));
        }

        if (!StringUtils.hasLength(loginRequest.getPassword())){
            errors.add(messageSource.getMessage("empty.password", null, locale));
        }
        return errors;
    }

}
