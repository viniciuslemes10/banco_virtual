package br.com.lemes.VLbank.service.bank;

import br.com.lemes.VLbank.enums.bank.BankCode;
import br.com.lemes.VLbank.exceptions.bank.BankNotFoundException;
import br.com.lemes.VLbank.exceptions.bank.InvalidBankCodeException;
import br.com.lemes.VLbank.model.bank.Bank;
import br.com.lemes.VLbank.record.bank.BankDTO;
import br.com.lemes.VLbank.record.bank.BankDetailsDTO;
import br.com.lemes.VLbank.repositories.bank.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    public Bank findById(Long id) {
       return bankRepository.findById(id)
               .orElseThrow(() -> new BankNotFoundException("Bank not found for this ID!"));
    }

    public BankDetailsDTO create(BankDTO data) {
        checkIfBankCodeIsValid(data);
        checkIfBankExistsByBankCode(data.bankCode());
        var bank = new Bank(data);
        var bankCreated = bankRepository.save(bank);
        return new BankDetailsDTO(bankCreated);
    }

    private void checkIfBankExistsByBankCode(BankCode bankCode) {
        var existByBankCode = bankRepository.existsByBankCode(bankCode);
        if(existByBankCode) throw new InvalidBankCodeException("The bank code is invalid.");
    }

    private void checkIfBankCodeIsValid(BankDTO data) {
        String bankCode = data.bankCode().getCode();

        boolean isValid = EnumSet.allOf(BankCode.class)
                .stream()
                .anyMatch(bankCodeEnum -> bankCodeEnum.getCode().equals(bankCode));

        if(!isValid) {
            throw new InvalidBankCodeException("The bank code is invalid.");
        }
    }
}
