	
package com.incede.Controller.scheme;
import com.incede.Dto.scheme.SchemeSpecificMasterDto;
import com.incede.Model.scheme.SchemeSpecificMaster;
import com.incede.Service.scheme.SchemeSpecificMasterService;
import com.incede.Exception.ApiResponse;

import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;

	import java.util.List;

	@RestController
	@RequestMapping("/v1/masterdata/scheme")
	@CrossOrigin(origins = "*")
	public class SchemeSpecificMasterController {

	    private final SchemeSpecificMasterService service;

	    public SchemeSpecificMasterController(SchemeSpecificMasterService service) {
	        this.service = service;
	    }
	    @GetMapping("/all")
	    public ResponseEntity<ApiResponse<List<SchemeSpecificMasterDto>>> getAllConfigs() {
	        List<SchemeSpecificMasterDto> configs = service.getAllSchemeConfigs();
	        return ResponseEntity.ok(new ApiResponse<>("success", configs, "All configurations fetched successfully"));
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<ApiResponse<SchemeSpecificMaster>> getConfigById(@PathVariable Integer id) {
	        SchemeSpecificMaster config = service.getSchemeConfigById(id);
	        return ResponseEntity.ok(new ApiResponse<>("success", config, "Configuration fetched successfully"));
	    }

	    @PostMapping("/create")
	    public ResponseEntity<ApiResponse<SchemeSpecificMaster>> createConfig(@RequestBody SchemeSpecificMasterDto dto) {
	        SchemeSpecificMaster created = service.createSchemeConfig(dto);
	        return ResponseEntity.ok(new ApiResponse<>("success", created, "scheme configuration created successfully")); // your custom message
	    }

	    @PutMapping("/update/{id}")
	    public ResponseEntity<ApiResponse<SchemeSpecificMaster>> updateConfig(
	            @PathVariable Integer id,
	            @RequestBody SchemeSpecificMasterDto dto) {
	        SchemeSpecificMaster updated = service.updateSchemeConfig(id, dto);
	        return ResponseEntity.ok(new ApiResponse<>("success", updated, "Configuration updated"));
	    }

	    @PostMapping("/delete/{id}")
	    public ResponseEntity<ApiResponse<String>> deleteConfig(@PathVariable Integer id) {
	        service.deleteSchemeConfig(id);
	        return ResponseEntity.ok(new ApiResponse<>("success", "Configuration soft-deleted successfully", "Deleted"));
	    }
	    @GetMapping("/active")
	    public ResponseEntity<ApiResponse<List<SchemeSpecificMasterDto>>> getAllActiveConfigs() {
	        List<SchemeSpecificMasterDto> activeConfigs = service.getAllActiveSchemeConfigs();
	        return ResponseEntity.ok(new ApiResponse<>("success", activeConfigs, "Active scheme configurations fetched successfully"));
	    }
	    
	    @GetMapping("/tenant/{tenantId}")
	    public ResponseEntity<ApiResponse<List<SchemeSpecificMasterDto>>> getByTenantId(@PathVariable Integer tenantId) {
	        List<SchemeSpecificMasterDto> dtoList = service.getSchemeConfigsByTenantId(tenantId);
	        return ResponseEntity.ok(new ApiResponse<>("success", dtoList, "Configs fetched for tenant"));
	    }
	    
	    @GetMapping("/scheme/{schemeId}")
	    public ResponseEntity<ApiResponse<List<SchemeSpecificMasterDto>>>getConfigsBySchemeId(@PathVariable Integer schemeId) {
	        List<SchemeSpecificMasterDto> configList = service.getSchemeConfigsBySchemeId(schemeId);
	        return ResponseEntity.ok(new ApiResponse<>("success", configList, "Config key value pairs fetched"));
	    }
	    

	}



