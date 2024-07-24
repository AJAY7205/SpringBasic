package com.ecommerce.project.service;
import com.ecommerce.project.exception.APIException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepositories;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{


    @Autowired
    private CategoryRepositories categoryRepositories;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepositories.findAll();
        if (categories.isEmpty()){
            throw new ResourceNotFoundException("Category");
        }
        List<CategoryDTO> categoryDTOS = categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepositories.findByCategoryName(category.getCategoryName());
        if(savedCategory!=null){
            throw new APIException("Category with the name " + category.getCategoryName() + " Already exists");
        }
        categoryRepositories.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepositories.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
        List<Category> categories = categoryRepositories.findAll();

        categoryRepositories.delete(category);
        return "Category with CategoryId: " + categoryId + " deleted successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Optional<Category> savedCategoryOptional = categoryRepositories.findById(categoryId);
        Category savedCategory = savedCategoryOptional.orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
        category.setCategoryId(categoryId);
        savedCategory = categoryRepositories.save(category);
        return savedCategory;
    }
}
