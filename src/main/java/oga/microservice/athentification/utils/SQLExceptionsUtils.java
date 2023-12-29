package oga.microservice.athentification.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class SQLExceptionsUtils {
    @Autowired
    private MessageSource _messageSource;

    public String parseException(String error) {
        if (error == null) {
            return null;
        }
        String message = error;
            if (error.startsWith("Duplicate entry")) { // Unique constraint
                int indexStart = error.indexOf("'");
                int indexEnd = error.indexOf("'", indexStart + 1);
                if (indexStart < indexEnd) {
                    String value = error.substring(indexStart + 1, indexEnd);
                    message = value + this._messageSource.getMessage("exceptions.sql.duplicate", null, new Locale("fr"));
                }

            } else if (error.contains("cannot be null")) { // Non null constraint
                String[] lines = error.split("\n");
                if (lines.length > 0) {
                    message = lines[0];
                }
            }else if (error.startsWith("Data truncation")){
                int indexStart = error.indexOf("'");
                int indexEnd = error.indexOf("'", indexStart + 1);
                if (indexStart < indexEnd) {
                    String value = error.substring(indexStart + 1, indexEnd);
                    message = value +" "+ this._messageSource.getMessage("exceptions.sql.length", null, new Locale("fr"));
                }
                }
        return message;
    }
}
