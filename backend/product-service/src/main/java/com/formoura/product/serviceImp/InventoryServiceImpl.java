package com.formoura.product.serviceImp;

import com.formoura.exception.exception.BusinessException;
import com.formoura.product.entity.Product;
import com.formoura.product.entity.StockStatus;
import com.formoura.product.repository.ProductRepository;
import com.formoura.product.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl
        implements InventoryService {

    private final ProductRepository repository;

    @Override
    public void increaseStock(Long productId,
                              Integer quantity){

        Product product=repository.findById(productId)

                .orElseThrow(()->new BusinessException("Product Not Found"));

        product.setStock(product.getStock()+quantity);

        updateStock(product);

        repository.save(product);

    }

    @Override
    public void decreaseStock(Long productId,
                              Integer quantity){

        Product product=repository.findById(productId)

                .orElseThrow(()->new BusinessException("Product Not Found"));

        if(product.getStock()<quantity){

            throw new BusinessException("Insufficient Stock");

        }

        product.setStock(product.getStock()-quantity);

        updateStock(product);

        repository.save(product);

    }

    @Override
    public Integer availableStock(Long productId){

        return repository.findById(productId)

                .orElseThrow(()->new BusinessException("Product Not Found"))

                .getStock();

    }

    private void updateStock(Product product){

        if(product.getStock()==0){

            product.setStockStatus(StockStatus.OUT_OF_STOCK);

        }else if(product.getStock()<10){

            product.setStockStatus(StockStatus.LOW_STOCK);

        }else{

            product.setStockStatus(StockStatus.IN_STOCK);

        }

    }

}