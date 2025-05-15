package com.incede.Model.schemeinterestrate;
	import com.incede.Model.baseentity.BaseEntity;
	import jakarta.persistence.*;
	import lombok.Data;
	import lombok.EqualsAndHashCode;
	import org.hibernate.annotations.GenericGenerator;

	import java.math.BigDecimal;
	import java.time.LocalDate;
import java.util.UUID;

	@Entity
	@Table(name = "interest_rate", schema = "master_data") 
	@Data
	@EqualsAndHashCode(callSuper = true)
	public class SchemeInterestRate extends BaseEntity {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "interest_rate_id")
	    private Integer interestRateId;

	    @Column(name = "tenant_id", nullable = false)
	    private Integer tenantId;

	    @Column(name = "scheme_id", nullable = false)
	    private Integer schemeId;

	    @Column(name = "from_amount")
	    private BigDecimal fromAmount;

	    @Column(name = "upto_amount")
	    private BigDecimal uptoAmount;

	    @Column(name = "from_period")
	    private BigDecimal fromPeriod;

	    @Column(name = "upto_period")
	    private BigDecimal uptoPeriod;

	    @Column(name = "period_type", nullable = false)
	    private String periodType;

	    @Column(name = "interest_rate", nullable = false)
	    private BigDecimal interestRate;

	    @Column(name = "effective_from", nullable = false)
	    private LocalDate effectiveFrom;

	    @Column(name = "effective_upto")
	    private LocalDate effectiveUpto;

	    @Column(name = "recalc_interest", columnDefinition = "BOOLEAN DEFAULT FALSE")
	    private Boolean recalcInterest = false;

	    @Column(name = "loan_past_due", columnDefinition = "BOOLEAN DEFAULT FALSE")
	    private Boolean loanPastDue = false;

	    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE")
	    private Boolean isActive = true;

	    @Column(name = "identity", nullable = false, unique = true)
	    private UUID identity;
	    
	}



