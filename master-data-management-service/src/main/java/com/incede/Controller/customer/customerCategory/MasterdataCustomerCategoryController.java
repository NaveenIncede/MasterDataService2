package com.incede.Controller.customer.customerCategory;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incede.Dto.customer.customerCategory.MasterdataCustomerCategoryDTO;
import com.incede.Service.customer.customerCategory.MasterdataCustomerCategoryService;
import com.incede.Exception.ApiResponse;

@RestController
@RequestMapping("/v1/masterdata/customers/customer-category")
public class MasterdataCustomerCategoryController {

    private final MasterdataCustomerCategoryService service;

    public MasterdataCustomerCategoryController(MasterdataCustomerCategoryService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<MasterdataCustomerCategoryDTO>>> getAllCategories() {
        List<MasterdataCustomerCategoryDTO> list = service.getAllCategories();
        return ResponseEntity.ok(new ApiResponse<>("success", list, "All customer categories fetched successfully"));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<MasterdataCustomerCategoryDTO>>> getActiveCategories() {
        List<MasterdataCustomerCategoryDTO> list = service.getAllActiveCategories();
        return ResponseEntity.ok(new ApiResponse<>("success", list, "All active customer categories fetched successfully"));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MasterdataCustomerCategoryDTO>> getCategoryById(@PathVariable Integer id) {
        MasterdataCustomerCategoryDTO dto = service.getCategoryById(id);
        return ResponseEntity.ok(new ApiResponse<>("success", dto, "Customer category fetched successfully"));
    }

    @GetMapping("/{id}/{tenantId}")
    public ResponseEntity<ApiResponse<MasterdataCustomerCategoryDTO>> getCategoryById(@PathVariable Integer id ,@PathVariable Integer tenantId ) {
        MasterdataCustomerCategoryDTO dto = service.getCategoryByIdAndTenant(id, tenantId);
        return ResponseEntity.ok(new ApiResponse<>("success", dto, "Customer category fetched successfully"));
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<ApiResponse<List<MasterdataCustomerCategoryDTO>>> getCategoriesByTenantId(@PathVariable Integer tenantId) {
        List<MasterdataCustomerCategoryDTO> list = service.getAllCategoriesByTenantId(tenantId);
        return ResponseEntity.ok(new ApiResponse<>("success", list, "Customer categories by tenant fetched successfully"));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<MasterdataCustomerCategoryDTO>> createCategory(@RequestBody MasterdataCustomerCategoryDTO dto) {
        MasterdataCustomerCategoryDTO created = service.createCategory(dto);
        return ResponseEntity.ok(new ApiResponse<>("success", created, "Customer category created successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<MasterdataCustomerCategoryDTO>> updateCategory(
            @PathVariable Integer id, 
            @RequestBody MasterdataCustomerCategoryDTO dto) {

        MasterdataCustomerCategoryDTO updated = service.updateCategory(id, dto);
        return ResponseEntity.ok(new ApiResponse<>("success", updated, "Customer category updated successfully"));
    }


//    @PostMapping("/delete/{id}")
//    public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable Integer id) {
//        service.deleteCategoryById(id);
//        return ResponseEntity.ok(new ApiResponse<>("success", "Customer category soft-deleted successfully", "Deleted"));
//    }
    
    @PostMapping("/delete/{categoryId}/tenant/{tenantId}")
    public ResponseEntity<ApiResponse<String>> deleteCategoryById(
            @PathVariable Integer categoryId,
            @PathVariable Integer tenantId) {

        service.deleteCategoryById(categoryId, tenantId);
        return ResponseEntity.ok(
            new ApiResponse<>("success", "Customer category deleted successfully", null)
        );
    }


}

