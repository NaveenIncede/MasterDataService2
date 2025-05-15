package com.incede.Dto.loanTransaction.loanTransactionEngine;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanTransactionDTO {
    private Integer schemeId;
    private BigDecimal principalAmount;
    private BigDecimal interestAmount;
    private BigDecimal penalAmount;
    private BigDecimal feesAmount;
    private Integer tenantId;
    private Integer createdBy;
}   
