package com.formoura.inventory.controller;

import com.formoura.inventory.dto.request.InventoryRequest;
import com.formoura.inventory.dto.response.InventoryResponse;
import com.formoura.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory API", description = "Inventory Management APIs")
public class InventoryController {

    private final InventoryService inventoryService;

    @Operation(summary = "Create Inventory")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse createInventory(
            @Valid @RequestBody InventoryRequest request) {

        return inventoryService.createInventory(request);
    }

    @Operation(summary = "Update Inventory")
    @PutMapping("/{productId}")
    public InventoryResponse updateInventory(
            @PathVariable Long productId,
            @Valid @RequestBody InventoryRequest request) {

        return inventoryService.updateInventory(productId, request);
    }

    @Operation(summary = "Get Inventory By Product")
    @GetMapping("/{productId}")
    public InventoryResponse getInventory(
            @PathVariable Long productId) {

        return inventoryService.getInventory(productId);
    }

    @Operation(summary = "Delete Inventory")
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(
            @PathVariable Long productId) {

        inventoryService.deleteInventory(productId);
    }

    @Operation(summary = "Get All Inventory")
    @GetMapping
    public Page<InventoryResponse> getAllInventory(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "productId")
            String sortBy) {

        return inventoryService.getAllInventory(
                page,
                size,
                sortBy);
    }

    @Operation(summary = "Get Low Stock Products")
    @GetMapping("/low-stock")
    public Page<InventoryResponse> getLowStockProducts(

            @RequestParam(defaultValue = "10")
            int quantity,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        return inventoryService.getLowStockProducts(
                quantity,
                page,
                size);
    }

    @Operation(summary = "Search Inventory By Warehouse")
    @GetMapping("/warehouse")
    public Page<InventoryResponse> searchWarehouse(

            @RequestParam String warehouse,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        return inventoryService.searchByWarehouse(
                warehouse,
                page,
                size);
    }

    @Operation(summary = "Reserve Stock")
    @PutMapping("/reserve/{productId}")
    public InventoryResponse reserveStock(

            @PathVariable Long productId,

            @RequestParam Integer quantity) {

        return inventoryService.reserveStock(
                productId,
                quantity);
    }

    @Operation(summary = "Release Reserved Stock")
    @PutMapping("/release/{productId}")
    public InventoryResponse releaseStock(

            @PathVariable Long productId,

            @RequestParam Integer quantity) {

        return inventoryService.releaseStock(
                productId,
                quantity);
    }

    @Operation(summary = "Reduce Stock After Order")
    @PutMapping("/reduce/{productId}")
    public InventoryResponse reduceStock(

            @PathVariable Long productId,

            @RequestParam Integer quantity) {

        return inventoryService.reduceStock(
                productId,
                quantity);
    }

}