package com.formoura.product.serviceImp;

import com.formoura.exception.exception.BusinessException;
import com.formoura.product.category.repository.CategoryRepository;
import com.formoura.product.dto.request.ProductRequest;
import com.formoura.product.dto.response.ProductResponse;
import com.formoura.product.category.entity.Category;
import com.formoura.product.entity.Product;
import com.formoura.product.mapper.ProductMapper;
import com.formoura.product.repository.ProductRepository;
import com.formoura.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final CategoryRepository categoryRepository;

    private final ProductMapper mapper;

    @Override
    public ProductResponse createProduct(ProductRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new BusinessException("Category Not Found"));

        Product product = mapper.toEntity(request);

        product.setCategory(category);

        return mapper.toResponse(repository.save(product));
    }

    @Override
    public ProductResponse updateProduct(Long id,
                                         ProductRequest request) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Product Not Found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new BusinessException("Category Not Found"));

        product.setProductName(request.getProductName());

        product.setDescription(request.getDescription());

        product.setPrice(request.getPrice());

        product.setDiscountPrice(request.getDiscountPrice());

        product.setQuantity(request.getQuantity());

        product.setSku(request.getSku());

        product.setBrand(request.getBrand());

        product.setImageUrl(request.getImageUrl());

        product.setCategory(category);

        return mapper.toResponse(repository.save(product));
    }

    @Override
    public ProductResponse getProduct(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Product Not Found"));

        return mapper.toResponse(product);
    }

    @Override
    public void deleteProduct(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Product Not Found"));

        repository.delete(product);
    }

    @Override
    public Page<ProductResponse> getAllProducts(int page,
                                                int size,
                                                String sortBy) {

        Pageable pageable =
                PageRequest.of(page,
                        size,
                        Sort.by(sortBy).ascending());

        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public Page<ProductResponse> searchProducts(String keyword,
                                                int page,
                                                int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return repository
                .findByProductNameContainingIgnoreCase(keyword, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public Page<ProductResponse> getProductsByCategory(Long categoryId,
                                                       int page,
                                                       int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return repository.findByCategory_Id(categoryId, pageable)
                .map(mapper::toResponse);
    }

 /*   @Override
    public void deleteProduct(Long id){

        Product product=repository.findById(id)

                .orElseThrow(()->
                        new BusinessException("Product Not Found"));

        product.setActive(false);

        product.setDeletedAt(LocalDateTime.now());

        repository.save(product);

    }*/

}