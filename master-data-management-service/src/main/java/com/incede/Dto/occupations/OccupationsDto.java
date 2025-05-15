package com.incede.Dto.occupations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OccupationsDto {
	
	 
 	private Long occupationId;
	
    @NotNull(message = "Tenant ID must not be null")
    private Long tenantId;

    @NotBlank(message = "Occupation Name must not be blank")
    private String occupationName;

    @NotNull(message = "Created By must not be null")
    private Integer createdBy;


	public Integer updatedBy;
	
// 	private boolean isActive = true;

}
