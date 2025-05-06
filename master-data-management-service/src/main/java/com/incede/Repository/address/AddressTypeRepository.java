package com.incede.Repository.address;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incede.Model.address.AddressType;

@Repository
public interface AddressTypeRepository extends JpaRepository<AddressType, Integer> {
	  Optional<AddressType> findByAddressTypeNameIgnoreCase(String addressTypeName);
}
