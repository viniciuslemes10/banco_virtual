package br.com.lemes.VLbank.model.user;

import br.com.lemes.VLbank.model.account.Account;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Account> account;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "cpf_or_cnpj")
    private String cpfOrCnpj;

    @Column(name = "account_not_expired")
    private Boolean accountNotExpired;

    @Column(name = "account_not_blocked")
    private Boolean accountNotBlocked;

    @Column(name = "credentials_not_expired")
    private Boolean credentialsNotExpired;

    @Column(name = "enable")
    private Boolean enabled;

    @Column(name = "recovery_code")
    private String recoveryCode;
}
