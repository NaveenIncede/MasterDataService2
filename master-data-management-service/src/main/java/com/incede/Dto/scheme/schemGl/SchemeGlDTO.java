package com.incede.Dto.scheme.schemGl;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchemeGlDTO {

    private Integer glConfigId;

    @NotNull(message = "TenantId must not be null")
    private Integer tenantId;

    @NotNull(message = "SchemeId must not be null")
    private Integer schemeId;

    @NotBlank(message = "GL Account Type must not be empty")
    private String glAccountType;

    @NotNull(message = "GL Account Id must not be null")
    private Integer glAccountId;

    private UUID identity;

    private Boolean isActive = true;

    @NotNull(message = "CreatedBy must not be null")
    private Integer createdBy;

    // getters and setters or use Lombok annotations like @Data
}
