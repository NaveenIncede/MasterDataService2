package com.incede.Model.document.documentconfiguration;

import java.util.UUID;

import org.hibernate.annotations.Check;

import com.incede.Model.baseentity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "document_requirement_configuration",
    schema = "master_data",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_product_document_type",
        columnNames = {"product_id", "document_type"}
    ),
    indexes = {
        @Index(name = "idx_product_id",    columnList = "product_id"),
        @Index(name = "idx_document_type", columnList = "document_type"),
        @Index(name = "idx_is_mandatory",  columnList = "is_mandatory"),
        @Index(name = "idx_is_active",     columnList = "is_active")
    }
)
@Check(constraints = "document_type IN (" +
    "'PAN','Aadhaar','Voter ID','Driving License'," +
    "'Passport','Utility Bill','Bank Statement'," +
    "'Salary Slip','Property Document','Photograph')")
@Data
@NoArgsConstructor
public class DocumentRequirementConfiguration extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id", updatable = false, nullable = false)
    private Integer documentId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "document_type", nullable = false, length = 50)
    private String documentType;

    @Column(name = "is_mandatory", nullable = false)
    private Boolean isMandatory = Boolean.TRUE;

    @Column(name = "document_category", length = 100)
    private String documentCategory;

    @Column(name = "sequence_number")
    private Integer sequenceNumber;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "identity", nullable = false, unique = true)
    private UUID identity;

   // If you want to set the UUID on persist:
    @PrePersist
    private void ensureIdentity() {
        if (this.identity == null) {
            this.identity = UUID.randomUUID();
        }
    }
}
