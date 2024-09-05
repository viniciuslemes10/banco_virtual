package br.com.lemes.VLbank.exceptions.bank;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidBankCodeException extends RuntimeException {
    public InvalidBankCodeException(String message) {
        super(message);
    }
}
