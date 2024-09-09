package br.com.lemes.VLbank.record.agency;

import br.com.lemes.VLbank.record.address.AddressDTO;

public record AgencyDTO(
        String agencyNumber,
        AddressDTO addressDTO,
        Long bankId
) {
}
