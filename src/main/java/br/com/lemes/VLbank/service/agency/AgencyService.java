package br.com.lemes.VLbank.service.agency;

import br.com.lemes.VLbank.exceptions.agency.AgencyAlreadyExistsException;
import br.com.lemes.VLbank.exceptions.agency.AgencyNotFoundException;
import br.com.lemes.VLbank.model.agency.Agency;
import br.com.lemes.VLbank.record.agency.AgencyDTO;
import br.com.lemes.VLbank.record.agency.AgencyDetailsDTO;
import br.com.lemes.VLbank.repositories.agency.AgencyRepository;
import br.com.lemes.VLbank.service.bank.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgencyService {

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private BankService bankService;

    public Agency findById(Long id) {
        return agencyRepository.findById(id)
                .orElseThrow(() -> new AgencyNotFoundException("Agency not found for this ID!"));
    }

    public AgencyDetailsDTO create(AgencyDTO data) {
        var bank = bankService.findById(data.bankId());
        var agency = new Agency(data, bank);
        existsByAgencyNumberAndAddress(data.agencyNumber());
        agencyRepository.save(agency);
        return new AgencyDetailsDTO(agency);
    }

    private void existsByAgencyNumberAndAddress(String agencyNumber) {
        var isExist = agencyRepository.existsByAgencyNumber(agencyNumber);
        if(isExist) {
            throw new AgencyAlreadyExistsException("An agency with the given number and address already exists.");
        }
    }
}
