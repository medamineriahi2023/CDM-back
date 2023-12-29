package oga.microservice.athentification.httpRequests;

import lombok.Data;

@Data
public class LoginRequest {
private String username;
private String password;
}
