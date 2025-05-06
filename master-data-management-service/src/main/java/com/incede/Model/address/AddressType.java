package com.incede.Model.address;

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
    name = "address_types",
    schema = "master_data",
    indexes = {
        @Index(name = "idx_address_type_name", columnList = "address_type_name")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uc_address_type_name", columnNames = {"address_type_name"})
    }
)
public class AddressType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_type_id")
    private Integer addressTypeId;

    @Column(name = "address_type_name", nullable = false)
    private String addressTypeName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
