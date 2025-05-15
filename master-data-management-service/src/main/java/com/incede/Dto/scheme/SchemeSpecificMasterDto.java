package com.incede.Dto.scheme;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SchemeSpecificMasterDto {

	    private Integer schemeSpecificId;
	    private Integer tenantId;
	    private Integer schemeId;
	    private String configKey;
	    private String configValue;
	    private String configValueType;
	    private Boolean isActive;
	    private UUID identity;
	    private Integer createdBy;
	    private LocalDateTime createdAt;
	    private Integer updatedBy;
	    private LocalDateTime updatedAt;
	    
	}



