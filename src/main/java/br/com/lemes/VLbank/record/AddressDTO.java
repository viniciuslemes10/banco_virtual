package br.com.lemes.VLbank.record;

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
