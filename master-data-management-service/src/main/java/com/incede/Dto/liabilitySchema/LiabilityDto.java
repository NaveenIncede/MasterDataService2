package com.incede.Dto.liabilitySchema;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
//--------------------------- created by Glodin joseph----------------------------//
//----------------------------08/05/2025------------------------------------------//

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LiabilityDto {
	
	 
    private Long schemeSpecificId; // Optional (used for update)

    @NotNull(message = "Scheme ID must not be null")
    private Long schemeId;

    @NotBlank(message = "Config Key must not be blank")
    @Size(max = 150, message = "Config Key must be at most 150 characters")
    private String configKey;

    @NotBlank(message = "Config Value must not be blank")
    private String configValue;

    @NotBlank(message = "Config Value Type must not be blank")
    @Size(max = 50, message = "Config Value Type must be at most 50 characters")
    private String configValueType;

    @NotNull(message = "Created By must not be null")
    private Integer createdBy;

    private Integer updatedBy;

    // public field - you can annotate it too or move to a getter/setter if needed
    public boolean deactivateSpecificConfiguration;

}

