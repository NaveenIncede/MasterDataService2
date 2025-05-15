package com.incede.Controller.liabilityInterest.liabilityInterestRateMaster;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.incede.Dto.liabilityInterest.liabilityInterestRateMaster.LiabilityInterestRateDto;
import com.incede.Exception.ApiResponse;
import com.incede.Service.liabilityInterest.liabilityInterestRateMaster.LiabilityInterestRateService;


@RestController
@RequestMapping("/v1/masterdata/liabilityInterest")
public class LiabilityInterestRateMasterController {

    private final LiabilityInterestRateService liabilityInterestRateService;

    public LiabilityInterestRateMasterController(LiabilityInterestRateService liabilityInterestRateService) {
        this.liabilityInterestRateService = liabilityInterestRateService;
    }

    // Create or update
    @PostMapping("/")
    public ResponseEntity<ApiResponse<Long>> createLiabilityInterest(
            @RequestBody LiabilityInterestRateDto liabilityInterestRateDto) {
        Long id = liabilityInterestRateService.createLiabilityInterest(liabilityInterestRateDto);
        return ResponseEntity.ok(new ApiResponse<>(
                "Success",
                id,
                "Successfully inserted/updated interest liability data in master table"
        ));
    }

    // Set outdated interest rates to archive
    @PostMapping("/archive-expired")
    public ResponseEntity<ApiResponse<?>> archiveExpiredRates() {
        int updated = liabilityInterestRateService.archiveExpiredRates();
        return ResponseEntity.ok(new ApiResponse<>(
                "Success",
                updated,
                "Expired interest rates archived successfully"
        ));
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getLiabilityInterestById(@PathVariable Integer id) {
        LiabilityInterestRateDto dto = liabilityInterestRateService.getLiabilityInterestById(id);
        if (dto != null) {
            return ResponseEntity.ok(new ApiResponse<>(
                    "Success",
                    dto,
                    "Liability interest retrieved successfully"
            ));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(
                    "Error",
                    "Liability interest not found",
                    "404"
            ));
        }
    }

    // Get currently active interest rates
    @GetMapping("/valid")
    public ResponseEntity<ApiResponse<?>> getCurrentlyValidInterestRates() {
        List<LiabilityInterestRateDto> list = liabilityInterestRateService.getCurrentlyValidInterestRates();
        return ResponseEntity.ok(new ApiResponse<>(
                "Success",
                list,
                "Valid interest rates retrieved successfully"
        ));
    }

    // Get by isActive
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<?>> getLiabilityInterestByIsActive() {
        List<LiabilityInterestRateDto> dtoList = liabilityInterestRateService.getLiabilityInterestByIsActive();
        return ResponseEntity.ok(new ApiResponse<>(
                "Success",
                dtoList,
                "Active liability interest rates retrieved successfully"
        ));
    }

    // Get expired interest rates
    @GetMapping("/expired")
    public ResponseEntity<ApiResponse<?>> getExpiredInterestRates() {
        List<LiabilityInterestRateDto> list = liabilityInterestRateService.getExpiredInterestRates();
        return ResponseEntity.ok(new ApiResponse<>(
                "Success",
                list,
                "Expired interest rates retrieved for audit"
        ));
    }

    // Get all (full entity)
    @GetMapping("/")
    public ResponseEntity<ApiResponse<?>> getAllLiabilityInterests() {
        List<LiabilityInterestRateDto> dtoList = liabilityInterestRateService.getAllLiabilityInterests();
        if (dtoList.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse<>(
                    "Error",
                    "No liability interests found",
                    "404"
            ));
        } else {
        	return ResponseEntity.ok(new ApiResponse<>(
                    "Success",
                    dtoList,
                    "Liability interests retrieved successfully"
            ));
        }
    }

    // Soft delete
    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteLiabilityInterest(@PathVariable Integer id) {
        liabilityInterestRateService.deleteLiabilityInterest(id);
        return ResponseEntity.ok(new ApiResponse<>(
                "Success",
                "Liability interest with id " + id + " deleted successfully (soft delete)",
                "Deleted"
        ));
    }
}

