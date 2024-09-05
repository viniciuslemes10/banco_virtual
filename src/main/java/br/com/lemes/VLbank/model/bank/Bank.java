package br.com.lemes.VLbank.model.bank;

import br.com.lemes.VLbank.enums.bank.BankCode;
import br.com.lemes.VLbank.record.bank.BankDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "banks")
@Data
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "bank_code", unique = true, nullable = false)
    private BankCode bankCode;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Agency> agencies;

    public Bank(BankDTO data) {
        this.bankCode = data.bankCode();
    }
}
