package com.incede.Dto.nationality.nationalitystatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NationalityDTO {

    private Integer nationalityId;

    @NotNull(message = "Tenant ID must not be null")
    private Integer tenantId;

    @NotBlank(message = "Nationality must not be empty")
    private String nationality;

    @NotNull(message = "isActive must not be null")
    private Boolean isActive;

    private Integer createdBy; // Optional: manually check this in service if needed for new records
}
