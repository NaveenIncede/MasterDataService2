package com.incede.Model.nationality.nationalityStatus;

import com.incede.Model.baseentity.BaseEntity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "nationalities",
	   schema = "master_data",
	   indexes = {
        @Index(name = "idx_tenant_id", columnList = "tenant_id"),
        @Index(name = "idx_nationality", columnList = "nationality")
    })
public class Nationality extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nationality_id")
    private Integer nationalityId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
