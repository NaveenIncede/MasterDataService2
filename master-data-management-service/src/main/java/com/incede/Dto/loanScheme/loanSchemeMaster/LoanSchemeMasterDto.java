package com.incede.Dto.loanScheme.loanSchemeMaster;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanSchemeMasterDto {

    private Integer schemeId;

    @NotNull(message = "TenantId must not be null")
    private Integer tenantId;

    @NotBlank(message = "SchemeName must not be empty")
    private String schemeName;

    @NotBlank(message = "SchemeCode must not be empty")
    private String schemeCode;

    @NotNull(message = "ProductId must not be null")
    private Integer productId;

    private Boolean isEpi = false;

    @NotNull(message = "MinLoanAmount must not be null")
    private BigDecimal minLoanAmount;

    @NotNull(message = "MaxLoanAmount must not be null")
    private BigDecimal maxLoanAmount;

    @NotNull(message = "MaxInterestRate must not be null")
    private BigDecimal maxInterestRate;

    @NotNull(message = "EffectiveFrom must not be null")
    private LocalDate effectiveFrom;

    @NotNull(message = "EffectiveTo must not be null")
    private LocalDate effectiveTo;

    @NotNull(message = "LtvRate must not be null")
    private BigDecimal ltvRate;

    @NotNull(message = "MinInterestDays must not be null")
    private Integer minInterestDays;

    private Boolean slabDownAllowed = false;

    @NotNull(message = "AuthorisedBy must not be null")
    private Integer authorisedBy;

    @NotNull(message = "PenalInterestRate must not be null")
    private BigDecimal penalInterestRate;

    @NotBlank(message = "PenalCalculateOn must not be empty")
    private String penalCalculateOn;

    private LocalDateTime authorisedAt;

    private Boolean isActive = true;

    private UUID identity;

    @NotNull(message = "CreatedBy must not be null")
    private Integer createdBy;

}
