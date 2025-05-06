package com.incede.Service.address;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.address.AddressTypeDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.address.AddressType;
import com.incede.Model.customer.customerStatus.CustomerStatus;
import com.incede.Repository.address.AddressTypeRepository;

@Service
public class AddressTypeService {

	private final AddressTypeRepository addressTypeRepository;

	public AddressTypeService(AddressTypeRepository addressTypeRepository) {
		this.addressTypeRepository = addressTypeRepository;
	}

	private AddressTypeDto toDto(AddressType at) {
		AddressTypeDto dto = new AddressTypeDto();
		dto.setAddressTypeName(at.getAddressTypeName());
		dto.setAddressTypeId(at.getAddressTypeId());
		dto.setIsActive(at.getIsActive());
		dto.setIsDeleted(at.getIsDeleted());
		dto.setCreatedBy(at.getCreatedBy());
		dto.setUpdatedBy(at.getUpdatedBy());
		return dto;
	}

	private AddressType toEntity(AddressTypeDto dto) {
		AddressType entity = new AddressType();
		entity.setAddressTypeId(dto.getAddressTypeId());
		entity.setAddressTypeName(dto.getAddressTypeName());
		entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
		entity.setIsDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false);

		if (dto.getCreatedBy() != null) {
			entity.setCreatedBy(dto.getCreatedBy());
		}

		return entity;
	}

	@Transactional(readOnly = true)
	public List<AddressTypeDto> getAllAddressTypes() {
		return addressTypeRepository.findAll().stream().filter(at -> at.getIsDeleted() == null || !at.getIsDeleted()) // exclude
																														// deleted
				.map(this::toDto).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public AddressType getAddressTypeById(Integer id) {
		return addressTypeRepository.findById(id).filter(at -> at.getIsDeleted() == null || !at.getIsDeleted())
				.orElseThrow(() -> new BusinessException("Address type not found with id: " + id));
	}

	@Transactional
	public AddressType createAddressType(AddressTypeDto dto) {
		boolean exists = addressTypeRepository.findByAddressTypeNameIgnoreCase(dto.getAddressTypeName())
				.filter(at -> at.getIsDeleted() == null || !at.getIsDeleted()).isPresent();

		if (exists) {
			throw new BusinessException("Address type already exists. Please use a unique name.");
		}

		AddressType addressType = toEntity(dto);
		return addressTypeRepository.save(addressType);
	}

	@Transactional
	public AddressType updateAddressType(Integer id, AddressTypeDto dto) {
		AddressType existing = addressTypeRepository.findById(id)
				.filter(at -> at.getIsDeleted() == null || !at.getIsDeleted())
				.orElseThrow(() -> new BusinessException("Cannot update. Address type not found with id: " + id));

		if (!existing.getAddressTypeName().equalsIgnoreCase(dto.getAddressTypeName())) {
			boolean exists = addressTypeRepository.findByAddressTypeNameIgnoreCase(dto.getAddressTypeName())
					.filter(at -> at.getIsDeleted() == null || !at.getIsDeleted()).isPresent();

			if (exists) {
				throw new BusinessException("Address type name already exists.");
			}
		}

		// ✅ Update only required fields on the existing entity
		existing.setAddressTypeName(dto.getAddressTypeName());
		existing.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : existing.getIsActive());
		existing.setUpdatedBy(dto.getUpdatedBy()); // allow null if not set

		return addressTypeRepository.save(existing);
	}

	@Transactional
	public void deleteAddressType(Integer id) {
		AddressType addressType = addressTypeRepository.findById(id)
				.filter(at -> at.getIsDeleted() == null || !at.getIsDeleted())
				.orElseThrow(() -> new BusinessException("Cannot delete. Address type not found with id: " + id));

		addressType.setIsDeleted(true);
		addressTypeRepository.save(addressType);
	}
}
