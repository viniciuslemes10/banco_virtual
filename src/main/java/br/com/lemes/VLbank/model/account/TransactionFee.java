package br.com.lemes.VLbank.model.account;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * Representa a taxa de transação associada a contas bancárias.
 * Esta classe é usada para armazenar informações sobre a taxa aplicada em transações financeiras.
 */
@Entity
@Table(name = "transaction_fee")
@Data
public class TransactionFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "percentage")
    private Double percentage;

    @Column(name = "description")
    private String description;

    @Column(name = "created")
    private LocalDateTime created;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}
