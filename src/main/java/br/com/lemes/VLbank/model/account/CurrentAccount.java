package br.com.lemes.VLbank.model.account;

import br.com.lemes.VLbank.enums.account.AccountType;
import br.com.lemes.VLbank.model.bank.Agency;
import br.com.lemes.VLbank.model.bank.Bank;
import br.com.lemes.VLbank.record.account.AccountDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("CURRENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrentAccount extends Account {
    /**
     * Limite de crédito disponível para a conta corrente.
     * Este valor define o montante máximo que o titular da conta pode
     * sacar além do saldo atual. Quando o saldo da conta corrente atinge
     *  * zero, o titular ainda pode realizar transações até o limite de
     *  * crédito definido.
     */
    @Column(name = "account_limit")
    private BigDecimal accountLimit;

    public CurrentAccount(AccountDTO data, Bank bank, Agency agency) {
        this.setAccountNumber(data.accountNumber());
        this.setBalance(data.balance());
        this.setAccountType(AccountType.CURRENT);
        this.setBank(bank);
        this.setAgency(agency);
        this.setCreatedDate(LocalDateTime.now());
        this.setUpdatedDate(LocalDateTime.now());
        this.accountLimit = new BigDecimal(100);
    }
}
