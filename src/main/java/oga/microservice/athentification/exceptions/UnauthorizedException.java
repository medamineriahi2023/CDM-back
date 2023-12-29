package oga.microservice.athentification.exceptions;

import oga.microservice.athentification.exceptions.abstracts.AbstractEntityException;
import oga.microservice.athentification.validator.enums.ErrorCodes;

import java.util.List;

public class UnauthorizedException extends AbstractEntityException {


    public UnauthorizedException(String message , ErrorCodes errorCodes , List<String> errors){
        super(message, errorCodes, errors);
    }

    public UnauthorizedException(String message , ErrorCodes errorCodes){
        super(message,errorCodes);
    }

}
