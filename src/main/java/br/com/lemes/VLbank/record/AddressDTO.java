package br.com.lemes.VLbank.record;

public record AddressDTO(
        String street,
        String number,
        String neighborhood,
        String city,
        String state,
        String postalCode
) {
}
