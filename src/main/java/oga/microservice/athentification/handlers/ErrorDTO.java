package oga.microservice.athentification.handlers;

import lombok.*;
import oga.microservice.athentification.validator.enums.ErrorCodes;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTO {
    private Integer httpCode;
    private ErrorCodes errorCodes;
    private String message;
    private List<String> errors = new ArrayList<>();
}
