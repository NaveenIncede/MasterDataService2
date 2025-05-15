package com.incede.Controller.nationality.nationalityStatus;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incede.Dto.nationality.nationalitystatus.NationalityDTO;
import com.incede.Exception.ApiResponse;
import com.incede.Model.nationality.nationalityStatus.Nationality;
import com.incede.Response.response.responseBody.ResponseWrapper;
import com.incede.Service.nationality.nationalityStatus.NationalityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/nationalities")
public class NationalityController {

    private final NationalityService nationalityService;

    public NationalityController(NationalityService nationalityService) {
        this.nationalityService = nationalityService;
    }

    // Get all active (non-deleted) nationalities
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<NationalityDTO>>> getAllNationalities() {
    	ApiResponse<List<NationalityDTO>> response = nationalityService.getAllNationalities();
        return ResponseEntity.ok(response);
    }

    // Get nationality by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NationalityDTO>> getNationalityById(@PathVariable Integer id) {
    	ApiResponse<NationalityDTO> response = nationalityService.getNationalityById(id);
        return ResponseEntity.ok(response);
    }

    // Create or Update a nationality
    @PostMapping("/")
    public ResponseEntity<ApiResponse<Nationality>> createOrUpdateNationality(
            @Valid @RequestBody NationalityDTO nationalityDTO) {
    	ApiResponse<Nationality> response = nationalityService.createOrUpdateNationality(nationalityDTO);
        return ResponseEntity.status(201).body(response);
    }

    // Soft delete a nationality
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteNationality(@PathVariable Integer id) {
    	ApiResponse<String> response = nationalityService.deleteNationality(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<NationalityDTO>> getByTenantId(@PathVariable Integer tenantId) {
        List<NationalityDTO> list = nationalityService.getByTenantId(tenantId);
        return ResponseEntity.ok(list);
    }

}
