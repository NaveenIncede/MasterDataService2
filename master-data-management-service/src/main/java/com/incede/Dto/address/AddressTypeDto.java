package com.incede.Dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressTypeDto {
    private Integer addressTypeId;
    private String addressTypeName;
    private Boolean isActive;
    private Integer createdBy;
    private Integer updatedBy;
    private Boolean isDeleted;
}
