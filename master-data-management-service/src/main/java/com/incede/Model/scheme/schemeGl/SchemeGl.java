package com.incede.Model.scheme.schemeGl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.incede.Model.baseentity.BaseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "scheme_gl",
	   schema="master_data",// Use lowercase with underscores to match PostgreSQL naming convention
       indexes = {
           @Index(name = "idx_tenant_id", columnList = "tenant_id"),
           @Index(name = "idx_scheme_id", columnList = "scheme_id"),
           @Index(name = "idx_gl_account_type", columnList = "gl_account_type"),
           @Index(name = "idx_gl_account_id", columnList = "gl_account_id"),
           @Index(name = "idx_identity", columnList = "identity", unique = true)
       })
public class SchemeGl extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gl_config_id")
    private Integer glConfigId;  // Changed to Long

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;  // Changed to Long

    @Column(name = "scheme_id", nullable = false)
    private Integer schemeId;  // Changed to Long

    @Column(name = "gl_account_type", nullable = false)
    private String glAccountType;

    @Column(name = "gl_account_id", nullable = false)
    private Integer glAccountId;  // Changed to Long

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "identity", nullable = false, unique = true)
    private UUID identity;


}