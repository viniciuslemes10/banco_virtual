package br.com.lemes.VLbank.record.account;

public record AddressDTO(
        String street,
        String number,
        String neighborhood,
        String city,
        String complement,
        String state,
        String postalCode
) {
}
