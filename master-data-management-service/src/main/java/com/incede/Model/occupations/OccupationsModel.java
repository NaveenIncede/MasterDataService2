package com.incede.Model.occupations;

import com.incede.Model.baseentity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name="master_data.occupations",
		schema = "master_data",
		uniqueConstraints = @UniqueConstraint(
				name="uc_occupation",
				columnNames = {"tenant_id", "occupation_name"}
				)
		)
public class OccupationsModel extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="occupation_id")
	private Long occupationId;
	
	@Column(name="tenant_id",nullable = false)
	private Long tenantId;
	
	@Column(name="occupation_name", nullable = false)
	private String occupationName;
	
//	@Column(name="is_active")
//	private boolean isActive = true;

}
