package br.com.lemes.VLbank.controllers.bank;

import br.com.lemes.VLbank.record.bank.BankDTO;
import br.com.lemes.VLbank.record.bank.BankDetailsDTO;
import br.com.lemes.VLbank.service.bank.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/vlbank/bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BankDetailsDTO> createBank(@RequestBody BankDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bankService.create(data));
    }
}
