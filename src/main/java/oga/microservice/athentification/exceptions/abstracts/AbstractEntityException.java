package oga.microservice.athentification.exceptions.abstracts;

import lombok.Getter;
import oga.microservice.athentification.validator.enums.ErrorCodes;

import java.util.List;

@Getter
public abstract class AbstractEntityException extends Exception {
    @Getter
    private ErrorCodes errorCodes ;
    @Getter
    private List<String> errors;


    public AbstractEntityException(String message , ErrorCodes errorCodes , List<String> errors){
        super(message);
        this.errorCodes = errorCodes;
        this.errors = errors;
    }

    public AbstractEntityException(String message , ErrorCodes errorCodes){
        super(message);
        this.errorCodes = errorCodes;
    }
}
