package com.incede.Controller.occupations;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incede.Dto.occupations.OccupationsDto;
import com.incede.Exception.ApiResponse;
import com.incede.Service.occupations.OccupationsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/master-data-occupations")
public class OccupationsController {

	public final OccupationsService occupationsService;
	public OccupationsController(OccupationsService occupationService) {
		this.occupationsService = occupationService;
	}
	
	//-----------------------------------created by Glodin Joseph---------------------------------//
	//-----------------------------------created on 8/5/2025--------------------------------------//
	
	// create new occupation using post method
    @PostMapping("/")
    public ResponseEntity<ApiResponse<OccupationsDto>> createOccupation( @Valid @RequestBody OccupationsDto occupationsDto) {
        OccupationsDto createdOccupation = occupationsService.createMasterDataOccupations(occupationsDto);
        ApiResponse<OccupationsDto> response = new ApiResponse<>("success",createdOccupation,"Occupation created successfully.");
        return ResponseEntity.status(201).body(response);
    }
    
    // update new occupations using put methode
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<OccupationsDto>> updateOccupation(@RequestBody OccupationsDto occupationDto) {
        OccupationsDto updatedDto = occupationsService.updateMasterDataOccupations(occupationDto);
        ApiResponse<OccupationsDto> response = new ApiResponse<>("success",updatedDto,"Occupation updated successfully");
        return ResponseEntity.ok(response);
    }
    
    // soft deleting using post methode
    @PostMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOccupationalStatus(@PathVariable("id") Long occupationId) {
    	occupationsService.deleteOccupationalStatus(occupationId);
        ApiResponse<Void> response = new ApiResponse<>("success", null, "Occupational status marked as inactive successfully.");
        return ResponseEntity.ok(response);
    }
    
    // get occupation by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OccupationsDto>> getOccupationalById(@PathVariable("id") Long occupationId) {
    	 OccupationsDto updatedDto =occupationsService.getOccupationalDetailsById(occupationId);
        ApiResponse<OccupationsDto> response = new ApiResponse<>("success", updatedDto, "Occupational retrived successfully.");
        return ResponseEntity.ok(response);
    }
    // get all occupations by id
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<OccupationsDto>>> getAllOccupationalDetails() {
    	 List<OccupationsDto> updatedDto = occupationsService.getAllActiveOccupationalDetails();
        ApiResponse<List<OccupationsDto>> response = new ApiResponse<>("success", updatedDto, "Occupational details retrived successfully.");
        return ResponseEntity.ok(response);
    }
    
    // get details by tenent id
    @GetMapping("/tenent/{id}")
    public ResponseEntity<ApiResponse<List<OccupationsDto>>> getAllOccupationalDetailsUsingTenantId(@PathVariable ("id") Long TenantId) {
    	 List<OccupationsDto> updatedDto = occupationsService.getAllActiveOccupationalDetailsUsingTenant(TenantId);
        ApiResponse<List<OccupationsDto>> response = new ApiResponse<>("success", updatedDto, "Occupational details retrived successfully.");
        return ResponseEntity.ok(response);
    }
}
