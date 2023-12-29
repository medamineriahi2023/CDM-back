package oga.microservice.athentification.httpRequests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;


}
