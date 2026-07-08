package com.formoura.product.category.serviceImp;

import com.formoura.exception.exception.BusinessException;
import com.formoura.product.category.dto.request.CategoryRequest;
import com.formoura.product.category.dto.response.CategoryResponse;
import com.formoura.product.category.entity.Category;
import com.formoura.product.category.mapper.CategoryMapper;
import com.formoura.product.category.repository.CategoryRepository;
import com.formoura.product.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    private final CategoryMapper mapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {

        if (repository.existsByName(request.getName())) {
            throw new BusinessException("Category already exists");
        }

        Category category = mapper.toEntity(request);

        return mapper.toResponse(
                repository.save(category));
    }

    @Override
    public CategoryResponse updateCategory(Long id,
                                           CategoryRequest request) {

        Category category = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Category not found"));

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());
        category.setActive(request.getActive());

        return mapper.toResponse(
                repository.save(category));
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {

        return mapper.toResponse(
                repository.findById(id)
                        .orElseThrow(() ->
                                new BusinessException("Category not found")));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void deleteCategory(Long id) {

        Category category = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Category not found"));

        repository.delete(category);
    }
}