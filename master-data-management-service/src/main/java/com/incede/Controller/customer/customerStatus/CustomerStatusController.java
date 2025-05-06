package com.incede.Controller.customer.customerStatus;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.incede.Dto.customer.customerStatus.CustomerStatusDto;
import com.incede.Model.customer.customerStatus.CustomerStatus;
import com.incede.Service.customer.customerStatus.CustomerStatusService;

@RestController
@RequestMapping("/v1/masterdata/customers/customer-statuses")
public class CustomerStatusController {

	private final CustomerStatusService customerStatusService;

	public CustomerStatusController(CustomerStatusService customerStatusService) {
		this.customerStatusService = customerStatusService;
	}

	// Get all customer statuses that are not deleted
	@GetMapping("/getAll")
	public ResponseEntity<List<CustomerStatusDto>> getAllCustomerStatuses() {
		List<CustomerStatusDto> statuses = customerStatusService.getAllCustomerStatuses();
		return ResponseEntity.ok(statuses);
	}

	// Get a customer status by ID
	@GetMapping("/{statusId}")
	public ResponseEntity<CustomerStatus> getCustomerStatusById(@PathVariable Integer statusId) {
		return ResponseEntity.ok(customerStatusService.getCustomerStatus(statusId));
	}

	// Create a new customer status
	@PostMapping("/")
	public ResponseEntity<CustomerStatus> createCustomerStatus(@RequestBody CustomerStatusDto dto) {
		return ResponseEntity.status(201).body(customerStatusService.createCustomerStatus(dto));
	}

	// Update an existing customer status
	@PutMapping("/{statusId}")
	public ResponseEntity<CustomerStatus> updateCustomerStatus(@PathVariable Integer statusId,
			@RequestBody CustomerStatusDto dto) {
		return ResponseEntity.ok(customerStatusService.updateCustomerStatus(statusId, dto));
	}

	// Soft delete a customer status
	@DeleteMapping("/{statusId}")
	public ResponseEntity<String> deleteCustomerStatus(@PathVariable Integer statusId) {
		customerStatusService.deleteCustomerStatus(statusId);
		return ResponseEntity.ok("Customer status deleted");
	}
}
