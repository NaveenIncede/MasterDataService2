
package com.incede.Dto.schemeinterestrate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class SchemeInterestRateDto {

    private Integer interestRateId;
    private Integer tenantId;
    private Integer schemeId;
    private BigDecimal fromAmount;
    private BigDecimal uptoAmount;
    private BigDecimal fromPeriod;
    private BigDecimal uptoPeriod;
    private String periodType;
    private BigDecimal interestRate;
    private LocalDate effectiveFrom;
    private LocalDate effectiveUpto;
    private Boolean recalcInterest;
    private Boolean loanPastDue;
    private Boolean isActive;
    private UUID identity;
    private Integer createdBy;
    //private LocalDate createdAt;
    private Integer updatedBy;
   // private LocalDate updatedAt;
   

}
