package br.com.lemes.VLbank.service;

import br.com.lemes.VLbank.exceptions.BankNotFoundException;
import br.com.lemes.VLbank.model.bank.Bank;
import br.com.lemes.VLbank.repositories.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    public Bank findById(Long id) {
       return bankRepository.findById(id)
               .orElseThrow(() -> new BankNotFoundException("Bank not found for this ID!"));
    }
}
