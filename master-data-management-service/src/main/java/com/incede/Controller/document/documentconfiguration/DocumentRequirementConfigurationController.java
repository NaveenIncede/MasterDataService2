package com.incede.Controller.document.documentconfiguration;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.incede.Dto.document.documentconfiguration.DocumentRequirementConfigurationDto;
import com.incede.Model.document.documentconfiguration.DocumentRequirementConfiguration;
import com.incede.Service.document.documentconfiguration.DocumentRequirementConfigurationService;

@RestController
@RequestMapping("/v1/masterdata/documents/requirement-configurations")
public class DocumentRequirementConfigurationController {

    private final DocumentRequirementConfigurationService service;

    public DocumentRequirementConfigurationController(DocumentRequirementConfigurationService service) {
        this.service = service;
    }

 // Get all configurations
    @GetMapping("/getAll")
    public ResponseEntity<List<DocumentRequirementConfigurationDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }


    // Get configuration by ID
    @GetMapping("/{id}")
    public ResponseEntity<DocumentRequirementConfiguration> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // Get configuration by UUID identity
    @GetMapping("/identity/{identity}")
    public ResponseEntity<DocumentRequirementConfiguration> getByIdentity(@PathVariable UUID identity) {
        return ResponseEntity.ok(service.getByIdentity(identity));
    }

    // Create new configuration using DTO
    @PostMapping("/")
    public ResponseEntity<DocumentRequirementConfiguration> create(
            @RequestBody DocumentRequirementConfigurationDto dto) {
        return ResponseEntity.status(201).body(service.create(dto));
    }

    // Update existing configuration using DTO
    @PutMapping("/{id}")
    public ResponseEntity<DocumentRequirementConfiguration> update(
            @PathVariable Integer id,
            @RequestBody DocumentRequirementConfigurationDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok("Configuration deleted successfully");
    }

}
