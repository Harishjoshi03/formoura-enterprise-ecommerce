package com.formoura.inventory.mapper;

import com.formoura.inventory.dto.request.InventoryRequest;
import com.formoura.inventory.dto.response.InventoryResponse;
import com.formoura.inventory.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservedQuantity", ignore = true)
    @Mapping(target = "soldQuantity", ignore = true)
    @Mapping(target = "returnedQuantity", ignore = true)
    @Mapping(target = "damagedQuantity", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Inventory toEntity(InventoryRequest request);

    InventoryResponse toResponse(Inventory inventory);

}