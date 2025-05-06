package com.incede.Dto.customer.customerStatus;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerStatusDto {
    
    private Integer statusId;
    private Integer tenantId;
    private String statusName;
    private Boolean isActive;
    private Integer createdBy;
    private LocalDateTime createdAt; 
    private Integer updatedBy;
    private Boolean isDeleted;

}
