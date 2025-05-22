package me.hwangjoonsoung.pefint.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUserFormException extends RuntimeException {
    public InvalidUserFormException(String message) {
        super(message);
    }
}
