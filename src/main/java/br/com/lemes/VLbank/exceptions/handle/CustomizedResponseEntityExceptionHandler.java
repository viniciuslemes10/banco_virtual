package br.com.lemes.VLbank.exceptions.handle;

import br.com.lemes.VLbank.exceptions.ArgumentInvalidException;
import br.com.lemes.VLbank.exceptions.agency.AgencyAlreadyExistsException;
import br.com.lemes.VLbank.exceptions.ExceptionResponse;
import br.com.lemes.VLbank.exceptions.account.AccountNotFoundException;
import br.com.lemes.VLbank.exceptions.account.AccountNumberAlreadyExistsException;
import br.com.lemes.VLbank.exceptions.account.InvalidEnumArgumentException;
import br.com.lemes.VLbank.exceptions.account.InvalidBalanceException;
import br.com.lemes.VLbank.exceptions.agency.AgencyNotFoundException;
import br.com.lemes.VLbank.exceptions.bank.BankNotFoundException;
import br.com.lemes.VLbank.exceptions.bank.InvalidBankCodeException;
import br.com.lemes.VLbank.exceptions.transaction.InsufficientFundsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@Slf4j
public class CustomizedResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleAccountNotFoundException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidEnumArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidArgumentForAccountTypeException(
            Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        log.debug("Handling InvalidArgumentForAccountTypeException");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BankNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleBankNotFoundException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AgencyNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleAgencyNotFoundException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountNumberAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleAccountNumberAlreadyExistsException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBalanceException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidBalanceException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBankCodeException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidBankCodeException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AgencyAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleAgencyAlreadyExistsException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ArgumentInvalidException.class)
    public ResponseEntity<ExceptionResponse> handleArgumentInvalidException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ExceptionResponse> handleInsufficientFundsException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
