package com.incede.Model.relationships;

import com.incede.Model.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
    name = "relationships",
    schema = "master_data",
    indexes = {
        @Index(name = "idx_relationship", columnList = "relationship")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uc_relationship", columnNames = {"tenant_id", "relationship"})
    }
)
public class Relationship extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationship_id")
    private Integer relationshipId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "relationship", nullable = false)
    private String relationship;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

}
