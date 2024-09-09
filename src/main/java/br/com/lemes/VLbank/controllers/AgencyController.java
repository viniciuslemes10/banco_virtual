package br.com.lemes.VLbank.controllers;

import br.com.lemes.VLbank.record.agency.AgencyDTO;
import br.com.lemes.VLbank.record.agency.AgencyDetailsDTO;
import br.com.lemes.VLbank.service.agency.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/vlbank/agency")
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgencyDetailsDTO> createAgency(@RequestBody AgencyDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agencyService.create(data));
    }
}
