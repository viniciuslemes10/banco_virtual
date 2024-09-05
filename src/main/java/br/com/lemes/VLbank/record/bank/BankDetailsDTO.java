package br.com.lemes.VLbank.record.bank;

import br.com.lemes.VLbank.enums.bank.BankCode;
import br.com.lemes.VLbank.model.bank.Bank;

public record BankDetailsDTO(
        Long id,
        BankCode bankCode
) {
    public BankDetailsDTO(Bank bank) {
        this(bank.getId(), bank.getBankCode());
    }
}
