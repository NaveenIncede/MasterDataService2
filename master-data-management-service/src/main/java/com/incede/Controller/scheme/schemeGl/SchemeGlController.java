package com.incede.Controller.scheme.schemeGl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import com.incede.Dto.scheme.schemGl.SchemeGlDTO;
import com.incede.Exception.ApiResponse;
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
    public ResponseEntity<ApiResponse<SchemeGlDTO>> saveOrUpdate(
            @Valid @RequestBody SchemeGlDTO dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new BusinessException("Validation failed: " + errorMsg);
        }

        ApiResponse<SchemeGlDTO> response = schemeGlService.saveOrUpdate(dto);
        return ResponseEntity.status(201).body(response);
    }

    // Soft delete by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> softDelete(@PathVariable Integer id) {
        return ResponseEntity.ok(schemeGlService.softDelete(id));
    }

    // Get all active scheme GLs
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllActive() {
        return ResponseEntity.ok(schemeGlService.getAllActive());
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SchemeGlDTO>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(schemeGlService.getById(id));
    }
    
    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<ResponseWrapper<List<SchemeGlDTO>>> getByTenantId(@PathVariable Integer tenantId) {
        ResponseWrapper<List<SchemeGlDTO>> list = schemeGlService.getByTenantId(tenantId);
        return ResponseEntity.ok(list);
    }
    
    @GetMapping("/{schemeId}/{glAccountType}")
    public ResponseEntity<ApiResponse<Integer>> getGlAccountId(@PathVariable Integer schemeId, @PathVariable String glAccountType) {
        return ResponseEntity.ok(schemeGlService.getGlAccountId(schemeId, glAccountType));
    }
    
    @GetMapping("/gl-account/{glAccountId}")
    public ResponseEntity<ApiResponse<List<SchemeGlDTO>>> getByGlAccountId(@PathVariable Integer glAccountId) {
    	ApiResponse<List<SchemeGlDTO>> list = schemeGlService.getByGlAccountId(glAccountId);
        return ResponseEntity.ok(list);
    }



}