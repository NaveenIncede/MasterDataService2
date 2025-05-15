package com.incede.Controller.loanScheme.loanSchemeMaster;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incede.Dto.loanScheme.loanSchemeMaster.LoanSchemeMasterDto;
import com.incede.Response.response.responseBody.ResponseWrapper;
import com.incede.Service.loanScheme.loanSchemeMaster.LoanSchemeMasterService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/v1/loan-schemes")
public class LoanSchemeMasterController {

    private final LoanSchemeMasterService service;
    
    public LoanSchemeMasterController(LoanSchemeMasterService service)
    {
    	this.service=service;
    }

    // Create or Update Loan Scheme
    @PostMapping("/")
    public ResponseEntity<ResponseWrapper<LoanSchemeMasterDto>> saveOrUpdate(
            @Valid @RequestBody LoanSchemeMasterDto dto) {
        ResponseWrapper<LoanSchemeMasterDto> response = service.createOrUpdate(dto);
        return ResponseEntity.status(201).body(response); // 201 Created
    }


    // Get Loan Scheme by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<LoanSchemeMasterDto>> getById(@PathVariable Integer id) {
        ResponseWrapper<LoanSchemeMasterDto> response = service.getByIdAndIsActive(id);
        return ResponseEntity.ok(response);
    }

    // Get All Loan Schemes
    @GetMapping("/")
    public ResponseEntity<ResponseWrapper<List<LoanSchemeMasterDto>>> getAll() {
        ResponseWrapper<List<LoanSchemeMasterDto>> response = service.getAllActive();
        return ResponseEntity.ok(response);
    }

    // Soft Delete Loan Scheme
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Boolean>> softDelete(@PathVariable Integer id) {
        ResponseWrapper<Boolean> response = service.softDelete(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<LoanSchemeMasterDto>> getActiveSchemesByTenant(
            @PathVariable Integer tenantId) {
        List<LoanSchemeMasterDto> response = service.getActiveSchemesByTenant(tenantId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<LoanSchemeMasterDto>> getActiveSchemesByProduct(
            @PathVariable Integer productId) {
        List<LoanSchemeMasterDto> response = service.getActiveSchemesByProduct(productId);
        return ResponseEntity.ok(response);
    }


}