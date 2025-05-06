package com.incede.Dto.ornament.ornamentmaster;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrnamentMasterDto {

    private Integer tenantId;
    private String ornamentName;
    private Boolean isActive;
    private UUID identity;
    private Integer createdBy;
    private Integer updatedBy;
    private Boolean isDeleted;
}
