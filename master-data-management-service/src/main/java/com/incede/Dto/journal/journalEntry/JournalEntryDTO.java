package com.incede.Dto.journal.journalEntry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalEntryDTO {

    private Integer glAccountId;
    private BigDecimal amount;
    private Integer tenantId;
    private Boolean isActive;
    private UUID identity;
    private Integer createdBy;
}
