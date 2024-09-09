package br.com.lemes.VLbank.model.agency;

import br.com.lemes.VLbank.model.account.Account;
import br.com.lemes.VLbank.model.bank.Bank;
import br.com.lemes.VLbank.model.user.Address;
import br.com.lemes.VLbank.record.agency.AgencyDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "agency")
@Data
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agency_number", nullable = false, unique = true)
    private String agencyNumber;

    @Embedded
    @Column(name = "address")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;

    public Agency() {}

    public Agency(AgencyDTO data, Bank bank) {
        this.agencyNumber = data.agencyNumber();
        this.address = new Address(data.addressDTO());
        this.bank = bank;
    }
}
