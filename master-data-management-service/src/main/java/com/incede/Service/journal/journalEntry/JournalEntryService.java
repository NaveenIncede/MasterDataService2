package com.incede.Service.journal.journalEntry;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incede.Model.journal.journalEntry.JournalEntry;
import com.incede.Repository.journal.journalEntry.JournalEntryRepository;

@Service
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;

    @Autowired
    public JournalEntryService(JournalEntryRepository journalEntryRepository) {
        this.journalEntryRepository = journalEntryRepository;
    }

    public void postTransaction(Integer glAccountId, BigDecimal amount, Integer tenantId,Integer createdBy) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return; // Ignore zero or null amounts
        }

        JournalEntry entry = new JournalEntry();
        entry.setGlAccountId(glAccountId);
        entry.setAmount(amount);
        entry.setTenantId(tenantId);
        entry.setIsActive(true);
        entry.setIdentity(UUID.randomUUID());
        entry.setCreatedBy(createdBy);

        journalEntryRepository.save(entry);
    }
}
