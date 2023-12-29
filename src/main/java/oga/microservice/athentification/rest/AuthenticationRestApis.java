package oga.microservice.athentification.rest;

import lombok.AllArgsConstructor;
import oga.microservice.athentification.entities.User;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.exceptions.InvalidEntityException;
import oga.microservice.athentification.exceptions.UnauthorizedException;
import oga.microservice.athentification.httpRequests.LoginRequest;
import oga.microservice.athentification.service.IAuthenticationService;
import org.keycloak.email.EmailException;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

import static oga.microservice.athentification.constants.Constants.ROOT;

@RestController
@RequestMapping(path = ROOT)
@AllArgsConstructor
public class AuthenticationRestApis {
    MessageSource messageSource;
    IAuthenticationService authenticationService;
    @CrossOrigin(origins = "*")
    @PostMapping(path = "auth")
    public ResponseEntity<AccessTokenResponse> login(@RequestHeader(name = "Accept-Language", required = false) Locale locale,@RequestBody LoginRequest loginRequest) throws InvalidEntityException, UnauthorizedException {
        return authenticationService.login(loginRequest, locale);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "register")
    public ResponseEntity<User> signUp(@RequestHeader(name = "Accept-Language", required = false) Locale locale, @RequestBody User user) throws InvalidEntityException{
        return authenticationService.createUser(user, locale);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "sendmail/{userId}")
    public void sendUserCredentials(@PathVariable String userId) throws EmailException {
        authenticationService.GenerateAndSendUserCredentials(userId);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "resetPass/{email}")
    public void sendUserCredentialsWithEmail(@RequestHeader(name = "Accept-Language", required = false) Locale locale,@PathVariable String email) throws EmailException, EntityNotFoundException {
        authenticationService.GenerateAndSendUserCredentialsWithEmail(email, locale);
    }
}
