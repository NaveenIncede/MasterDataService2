package com.incede.Model.journal.journalEntry;

import com.incede.Model.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "journal_entry")
public class JournalEntry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id")
    private Integer entryId;

    @Column(name = "gl_account_id", nullable = false)
    private Integer glAccountId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "identity", nullable = false, unique = true)
    private UUID identity;
}
