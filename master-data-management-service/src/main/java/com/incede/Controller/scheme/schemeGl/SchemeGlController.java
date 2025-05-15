package com.incede.Controller.scheme.schemeGl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import com.incede.Dto.scheme.schemGl.SchemeGlDTO;
import com.incede.Exception.BusinessException;
import com.incede.Response.response.responseBody.ResponseWrapper;
import com.incede.Service.scheme.schemeGl.SchemeGlService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/scheme-gl")
public class SchemeGlController {

    @Autowired
    private SchemeGlService schemeGlService;

    // Create or update
    @PostMapping("/")
    public ResponseEntity<ResponseWrapper<SchemeGlDTO>> saveOrUpdate(
            @Valid @RequestBody SchemeGlDTO dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new BusinessException("Validation failed: " + errorMsg);
        }

        ResponseWrapper<SchemeGlDTO> response = schemeGlService.saveOrUpdate(dto);
        return ResponseEntity.status(201).body(response);
    }

    // Soft delete by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Boolean>> softDelete(@PathVariable Integer id) {
        return ResponseEntity.ok(schemeGlService.softDelete(id));
    }

    // Get all active scheme GLs
    @GetMapping
    public ResponseEntity<ResponseWrapper<?>> getAllActive() {
        return ResponseEntity.ok(schemeGlService.getAllActive());
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<SchemeGlDTO>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(schemeGlService.getById(id));
    }
    
    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<ResponseWrapper<List<SchemeGlDTO>>> getByTenantId(@PathVariable Integer tenantId) {
        ResponseWrapper<List<SchemeGlDTO>> list = schemeGlService.getByTenantId(tenantId);
        return ResponseEntity.ok(list);
    }
    
    @GetMapping("/{schemeId}/{glAccountType}")
    public ResponseEntity<ResponseWrapper<Integer>> getGlAccountId(@PathVariable Integer schemeId, @PathVariable String glAccountType) {
        return ResponseEntity.ok(schemeGlService.getGlAccountId(schemeId, glAccountType));
    }
    
    @GetMapping("/gl-account/{glAccountId}")
    public ResponseEntity<ResponseWrapper<List<SchemeGlDTO>>> getByGlAccountId(@PathVariable Integer glAccountId) {
        ResponseWrapper<List<SchemeGlDTO>> list = schemeGlService.getByGlAccountId(glAccountId);
        return ResponseEntity.ok(list);
    }



}