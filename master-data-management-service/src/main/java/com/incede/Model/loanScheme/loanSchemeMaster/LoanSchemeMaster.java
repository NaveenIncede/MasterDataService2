package com.incede.Model.loanScheme.loanSchemeMaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.incede.Model.baseentity.BaseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loan_scheme_master")
public class LoanSchemeMaster extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scheme_id")
    private Integer schemeId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "scheme_name", nullable = false)
    private String schemeName;

    @Column(name = "scheme_code", nullable = false)
    private String schemeCode;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "is_epi", nullable = false)
    private Boolean isEpi;

    @Column(name = "min_loan_amount", nullable = false)
    private BigDecimal minLoanAmount;

    @Column(name = "max_loan_amount", nullable = false)
    private BigDecimal maxLoanAmount;

    @Column(name = "max_interest_rate", nullable = false)
    private BigDecimal maxInterestRate;

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    @Column(name = "ltv_rate", nullable = false)
    private BigDecimal ltvRate;

    @Column(name = "min_interest_days", nullable = false)
    private Integer minInterestDays;

    @Column(name = "slab_down_allowed", nullable = false)
    private Boolean slabDownAllowed;

    @Column(name = "authorised_by", nullable = false)
    private Integer authorisedBy;

    @Column(name = "penal_interest_rate", nullable = false)
    private BigDecimal penalInterestRate;

    @Column(name = "penal_calculate_on", nullable = false)
    private String penalCalculateOn;

    @Column(name = "authorised_at", nullable = false, updatable = false)
    private LocalDateTime authorisedAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "identity", nullable = false, unique = true)
    private UUID identity;

}
