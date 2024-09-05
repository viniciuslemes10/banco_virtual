package br.com.lemes.VLbank.service.agency;

import br.com.lemes.VLbank.exceptions.agency.AgencyNotFoundException;
import br.com.lemes.VLbank.model.bank.Agency;
import br.com.lemes.VLbank.repositories.agency.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgencyService {

    @Autowired
    private AgencyRepository agencyRepository;

    public Agency findById(Long id) {
        return agencyRepository.findById(id)
                .orElseThrow(() -> new AgencyNotFoundException("Agency not found for this ID!"));
    }
}
