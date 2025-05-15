package com.incede.Model.contactType;

import com.incede.Model.baseentity.BaseEntity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "contact_types", schema = "master_data",
       uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id", "contact_type"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactType extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_type_id")
    private Integer contactTypeId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "contact_type", length = 30, nullable = false)
    private String contactType;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive ;

}
