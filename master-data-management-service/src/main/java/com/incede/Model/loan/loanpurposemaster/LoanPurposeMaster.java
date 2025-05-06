package com.incede.Model.loan.loanpurposemaster;

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
    name = "loan_purpose_master",
    schema = "master_data",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_tenant_purpose_name",
        columnNames = {"tenant_id", "purpose_name"}
    ),
    indexes = {
        @Index(name = "idx_tenant_id", columnList = "tenant_id"),
        @Index(name = "idx_purpose_name", columnList = "purpose_name"),
    }
)
@Check(constraints = "purpose_name IN (" +
    "'Business', 'Personal', 'Education', 'Medical', " +
    "'Home Improvement', 'Debt Consolidation', 'Vehicle Purchase', " +
    "'Agriculture', 'Gold Purchase', 'Other')")
@Data
@NoArgsConstructor
public class LoanPurposeMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_purpose_id", updatable = false, nullable = false)
    private Integer loanPurposeId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "purpose_name", nullable = false)
    private String purposeName;

    @Column(name = "purpose_code")
    private String purposeCode;

    @Column(name = "purpose_description", columnDefinition = "TEXT")
    private String purposeDescription;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "identity", nullable = false, unique = true)
    private UUID identity;

    @PrePersist
    public void generateUUID() {
        if (this.identity == null) {
            this.identity = UUID.randomUUID();
        }
    }
}
