package com.formoura.user.serviceImp;

import com.formoura.exception.exception.BusinessException;
import com.formoura.user.dto.request.AddressRequest;
import com.formoura.user.dto.response.AddressResponse;
import com.formoura.user.entity.Address;
import com.formoura.user.mapper.AddressMapper;
import com.formoura.user.repository.AddressRepository;
import com.formoura.user.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;

    private final AddressMapper mapper;

    @Override
    public AddressResponse addAddress(Long userId,
                                      AddressRequest request) {

        Address address=mapper.toEntity(request);

        address.setUserId(userId);

        return mapper.toResponse(
                repository.save(address));

    }

    @Override
    public AddressResponse updateAddress(Long id,
                                         AddressRequest request) {

        Address address=repository.findById(id)

                .orElseThrow(()->
                        new BusinessException("Address Not Found"));

        address.setFullName(request.getFullName());

        address.setMobile(request.getMobile());

        address.setAddressLine1(request.getAddressLine1());

        address.setAddressLine2(request.getAddressLine2());

        address.setCity(request.getCity());

        address.setState(request.getState());

        address.setCountry(request.getCountry());

        address.setPinCode(request.getPinCode());

        address.setDefaultAddress(
                request.isDefaultAddress());

        return mapper.toResponse(
                repository.save(address));

    }

    @Override
    public List<AddressResponse> getAddresses(Long userId) {

        return repository.findByUserId(userId)

                .stream()

                .map(mapper::toResponse)

                .toList();

    }

    @Override
    public void deleteAddress(Long id) {

        repository.deleteById(id);

    }

}