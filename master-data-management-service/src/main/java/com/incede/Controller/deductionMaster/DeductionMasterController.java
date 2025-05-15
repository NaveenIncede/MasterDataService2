package com.incede.Controller.deductionMaster;


import com.incede.Dto.deductionMaster.DeductionMasterDto;
import com.incede.Exception.ApiResponse;
import com.incede.Service.deductionMasterService.DeductionMasterService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/masterdata/deduction")
public class DeductionMasterController {
	    private final DeductionMasterService deductionMasterService;

	    public DeductionMasterController(DeductionMasterService deductionMasterService) {
	        this.deductionMasterService = deductionMasterService;
	        
	    }

	    // Create or Update
	    @PostMapping("/")
	    public ResponseEntity<ApiResponse<Long>> createOrUpdateDeduction(@RequestBody DeductionMasterDto dto) {
	        Long id = deductionMasterService.createOrUpdateDeduction(dto);
	        return ResponseEntity.ok(new ApiResponse<>(
	                "Success",
	                id,
	                "Deduction created/updated successfully"
	        ));
	    }

	    // Get by ID
	    @GetMapping("/{deductionId}")
	    public ResponseEntity<ApiResponse<?>> getDeductionById(@PathVariable Integer deductionId) {
	        DeductionMasterDto dto = deductionMasterService.getDeductionById(deductionId);
	        return ResponseEntity.ok(new ApiResponse<>(
	                "Success",
	                dto,
	                "Deduction of id : "+deductionId+ "fetched successfully"
	                
	        ));
	    }

	    // Get Active
	    @GetMapping("/active")
	    public ResponseEntity<ApiResponse<?>> getActiveDeductions() {
	        List<DeductionMasterDto> list = deductionMasterService.getActiveDeductions();
	        return ResponseEntity.ok(new ApiResponse<>(
	                "Success",
	                list,
	                "Active deductions fetched successfully"
	        ));
	    }

	    // Soft delete
	    @PostMapping("/{id}")
	    public ResponseEntity<ApiResponse<String>> softDelete(@PathVariable Integer id) {
	        deductionMasterService.softDeleteDeduction(id);
	        return ResponseEntity.ok(new ApiResponse<>(
	                "Success",
	                "Deduction soft-deleted successfully",
	                "Deleted"
	        ));
	    }
	}

	

