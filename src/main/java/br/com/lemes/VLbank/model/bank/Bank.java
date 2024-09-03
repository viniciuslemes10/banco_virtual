package br.com.lemes.VLbank.model.bank;

import br.com.lemes.VLbank.enums.bank.BankCode;
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
    @Column(name = "bank_code", nullable = false)
    private BankCode bankCode;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Agency> agencies;

}
