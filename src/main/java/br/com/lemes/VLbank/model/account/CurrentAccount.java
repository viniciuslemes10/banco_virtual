package br.com.lemes.VLbank.model.account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("CURRENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrentAccount extends Account {
    /**
     * Limite de crédito disponível para a conta corrente.
     * Este valor define o montante máximo que o titular da conta pode
     * sacar além do saldo atual. Quando o saldo da conta corrente atinge
     *  * zero, o titular ainda pode realizar transações até o limite de
     *  * crédito definido.
     */
    @Column(name = "account_limit")
    private Double accountLimit;

    /**
     * Referência à taxa de transação associada a esta conta.
     * Esta taxa pode ser um valor fixo ou percentual aplicado a
     * operações realizadas na conta. Permite a configuração flexível
     * das taxas de transação e pode variar conforme o tipo de conta
     * ou política do banco.
     */
    @ManyToOne
    @JoinColumn(name = "transaction_fee_id")
    private TransactionFee transactionFee;
}
