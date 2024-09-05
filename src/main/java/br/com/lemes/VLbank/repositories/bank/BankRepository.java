package br.com.lemes.VLbank.repositories.bank;

import br.com.lemes.VLbank.model.bank.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
}
