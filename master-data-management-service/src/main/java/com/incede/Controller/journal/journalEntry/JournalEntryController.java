package com.incede.Controller.journal.journalEntry;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incede.Dto.journal.journalEntry.JournalEntryDTO;
import com.incede.Response.response.responseBody.ResponseWrapper;
import com.incede.Service.journal.journalEntry.JournalEntryService;

@RestController
@RequestMapping("/api/journal-entry")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;

    public JournalEntryController(JournalEntryService journalEntryService) {
        this.journalEntryService = journalEntryService;
    }

    // API to post journal entries
    @PostMapping("/post")
    public ResponseEntity<ResponseWrapper<String>> postJournalEntry(@RequestBody JournalEntryDTO journalEntryDTO) {
        journalEntryService.postTransaction(
            journalEntryDTO.getGlAccountId(), 
            journalEntryDTO.getAmount(),
            journalEntryDTO.getTenantId(),
            journalEntryDTO.getCreatedBy()
        );
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseWrapper<>("success", "Transaction posted successfully", "completed"));
    }

}
