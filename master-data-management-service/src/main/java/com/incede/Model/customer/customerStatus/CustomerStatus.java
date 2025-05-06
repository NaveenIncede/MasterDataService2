package com.incede.Model.customer.customerStatus;

import com.incede.Model.baseentity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(
    name = "customer_statuses",
    schema = "master_data",
    indexes = {
        @Index(name = "idx_tenant", columnList = "tenant_id")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uc_customer_status", columnNames = {"tenant_id", "status_name"})
    }
)
public class CustomerStatus extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Integer statusId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "status_name", nullable = false)
    private String statusName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

}
