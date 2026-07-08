package com.formoura.payment.mapper;

import com.formoura.payment.dto.request.PaymentRequest;
import com.formoura.payment.dto.response.PaymentResponse;
import com.formoura.payment.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toEntity(PaymentRequest request);

    PaymentResponse toResponse(Payment payment);

}