package br.com.lemes.VLbank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AgencyAlreadyExistsException extends RuntimeException {
    public AgencyAlreadyExistsException(String message) {
        super(message);
    }
}
