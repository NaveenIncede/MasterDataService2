package com.incede.Model.liabityInterest.liabilityInterestMaster;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.incede.Model.baseentity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "interest_rate", indexes = {
	    @Index(name = "idx_scheme_interest", columnList = "scheme_id"),
	    @Index(name = "idx_effective_dates", columnList = "effective_from, effective_upto"),
	    @Index(name = "idx_period_range", columnList = "from_period, upto_period")
	   
	})

public class LiabilityInterestRateMaster extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "interest_rate_id")

	private Integer interestRateId;  

	@Column(name = "scheme_id", nullable = false)
	private Integer schemeId;

	@Column(name = "interest_rate", nullable = false)
	private BigDecimal interestRate;

	@Column(name = "interest_description", nullable = false)
	private String interestDescription;

	@Column(name = "from_period")
	private BigDecimal fromPeriod;
// precision = 10, scale = 2
	@Column(name = "upto_period")
	private BigDecimal uptoPeriod;

	@Column(name = "period_type", nullable = false)
	private String periodType;

	@Column(name = "effective_from", nullable = false)
	private LocalDate effectiveFrom;

	@Column(name = "effective_upto")
	private LocalDate effectiveUpto;

	
	@Column(name = "is_active")
	private boolean isactive;
	
	
	
	@Column(name="is_Archived")
	private Boolean isArchived = false;	
}
