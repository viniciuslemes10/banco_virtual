package br.com.lemes.VLbank.record.agency;

import br.com.lemes.VLbank.model.agency.Agency;
import br.com.lemes.VLbank.record.address.AddressDTO;

public record AgencyDetailsDTO(
        Long id,
        String agencyNumber,
        AddressDTO addressDTO,
        Long bankId
) {
    public AgencyDetailsDTO(Agency agency) {
        this(agency.getId(), agency.getAgencyNumber(), new AddressDTO(agency.getAddress()),
                agency.getBank().getId());
    }
}
