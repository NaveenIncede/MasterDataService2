package com.incede.Model.liabilityScheme;


import com.incede.Model.baseentity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import lombok.Data;


//--------------------------- created by Glodin joseph----------------------------//
//----------------------------08/05/2025------------------------------------------//


@Data
@Entity
@Table(name="liability_scheme_specific_master",
       schema = "master_data",
indexes = {
        @Index(name = "idx_scheme_id", columnList = "scheme_id"),
        @Index(name = "idx_config_key", columnList = "config_key"),
        @Index(name = "idx_config_value_type", columnList = "config_value_type")
    }
)
public class LiabilityModel extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="scheme_specific_id")
	private Long schemeSpecificId;
	
	@Column(name="scheme_id", nullable = false)  
	private Long schemeId;
	
	@Column(name="config_key", nullable=false, length=150)
	private String configKey;
	
	// @Lob   // ---------------to map that field as a Large Object in the database ------------- 
	@Column(name="config_value", nullable=false )
	private String configValue;
	
	@Column(name="config_value_type", nullable=false , length=50)
	private String configValueType ;
	
//	@Column(name="idDelete")
//	private boolean isDelete;
}
