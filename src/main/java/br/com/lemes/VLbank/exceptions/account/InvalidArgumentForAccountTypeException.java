package br.com.lemes.VLbank.exceptions.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidArgumentForAccountTypeException extends RuntimeException {
    public InvalidArgumentForAccountTypeException(String message) {
        super(message);
    }
}
