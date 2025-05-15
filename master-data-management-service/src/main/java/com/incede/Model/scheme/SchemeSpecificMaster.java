package com.incede.Model.scheme;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;
import java.util.UUID;
	
	@Entity
	@Table(name = "scheme_specific_master")
	@Data
	public class SchemeSpecificMaster {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "scheme_specific_id")
	    private Integer schemeSpecificId;

	    @Column(name = "tenant_id", nullable = false)
	    private Integer tenantId;

	    @Column(name = "scheme_id", nullable = false)
	    private Integer schemeId;

	    @Column(name = "config_key", nullable = false)
	    private String configKey;

	    @Column(name = "config_value", nullable = false, columnDefinition = "TEXT")
	    private String configValue;

	    @Column(name = "config_value_type", nullable = false)
	    private String configValueType;

	    @Column(name = "is_active")
	    private Boolean isActive = true;

	    @Column(name = "identity", nullable = false, unique = true, columnDefinition = "UUID")
	    private UUID identity = UUID.randomUUID();

	    @Column(name = "created_by", nullable = false)
	    private Integer createdBy;

	    @Column(name = "created_at", nullable = false)
	    private LocalDateTime createdAt = LocalDateTime.now();

	    @Column(name = "updated_by")
	    private Integer updatedBy;

	    @Column(name = "updated_at")
	    private LocalDateTime updatedAt = LocalDateTime.now();
	}


