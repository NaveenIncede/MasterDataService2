package com.incede.Controller.ContactType;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incede.Dto.contactType.ContactTypeDto;
import com.incede.Exception.ApiResponse;
import com.incede.Service.contactType.ContactTypeService;

@RestController
@RequestMapping("/v1/masterdata/contacts/contactType")
public class ContactTypeController {

	public final ContactTypeService contactTypeService;

	public ContactTypeController(ContactTypeService contactTypeService) {

		this.contactTypeService = contactTypeService;
	}
	 // Create or Update Contact Type
    @PostMapping("/")
    public ResponseEntity<ApiResponse<Long>> createOrUpdateContactType(@RequestBody ContactTypeDto contactTypeDto) {
        Long id = contactTypeService.createOrUpdateContactType(contactTypeDto);
        return ResponseEntity.ok(new ApiResponse<>("success", id, "Contact type created/updated successfully"));
    }

    // Get Contact Type by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getContactTypeById(@PathVariable Long id) {
        ContactTypeDto dto = contactTypeService.getContactTypeById(id);
        return ResponseEntity.ok(new ApiResponse<>(
        		"success",
        		dto, 
        		"Contact type of "+id+" retrieved successfully"
        		));
    }

    // Get All Contact Types
    @GetMapping("/")
    public ResponseEntity<ApiResponse<?>> getAllContactTypes() {
        List<ContactTypeDto> dtoList = contactTypeService.getAllContactTypes();
        if (dtoList.isEmpty()) {
            return ResponseEntity.status(404)
                .body(new ApiResponse<>(
                		"error", 
                		"No contact types found", 
                		"404"
                		));
        } else {
            return ResponseEntity.ok(new ApiResponse<>("success", dtoList, "Contact types retrieved successfully"));
        }
    }

    // Soft Delete Contact Type
    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteContactType(@PathVariable Long id) {
        contactTypeService.deleteContactType(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Contact type with ID " + id + " soft deleted successfully", "deleted"));
    }
	

}
