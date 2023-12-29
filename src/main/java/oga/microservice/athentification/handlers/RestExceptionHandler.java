package oga.microservice.athentification.handlers;

import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.exceptions.InvalidEntityException;
import oga.microservice.athentification.exceptions.UnauthorizedException;
import oga.microservice.athentification.utils.SQLExceptionsUtils;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private SQLExceptionsUtils _sqlExceptionsUtils;
    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<ErrorDTO> handleException(InvalidEntityException exception , WebRequest webRequest){
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorDTO error =  ErrorDTO.builder()
                .errorCodes(exception.getErrorCodes())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errors(exception.getErrors())
                .build();
        return new ResponseEntity<>(error , badRequest);

    }


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDTO> handleException(UnauthorizedException exception , WebRequest webRequest){
        final HttpStatus badRequest = HttpStatus.UNAUTHORIZED;
        ErrorDTO error =  ErrorDTO.builder()
                .errorCodes(exception.getErrorCodes())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errors(exception.getErrors())
                .build();
        return new ResponseEntity<>(error , badRequest);

    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleException(EntityNotFoundException exception , WebRequest webRequest){
        final HttpStatus badRequest = HttpStatus.NOT_FOUND;
        ErrorDTO error =  ErrorDTO.builder()
                .errorCodes(exception.getErrorCodes())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errors(exception.getErrors())
                .build();
        return new ResponseEntity<>(error , badRequest);

    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorDTO> handleException(SQLException exception , WebRequest webRequest){
        String message = _sqlExceptionsUtils.parseException(exception.getMessage());
        ErrorDTO error =  ErrorDTO.builder()
                .errorCodes(ErrorCodes.SQL_EXCEPTION)
                .httpCode(500)
                .errors(new ArrayList<>(Collections.singleton(message)))
                .message(message)
                .build();
        return new ResponseEntity<>(error , HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
