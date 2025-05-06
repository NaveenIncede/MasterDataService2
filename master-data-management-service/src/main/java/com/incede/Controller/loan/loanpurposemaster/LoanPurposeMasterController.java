package com.incede.Controller.loan.loanpurposemaster;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incede.Dto.loan.loanpurposemaster.LoanPurposeMasterDto;
import com.incede.Model.loan.loanpurposemaster.LoanPurposeMaster;
import com.incede.Service.loan.loanpurposemaster.LoanPurposeMasterService;

@RestController
@RequestMapping("/v1/masterdata/loan/purposes")
public class LoanPurposeMasterController {

    private final LoanPurposeMasterService loanPurposeMasterService;

    public LoanPurposeMasterController(LoanPurposeMasterService loanPurposeMasterService) {
        this.loanPurposeMasterService = loanPurposeMasterService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<LoanPurposeMasterDto>> getAll() {
        return ResponseEntity.ok(loanPurposeMasterService.getAllPurposes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanPurposeMasterDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(loanPurposeMasterService.getPurposeById(id));
    }


    @GetMapping("/identity/{uuid}")
    public ResponseEntity<LoanPurposeMasterDto> getByIdentity(@PathVariable UUID uuid) {
        return ResponseEntity.ok(loanPurposeMasterService.getPurposeByIdentity(uuid));
    }

    @PostMapping("/")
    public ResponseEntity<LoanPurposeMaster> create(@RequestBody LoanPurposeMasterDto dto) {
        return ResponseEntity.status(201).body(loanPurposeMasterService.createPurpose(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanPurposeMaster> update(@PathVariable Integer id,
                                                    @RequestBody LoanPurposeMasterDto dto) {
        return ResponseEntity.ok(loanPurposeMasterService.updatePurpose(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePurpose(@PathVariable Integer id) {
    	loanPurposeMasterService.deletePurpose(id);
        return ResponseEntity.ok("Loan purpose deleted successfully");
    }
}