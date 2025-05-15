package com.incede.Dto.customer.customerCategory;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterdataCustomerCategoryDTO {
	
    private Integer categoryId;
    private Integer tenantId;
    private String categoryName;
    private String description;
    private Boolean isActive;
    private Integer createdBy;
    private Integer updatedBy;
    private Boolean isDeleted;
//    private 
    private LocalDateTime createdAt; 
    
    
}
