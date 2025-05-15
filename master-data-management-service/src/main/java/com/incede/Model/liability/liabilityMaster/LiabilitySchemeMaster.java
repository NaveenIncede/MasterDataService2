package com.incede.Model.liability.liabilityMaster;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.incede.Model.baseentity.BaseEntity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "liability_scheme_master", schema = "master_data")
public class LiabilitySchemeMaster extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scheme_id")
    private Integer schemeId;

    @Column(name = "scheme_name", nullable = false)
    private String schemeName;

    @Column(name = "scheme_code", nullable = false)
    private String schemeCode;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "min_deposit_amount", nullable = false)
    private BigDecimal minDepositAmount;

    @Column(name = "max_deposit_amount", nullable = false)
    private BigDecimal maxDepositAmount;

    @Column(name = "max_interest_rate", nullable = false)
    private BigDecimal maxInterestRate;

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    @Column(name = "multiples_of", nullable = false)
    private BigDecimal multiplesOf;

    @Column(name = "deposit_period", nullable = false)
    private Integer depositPeriod;

    @Column(name = "period_type", nullable = false)
    private String periodType;

    @Column(name = "special_interest_rate", nullable = false)
    private BigDecimal specialInterestRate;

    @Column(name = "interest_accrual_posting_frequency", nullable = false)
    private String interestAccrualPostingFrequency;

    @Column(name = "compound_frequency", nullable = false)
    private String compoundFrequency;

    @Column(name = "interest_payment_frequency", nullable = false)
    private String interestPaymentFrequency;

    @Column(name = "put_option_available", nullable = false)
    private Boolean putOptionAvailable = true;

    @Column(name = "put_option_after", nullable = false)
    private Integer putOptionAfter;

    @Column(name = "put_option_period_type", nullable = false)
    private String putOptionPeriodType;

    @Column(name = "authorised_by", nullable = false)
    private Integer authorisedBy;

    @Column(name = "authorised_at", nullable = false)
    private LocalDateTime authorisedAt = LocalDateTime.now();

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "identity", nullable = false, unique = true)
    private UUID identity;

    // Getters and setters
}
