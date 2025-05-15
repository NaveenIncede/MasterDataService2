package com.incede.Dto.liabilityInterest.liabilityInterestRateMaster;


import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiabilityInterestRateDto {
	
	  private Integer interestRateId;
	    private Integer schemeId;
	    private BigDecimal interestRate;
	    private String interestDescription;
	    private BigDecimal fromPeriod;
	    private BigDecimal uptoPeriod;
	    private String periodType;
	    private LocalDate effectiveFrom;
	    private LocalDate effectiveUpto;
	    private Integer createdBy;
	    private boolean isArchived;
		private boolean isactive ;
	
}