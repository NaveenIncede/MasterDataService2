package com.incede.Dto.contactType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactTypeDto {

	private Integer contactTypeId;
	private Integer tenantId;
	private String contactType;
	private boolean isActive;
	private Integer createdBy;
	

}
