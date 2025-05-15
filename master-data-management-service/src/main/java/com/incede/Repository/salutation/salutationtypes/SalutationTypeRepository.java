package com.incede.Repository.salutation.salutationtypes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incede.Model.salutation.salutationtypes.SalutationType;

public interface SalutationTypeRepository extends JpaRepository<SalutationType, Integer> {
 
	List<SalutationType> findByIsActiveTrue();
	

}
