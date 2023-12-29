package oga.microservice.athentification.config;


import lombok.Data;
import oga.microservice.athentification.httpRequests.LoginRequest;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class KeycloakConfig {

    @Value("${keycloak.auth-server-url}")
    public String serverURL;
    @Value("${keycloak.realm}")
    public String realm;
    @Value("${keycloak.resource}")
    public String clientID;
    @Value("${keycloak.credentials.secret}")
    public String clientSecret;

    @Value("${admin.username}")
    private String USERNAME;

    @Value("${admin.password}")
    private String PASSWORD;



    private static Keycloak keycloak = null;

    public Keycloak getInstance() {
        if (keycloak == null) {
            return KeycloakBuilder.builder()
                    .realm(realm)
                    .serverUrl(serverURL)
                    .clientId(clientID)
                    .clientSecret(clientSecret)
                    .grantType(OAuth2Constants.PASSWORD).
                    username(USERNAME).
                    password(PASSWORD)
                    .build();
        }
        return keycloak;
    }


    public KeycloakBuilder newKeycloakBuilderWithPasswordCredentials(LoginRequest loginRequest) {
        return KeycloakBuilder.builder()
                .realm(realm)
                .serverUrl(serverURL)
                .clientId(clientID)
                .clientSecret(clientSecret)
                .username(loginRequest.getUsername())
                .password(loginRequest.getPassword());
    }
}