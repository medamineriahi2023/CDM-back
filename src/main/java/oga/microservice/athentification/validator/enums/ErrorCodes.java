package oga.microservice.athentification.validator.enums;

public enum ErrorCodes {
    ENTITY_NOT_FOUND(1000),

    UNAUTHORIZED_USER(1004),

    USER_ALREADY_EXIST(1005),
    USER_NOT_VALID(1001),

    SQL_EXCEPTION(1003);

    private int id;

    ErrorCodes(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
}
