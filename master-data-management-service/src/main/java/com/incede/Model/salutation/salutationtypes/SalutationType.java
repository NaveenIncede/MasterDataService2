package com.incede.Model.salutation.salutationtypes;
import com.incede.Model.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "salutation_types", schema = "master_data")
@Data
@EqualsAndHashCode(callSuper = true)
public class SalutationType extends BaseEntity {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "salutation_id")
	    private Integer salutationId;

	    @Column(name = "salutation", nullable = false)
	    private String salutation;

	    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE")
	    private Boolean isActive = true;
	}


	
	
