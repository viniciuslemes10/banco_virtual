package br.com.lemes.VLbank.model.user;

import br.com.lemes.VLbank.record.address.AddressDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Address {
    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String complement;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String postalCode;

    public Address() {}

    public Address(AddressDTO data) {
        this.street = data.street();
        this.number = data.number();
        this.complement = data.complement();
        this.city = data.city();
        this.state = data.state();
        this.postalCode = data.postalCode();
    }
}
