package com.incede.Dto.salutation.salutationtypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
	public class SalutationTypeDto {
	    private Integer salutationId;
	    private String salutation;
	    private Boolean isActive;
	    private Integer createdBy;
	    private Integer updatedBy;
	}


