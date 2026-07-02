package com.formoura.user.service;

import com.formoura.user.dto.request.AddressRequest;
import com.formoura.user.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {

    AddressResponse addAddress(Long userId,
                               AddressRequest request);

    AddressResponse updateAddress(Long addressId,
                                  AddressRequest request);

    List<AddressResponse> getAddresses(Long userId);

    void deleteAddress(Long addressId);

}