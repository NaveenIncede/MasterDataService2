package com.incede.Dto.deductionMaster;

import java.math.BigDecimal;
import java.util.UUID;

import com.incede.enums.deductionTypes.DeductionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductionMasterDto {


    private Integer deductionId;
    private Integer tenantId;
    private String deductionName;
    private DeductionType deductionType;
    private BigDecimal amountOrPercent;
    private Boolean isActive;
    private UUID identity;
    private Integer createdBy;
    
}
