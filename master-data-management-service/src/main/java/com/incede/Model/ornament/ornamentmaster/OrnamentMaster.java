package com.incede.Model.ornament.ornamentmaster;

import com.incede.Model.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "ornament_master", schema = "master_data", indexes = {
		@Index(name = "idx_ornament_tenant", columnList = "tenant_id") }, uniqueConstraints = {
				@UniqueConstraint(name = "uc_ornament_name_per_tenant", columnNames = { "tenant_id",
						"ornament_name" }) })
public class OrnamentMaster extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ornament_id")
	private Integer ornamentId;

	@Column(name = "tenant_id", nullable = false)
	private Integer tenantId;

	@Column(name = "ornament_name", nullable = false)
	private String ornamentName;

	@Column(name = "is_active", nullable = false)
	private Boolean isActive = true;

	@Column(name = "identity", nullable = false, unique = true)
	private UUID identity;

}
