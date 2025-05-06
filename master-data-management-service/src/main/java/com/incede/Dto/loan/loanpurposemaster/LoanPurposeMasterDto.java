package com.incede.Dto.loan.loanpurposemaster;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanPurposeMasterDto {
    private Integer tenantId;
    private String purposeName;
    private String purposeCode;
    private String purposeDescription;
    private Boolean isActive;
    private Integer createdBy;
    private Integer updatedBy;
    private Boolean isDeleted;
    private UUID identity;
}
