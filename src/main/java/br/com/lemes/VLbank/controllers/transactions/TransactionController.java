package br.com.lemes.VLbank.controllers.transactions;

import br.com.lemes.VLbank.record.transaction.TransactionDTO;
import br.com.lemes.VLbank.record.transaction.TransactionDetailsDTO;
import br.com.lemes.VLbank.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vlbank/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionDetailsDTO> createTransaction(@RequestBody TransactionDTO data) {
        var transactionDTO = transactionService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionDTO);
    }
}
