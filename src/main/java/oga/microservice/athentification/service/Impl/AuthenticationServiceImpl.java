package oga.microservice.athentification.service.Impl;

import oga.microservice.athentification.config.KeycloakConfig;
import oga.microservice.athentification.entities.User;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.exceptions.InvalidEntityException;
import oga.microservice.athentification.exceptions.UnauthorizedException;
import oga.microservice.athentification.httpRequests.LoginRequest;
import oga.microservice.athentification.service.IAuthenticationService;
import oga.microservice.athentification.validator.LoginRequestValidator;
import oga.microservice.athentification.validator.RegisterRequestValidator;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.email.EmailException;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.core.Response;
import java.util.*;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
    KeycloakConfig keycloakConfig;
    @Autowired
    UserService userService;
    MessageSource messageSource;

    @Value("${mail.smtp.host}")
    private String HOST;
    @Value("${mail.smtp.port}")
    private String PORT;
    @Value("${mail.smtp.auth}")
    private String AUTH;
    @Value("${mail.internet.address}")
    private String INTERNET_ADDRESS;
    @Value("${mail.header}")
    private String HEADER;
    @Value("${mail.protocol}")
    private String PROTOCOL;





    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
    public AuthenticationServiceImpl(KeycloakConfig keycloakConfig, MessageSource messageSource) {
        this.keycloakConfig = keycloakConfig;
        this.messageSource = messageSource;
    }

    @Override
    public ResponseEntity<AccessTokenResponse> login(LoginRequest loginRequest, Locale locale) throws InvalidEntityException, UnauthorizedException {
        LoginRequestValidator loginRequestValidator = new LoginRequestValidator(messageSource);
        Keycloak keycloak = null;
                List<String> errors = loginRequestValidator.validator(loginRequest, locale);
        if (!errors.isEmpty()){
            throw new InvalidEntityException(messageSource.getMessage("entity.invalid",null, locale),ErrorCodes.USER_NOT_VALID, errors);
        }
        try {
        keycloak = this.keycloakConfig.newKeycloakBuilderWithPasswordCredentials(loginRequest).build();
           keycloak.tokenManager().getAccessToken();
        }catch (Exception e){
            throw new UnauthorizedException(messageSource.getMessage("unauthorized.user", null , locale), ErrorCodes.UNAUTHORIZED_USER, new ArrayList<>(Collections.singleton(messageSource.getMessage("unauthorized.user", null , locale))));
        }
        return ResponseEntity.ok(keycloak.tokenManager().getAccessToken());
    }

    @Override
    public ResponseEntity<User> createUser(User user, Locale locale) throws InvalidEntityException {
        var registerRequestValidator = new RegisterRequestValidator(messageSource);
        var errors = registerRequestValidator.validator(user, locale);
        if (!errors.isEmpty()) {
            throw new InvalidEntityException(messageSource.getMessage("entity.invalid", null, locale),ErrorCodes.USER_NOT_VALID, errors);
        }else {

            UsersResource usersResource = keycloakConfig.getInstance().realm(keycloakConfig.getRealm()).users();
            User user1 = userService.create(user);
            CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());

            UserRepresentation kcUser = new UserRepresentation();
            kcUser.setUsername(user.getEmail());
            kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
            kcUser.setFirstName(user.getFirstname());
            kcUser.setLastName(user.getLastname());
            kcUser.setEmail(user.getEmail());
            kcUser.setEnabled(true);
            kcUser.setEmailVerified(false);
            Map<String, List<String>> map = new HashMap<>();
            map.put("userId", List.of(user1.getId().toString()));
            kcUser.setAttributes(map);

            Response response = usersResource.create(kcUser);

            if (response.getStatus() == 201) {
                List<UserRepresentation> userRepresentations = keycloakConfig.getInstance().realm(keycloakConfig.getRealm()).users().search(user.getEmail());
                user1.setUserId(userRepresentations.get(0).getId());
                userService.update(user1);
                return ResponseEntity.status(201).body(user1);
            }

           throw new InvalidEntityException(messageSource.getMessage("user.exist", null, locale), ErrorCodes.USER_ALREADY_EXIST);
        }
        }
    public void GenerateAndSendUserCredentials(String userId) throws EmailException{
        try {
            String password = this.pwGenerator();
            CredentialRepresentation credentialRepresentation = createPasswordCredentials(password);
            UserResource userResource = keycloakConfig.getInstance().realms().realm(keycloakConfig.getRealm()).users().get(userId);
            userResource.resetPassword(credentialRepresentation);
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", HOST);
            props.setProperty("mail.smtp.port", PORT);
            props.put("mail.smtp.auth", AUTH);
            Session session = Session.getInstance(props);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(INTERNET_ADDRESS));
            msg.setHeader("To", HEADER);
            msg.setSubject("Generated Password");
            msg.setContent("<html><body><div style='height : 70px ; width :100% ; background-color:lightGray'><img src='https://www.pngmart.com/files/4/Pepsi-Logo-PNG-Image.png' style='width: 100px; height: auto'</div><div style='width : 100% ; background-color: #ADD8E6'><h3 style='color : green'>" +
                    "your new password is : "+ password +"</h3></div></body></html>", "text/html");
            msg.saveChanges();
            msg.setSentDate(new Date());
            Transport transport = session.getTransport(PROTOCOL);
            transport.connect();
            transport.sendMessage(msg, new InternetAddress[]{new InternetAddress(userResource.toRepresentation().getEmail())});
        }catch (Exception e) {
            System.out.println("email not sent " + e.getMessage());
        throw new EmailException(e);
        }
    }

    @Override
    public void GenerateAndSendUserCredentialsWithEmail(String email, Locale locale) throws EmailException,EntityNotFoundException {
        String password = this.pwGenerator();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(password);
        List<UserRepresentation>  userRepresentations = keycloakConfig.getInstance().realms().realm(keycloakConfig.getRealm()).users().search(email);
            if (userRepresentations.isEmpty()){
                String message = messageSource.getMessage("email.not.found", null, locale);
                throw new EntityNotFoundException(message, ErrorCodes.ENTITY_NOT_FOUND , new ArrayList<>(Collections.singleton(message)));
            }

        try {
            UserResource userResource = keycloakConfig.getInstance().realms().realm(keycloakConfig.getRealm()).users().get(userRepresentations.get(0).getId());
            userResource.resetPassword(credentialRepresentation);
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", HOST);
            props.setProperty("mail.smtp.port", PORT);
            props.put("mail.smtp.auth", AUTH);
            Session session = Session.getInstance(props);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(INTERNET_ADDRESS));
            msg.setHeader("To", HEADER);
            msg.setSubject("Generated Password");
            msg.setContent("<html><body><div style='height : 70px ; width :100% ; background-color:lightGray'><img src='https://www.pngmart.com/files/4/Pepsi-Logo-PNG-Image.png' style='width: 100px; height: auto'</div><div style='width : 100% ; background-color: #ADD8E6'><h3 style='color : green'>" +
                    "your new password is : "+ password +"</h3></div></body></html>", "text/html");
            msg.saveChanges();
            msg.setSentDate(new Date());
            Transport transport = session.getTransport(PROTOCOL);
            transport.connect();
            transport.sendMessage(msg, new InternetAddress[]{new InternetAddress(userResource.toRepresentation().getEmail())});
        }catch (Exception e) {
            System.out.println("email not sent " + e.getMessage());
            throw new EmailException(e);
        }
    }

    private String pwGenerator() {
        return new Random().ints(10, 65, 122).collect(StringBuilder::new,
                        StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
