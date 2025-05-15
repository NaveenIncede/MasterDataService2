package com.incede.Repository.journal.journalEntry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incede.Model.journal.journalEntry.JournalEntry;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, Integer> {
    // CRUD operations for journal entries (if needed)
}
