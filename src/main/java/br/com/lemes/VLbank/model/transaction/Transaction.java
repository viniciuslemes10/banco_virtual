package br.com.lemes.VLbank.model.transaction;

import br.com.lemes.VLbank.enums.transactions.ExpenseType;
import br.com.lemes.VLbank.model.account.Account;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(name = "expense_type", nullable = false )
    private ExpenseType expenseType;

    @Column(name = "description")
    private String description;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;
}
