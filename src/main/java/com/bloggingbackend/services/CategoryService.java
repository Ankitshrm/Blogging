package com.bloggingbackend.services;

import com.bloggingbackend.models.Category;
import com.bloggingbackend.payloads.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO getCategoryById(Integer categoryId);

    List<CategoryDTO> getAllCategory();

    CategoryDTO updateCategory(CategoryDTO categoryDTO,Integer categoryId);

    void deleteCategory(Integer categoryId);
}
