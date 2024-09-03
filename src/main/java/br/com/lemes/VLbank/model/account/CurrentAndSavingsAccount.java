package br.com.lemes.VLbank.model.account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("CURRENT_AND_SAVINGS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrentAndSavingsAccount extends Account {

    @Column(name = "current_balance")
    private Double currentBalance;

    @Column(name = "savings_balance")
    private Double savingsBalance;

    @ManyToOne
    @JoinColumn(name = "transaction_fee_id")
    private TransactionFee transactionFee;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "interest_frequency")
    private String interestFrequency;
}
