package br.com.lemes.VLbank.model.bank;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "agency")
@Data
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agency_number", nullable = false, unique = true)
    private String agencyNumber;

    @Column(name = "address")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;
}
