package com.formoura.user.mapper;

import com.formoura.user.dto.request.AddressRequest;
import com.formoura.user.dto.response.AddressResponse;
import com.formoura.user.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toEntity(AddressRequest request);

    AddressResponse toResponse(Address address);

}