package com.incede.Controller.liabilitySchema;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incede.Dto.liabilitySchema.LiabilityDto;
import com.incede.Exception.ApiResponse;
import com.incede.Model.liabilityScheme.LiabilityModel;
import com.incede.Service.liabilitySchema.LiabilityService;

import jakarta.validation.Valid;


//---------------------------created by glodin joseph------------------------------------//
//----------------------------date 08/05/2025--------------------------------------------//
 

@RestController
@RequestMapping("/v1/liability-scheme-specific-masters")
public class LiabilityController {

    private final LiabilityService liabilityService;

    public LiabilityController(LiabilityService liabilityService) {
        this.liabilityService = liabilityService;
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<LiabilityDto>> saveOrUpdate(@Valid @RequestBody LiabilityDto liabilityDto) {
    	LiabilityDto result = liabilityService.createOrUpdateLiabilitySchemeSpecificMasterDetails(liabilityDto);

        ApiResponse<LiabilityDto> response = new ApiResponse<>();
        response.setStatus("successful");
        response.setMessage("Liability saved or updated successfully.");
        response.setData(result);

        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/liability/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> softDeleteLiability(@PathVariable("id") Long id) {
        LiabilityModel result = liabilityService.softDeletingLiabilitySchemeSpecificMasterDetails(id);

        ApiResponse<Boolean> response = new ApiResponse<>();
        response.setStatus("successful");
        response.setMessage("Liability deleted successfully.");
        response.setData(result.getIsDeleted());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/liability/get/{id}")
    public ResponseEntity<ApiResponse<LiabilityDto>> getLiabilityDetails(@PathVariable("id") Long id) {
    	LiabilityDto result = liabilityService.getLiabilitySchemeSpecificMasterDetailById(id);

        ApiResponse<LiabilityDto> response = new ApiResponse<>();
        response.setStatus("successful");
        response.setMessage("Liability details retrieved successfully.");
        response.setData(result);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/liabilitys/get")
    public ResponseEntity<ApiResponse<List<LiabilityDto>>> getAllLiabilityDetails() {
        List<LiabilityDto> result = liabilityService.getAllLiabilitySchemeSpecificMasterDetail();

        ApiResponse<List<LiabilityDto>> response = new ApiResponse<>();
        response.setStatus("successful");
        response.setMessage("All liability details retrieved successfully.");
        response.setData(result);

        return ResponseEntity.ok(response);
    }
    
    // deactivate a specific controller
    
    @PostMapping("/activate_deactivate")
    public ResponseEntity<ApiResponse<String>> DeActivateaSpecificConfiguration(@RequestBody LiabilityDto liabilitydto) {
        String result = liabilityService.deactivateSpecificConfiguration(liabilitydto);
        ApiResponse<String> response = new ApiResponse<>("successful",result,"All liability details retrieved successfully.");
        return ResponseEntity.ok(response);
    }
    
    // (optional for future reff...)
    @GetMapping("/scheme-id/{schemeId}")
    public ResponseEntity<ApiResponse<List<LiabilityDto>>> getLiabilityDetailsBySchemeId(@PathVariable Long schemeId) {
        List<LiabilityDto> result = liabilityService.getLiabilitySchemeSpecificMasterDetailsBySchemeId(schemeId);
        ApiResponse<List<LiabilityDto>> response = new ApiResponse<>("successful", result, "All liability details retrieved successfully");
        return ResponseEntity.ok(response);
    }

}


