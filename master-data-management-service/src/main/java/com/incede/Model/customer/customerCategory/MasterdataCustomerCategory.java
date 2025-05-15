package com.incede.Model.customer.customerCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.incede.Model.baseentity.*;

@Entity
@Table(name = "customer_categories", schema = "master_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterdataCustomerCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "category_name", nullable = false, length = 255)
    private String categoryName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive = true;
}
