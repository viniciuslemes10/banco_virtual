package br.com.lemes.VLbank.repositories;

import br.com.lemes.VLbank.model.bank.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {
}
