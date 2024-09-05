package br.com.lemes.VLbank.model.account;

import br.com.lemes.VLbank.enums.account.AccountType;
import br.com.lemes.VLbank.model.bank.Agency;
import br.com.lemes.VLbank.model.bank.Bank;
import br.com.lemes.VLbank.record.AccountDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("SAVINGS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingsAccount extends Account{
    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "interest_frequency")
    private String interestFrequency;

    public SavingsAccount(AccountDTO data, Bank bank, Agency agency) {
        this.setAccountNumber(data.accountNumber());
        this.setBalance(data.balance());
        this.setAccountType(AccountType.SAVINGS);
        this.setBank(bank);
        this.setAgency(agency);
        this.setCreatedDate(LocalDateTime.now());
        this.setUpdatedDate(LocalDateTime.now());
        this.interestRate = BigDecimal.valueOf(0.008);
        this.interestFrequency = "MONTH";
    }
}
