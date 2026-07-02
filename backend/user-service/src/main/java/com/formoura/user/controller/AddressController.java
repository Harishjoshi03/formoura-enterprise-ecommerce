package com.formoura.user.controller;

import com.formoura.user.dto.request.AddressRequest;
import com.formoura.user.dto.response.AddressResponse;
import com.formoura.user.service.AddressService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponse addAddress(
            @PathVariable Long userId,
            @Valid @RequestBody AddressRequest request){

        return addressService.addAddress(userId,request);

    }

    @PutMapping("/{addressId}")
    public AddressResponse updateAddress(
            @PathVariable Long addressId,
            @Valid @RequestBody AddressRequest request){

        return addressService.updateAddress(addressId,request);

    }

    @GetMapping("/{userId}")
    public List<AddressResponse> getAddresses(
            @PathVariable Long userId){

        return addressService.getAddresses(userId);

    }

    @DeleteMapping("/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(
            @PathVariable Long addressId){

        addressService.deleteAddress(addressId);

    }

}