package com.incede.Model.loanTransaction.loanTransactionEngine;

import com.incede.Model.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loan_transaction")
public class LoanTransaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionId;

    @Column(name = "scheme_id", nullable = false)
    private Integer schemeId;

    @Column(name = "principal_amount", nullable = false)
    private BigDecimal principalAmount;

    @Column(name = "interest_amount", nullable = false)
    private BigDecimal interestAmount;

    @Column(name = "penal_amount", nullable = false)
    private BigDecimal penalAmount;

    @Column(name = "fees_amount", nullable = false)
    private BigDecimal feesAmount;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "identity", nullable = false, unique = true)
    private UUID identity;
}
