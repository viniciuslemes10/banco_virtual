package br.com.lemes.VLbank.repositories.agency;

import br.com.lemes.VLbank.model.agency.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {

    boolean existsByAgencyNumber(String agencyNumber);
}
