package br.com.lemes.VLbank.record.address;

import br.com.lemes.VLbank.model.user.Address;

public record AddressDTO(
        String street,
        String number,
        String city,
        String complement,
        String state,
        String postalCode
) {
    public AddressDTO(Address address) {
        this(address.getStreet(), address.getNumber(),
                address.getCity(), address.getComplement(), address.getState(),
                address.getPostalCode());
    }
}
