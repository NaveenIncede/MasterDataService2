package com.incede.Model.deductionMaster;

import java.math.BigDecimal;
import java.util.UUID;

import com.incede.Model.baseentity.BaseEntity;
import com.incede.enums.deductionTypes.DeductionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "deduction", uniqueConstraints = { @UniqueConstraint(name = "uk_tenant_deduction_name", columnNames = {
		"tenant_id", "deduction_name" }) }, 
	indexes = { 
				@Index(name = "idx_tenant_id", columnList = "tenant_id"),
				@Index(name = "idx_is_active", columnList = "is_active"),
				@Index(name = "idx_deduction_type", columnList = "deduction_type")
				
				})
@Data
@NoArgsConstructor
@AllArgsConstructor 

public class DeductionMaster extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "deduction_id")
	private Integer deductionId;

	@Column(name = "tenant_id", nullable = false)
	private Integer tenantId;

	@Column(name = "deduction_name", nullable = false)
	private String deductionName;

	@Enumerated(EnumType.STRING)
	@Column(name = "deduction_type", nullable = false)
	private DeductionType deductionType;


	@Column(name = "amount_or_percent", nullable = false)
	private BigDecimal amountOrPercent;

	@Column(name = "is_active")
	private Boolean isActive = true;

	@Column(name = "identity", nullable = false, unique = true, updatable = false)
	private UUID identity;

	@PrePersist
	public void prePersist() {
		if (identity == null) {
			identity = UUID.randomUUID();
		}
	}

}
