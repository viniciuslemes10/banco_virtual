package br.com.lemes.VLbank.repositories.transaction;

import br.com.lemes.VLbank.model.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
