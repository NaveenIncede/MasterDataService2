
 package com.incede.Controller.salutation.salutationtypes;

import com.incede.Dto.salutation.salutationtypes.SalutationTypeDto;
import com.incede.Model.salutation.salutationtypes.SalutationType;
import com.incede.Service.salutation.salutationtypes.SalutationTypeService;
import com.incede.Exception.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/masterdata/salutationtype")
@CrossOrigin(origins = "*")
public class SalutationTypeController {

    private final SalutationTypeService service;

    public SalutationTypeController(SalutationTypeService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<SalutationTypeDto>>> getAllSalutations() {
        List<SalutationTypeDto> list = service.getAllSalutations();
        return ResponseEntity.ok(new ApiResponse<>("success", list, "All salutation types fetched successfully"));
    }
    
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<SalutationTypeDto>>> getActiveSalutations() {
    	List<SalutationTypeDto> list = service.getActiveSalutations();
        return ResponseEntity.ok(new ApiResponse<>("success",list, "All active salutations fetched successfully"));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SalutationTypeDto>> getSalutationById(@PathVariable Integer id) {
        SalutationTypeDto dto = service.getSalutationById(id);
        return ResponseEntity.ok(new ApiResponse<>("success", dto, "Salutation type fetched successfully"));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<SalutationTypeDto>> createSalutation(@RequestBody SalutationTypeDto dto) {
        SalutationTypeDto created = service.createSalutation(dto);
        return ResponseEntity.ok(new ApiResponse<>("success", created, "Salutation type created successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<SalutationTypeDto>> updateSalutation(@PathVariable Integer id, @RequestBody SalutationTypeDto dto) {
        SalutationTypeDto updated = service.updateSalutation(id, dto);
        return ResponseEntity.ok(new ApiResponse<>("success", updated, "Salutation type updated successfully"));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSalutation(@PathVariable Integer id) {
        service.deleteSalutation(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Salutation type soft-deleted successfully", "Deleted"));
    }
}

