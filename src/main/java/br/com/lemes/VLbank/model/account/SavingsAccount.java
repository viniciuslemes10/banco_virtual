package br.com.lemes.VLbank.model.account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("SAVINGS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingsAccount extends Account{
    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "interest_frequency")
    private String interestFrequency;

    @ManyToOne
    @JoinColumn(name = "transaction_fee_id")
    private TransactionFee transactionFee;
}
