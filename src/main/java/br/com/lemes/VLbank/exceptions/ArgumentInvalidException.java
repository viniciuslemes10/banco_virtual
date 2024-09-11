package br.com.lemes.VLbank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ArgumentInvalidException extends RuntimeException {
    public ArgumentInvalidException(String message) {
        super(message);
    }
}
