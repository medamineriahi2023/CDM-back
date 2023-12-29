package oga.microservice.athentification.service;

import oga.microservice.athentification.entities.User;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.exceptions.InvalidEntityException;
import oga.microservice.athentification.exceptions.UnauthorizedException;
import oga.microservice.athentification.httpRequests.LoginRequest;
import org.keycloak.email.EmailException;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

public interface IAuthenticationService {
    ResponseEntity<AccessTokenResponse> login(LoginRequest loginRequest, Locale locale) throws InvalidEntityException, UnauthorizedException;

    ResponseEntity<User> createUser(User user, Locale locale) throws InvalidEntityException;

    void GenerateAndSendUserCredentials(String userId) throws EmailException;


    void GenerateAndSendUserCredentialsWithEmail(String email, Locale locale) throws EmailException, EntityNotFoundException;

}
