package com.formoura.inventory.serviceImp;

import com.formoura.event.inventory.InventoryRollbackEvent;
import com.formoura.event.order.OrderCreatedEvent;
import com.formoura.event.order.OrderItemEvent;
import com.formoura.exception.exception.BusinessException;
import com.formoura.inventory.dto.request.InventoryRequest;
import com.formoura.inventory.dto.response.InventoryResponse;
import com.formoura.inventory.entity.Inventory;
import com.formoura.inventory.mapper.InventoryMapper;
import com.formoura.inventory.repository.InventoryRepository;
import com.formoura.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    private final InventoryMapper mapper;

    @Override
    public InventoryResponse createInventory(InventoryRequest request) {

        if (repository.existsByProductId(request.getProductId())) {
            throw new BusinessException("Inventory already exists for this product");
        }

        Inventory inventory = mapper.toEntity(request);

        return mapper.toResponse(repository.save(inventory));
    }

    @Override
    public InventoryResponse updateInventory(Long productId,
                                             InventoryRequest request) {

        Inventory inventory = repository.findByProductId(productId)
                .orElseThrow(() ->
                        new BusinessException("Inventory Not Found"));

        inventory.setAvailableQuantity(request.getAvailableQuantity());
        inventory.setMinimumStock(request.getMinimumStock());
        inventory.setWarehouse(request.getWarehouse());

        return mapper.toResponse(repository.save(inventory));
    }

    @Override
    public InventoryResponse getInventory(Long productId) {

        Inventory inventory = repository.findByProductId(productId)
                .orElseThrow(() ->
                        new BusinessException("Inventory Not Found"));

        return mapper.toResponse(inventory);
    }

    @Override
    public void deleteInventory(Long productId) {

        Inventory inventory = repository.findByProductId(productId)
                .orElseThrow(() ->
                        new BusinessException("Inventory Not Found"));

        repository.delete(inventory);
    }

    @Override
    public Page<InventoryResponse> getAllInventory(int page,
                                                   int size,
                                                   String sortBy) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy).ascending());

        return repository.findByActiveTrue(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public Page<InventoryResponse> getLowStockProducts(int quantity,
                                                       int page,
                                                       int size) {

        Pageable pageable = PageRequest.of(page, size);

        return repository
                .findByAvailableQuantityLessThanEqual(quantity, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public Page<InventoryResponse> searchByWarehouse(String warehouse,
                                                     int page,
                                                     int size) {

        Pageable pageable = PageRequest.of(page, size);

        return repository
                .findByWarehouseContainingIgnoreCase(warehouse, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public InventoryResponse reserveStock(Long productId,
                                          Integer quantity) {

        Inventory inventory = repository.findByProductId(productId)
                .orElseThrow(() ->
                        new BusinessException("Inventory Not Found"));

        if (inventory.getAvailableQuantity() < quantity) {
            throw new BusinessException("Insufficient Stock");
        }

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() - quantity);

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() + quantity);

        return mapper.toResponse(repository.save(inventory));
    }

    @Override
    public InventoryResponse releaseStock(Long productId,
                                          Integer quantity) {

        Inventory inventory = repository.findByProductId(productId)
                .orElseThrow(() ->
                        new BusinessException("Inventory Not Found"));

        if (inventory.getReservedQuantity() < quantity) {
            throw new BusinessException("Reserved quantity is insufficient");
        }

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() - quantity);

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() + quantity);

        return mapper.toResponse(repository.save(inventory));
    }

    @Override
    public InventoryResponse reduceStock(Long productId,
                                         Integer quantity) {

        Inventory inventory = repository.findByProductId(productId)
                .orElseThrow(() ->
                        new BusinessException("Inventory Not Found"));

        if (inventory.getReservedQuantity() < quantity) {
            throw new BusinessException("Reserved quantity is insufficient");
        }

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() - quantity);

        inventory.setSoldQuantity(
                inventory.getSoldQuantity() + quantity);

        return mapper.toResponse(repository.save(inventory));
    }

    @Override
    public void reduceStock(
            OrderCreatedEvent event) {

        for (OrderItemEvent item : event.getItems()) {

            Inventory inventory = repository

                    .findByProductId(item.getProductId())

                    .orElseThrow(() ->
                            new BusinessException(
                                    "Inventory Not Found"));

            if (inventory.getAvailableQuantity()
                    < item.getQuantity()) {

                throw new BusinessException(
                        "Insufficient Stock");

            }

            inventory.setAvailableQuantity(

                    inventory.getAvailableQuantity()
                            - item.getQuantity());

            repository.save(inventory);

        }

    }

    @Override
    public void rollbackInventory(
            InventoryRollbackEvent event){

        event.getProducts().forEach(

                (productId,qty)->{

                    Inventory inventory =
                            repository.findByProductId(
                                            productId)

                                    .orElseThrow(()->
                                            new BusinessException(
                                                    "Inventory Not Found"));

                    inventory.setAvailableQuantity(

                            inventory.getAvailableQuantity()
                                    + qty

                    );

                    inventory.setReservedQuantity(

                            inventory.getReservedQuantity()
                                    - qty

                    );

                    repository.save(inventory);

                });

    }

}