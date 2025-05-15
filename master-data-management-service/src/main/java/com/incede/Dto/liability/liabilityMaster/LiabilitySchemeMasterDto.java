package com.incede.Dto.liability.liabilityMaster;

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
public class LiabilitySchemeMasterDto {

    private Integer schemeId; // optional for update

    @NotBlank(message = "Scheme name must not be empty")
    private String schemeName;

    @NotBlank(message = "Scheme code must not be empty")
    private String schemeCode;

    @NotNull(message = "Product ID must not be null")
    private Integer productId;

    @NotNull(message = "Minimum deposit amount must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Minimum deposit amount must be greater than 0")
    private BigDecimal minDepositAmount;

    @NotNull(message = "Maximum deposit amount must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Maximum deposit amount must be greater than 0")
    private BigDecimal maxDepositAmount;

    @NotNull(message = "Maximum interest rate must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Maximum interest rate must be greater than 0")
    private BigDecimal maxInterestRate;

    @NotNull(message = "Effective from date must not be null")
    private LocalDate effectiveFrom;

    @NotNull(message = "Effective to date must not be null")
    private LocalDate effectiveTo;

    @NotNull(message = "Multiples of must not be null")
    @DecimalMin(value = "1.0", message = "Multiples of must be at least 1")
    private BigDecimal multiplesOf;

    @NotNull(message = "Deposit period must not be null")
    @Min(value = 1, message = "Deposit period must be at least 1")
    private Integer depositPeriod;

    @NotBlank(message = "Period type must not be empty")
    private String periodType;

    @NotNull(message = "Special interest rate must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Special interest rate must be non-negative")
    private BigDecimal specialInterestRate;

    @NotBlank(message = "Interest accrual posting frequency must not be empty")
    private String interestAccrualPostingFrequency;

    @NotBlank(message = "Compound frequency must not be empty")
    private String compoundFrequency;

    @NotBlank(message = "Interest payment frequency must not be empty")
    private String interestPaymentFrequency;

    private Boolean putOptionAvailable = true;

    private Integer putOptionAfter;

    private String putOptionPeriodType;

    private Integer authorisedBy;

    private LocalDateTime authorisedAt;

    private Boolean isActive = true;

    private UUID identity;

    @NotNull(message = "CreatedBy must not be null for new records")
    private Integer createdBy;
}
