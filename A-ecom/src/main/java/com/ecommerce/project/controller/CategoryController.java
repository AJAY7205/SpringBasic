package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    //@RequestMapping(value ="/public/categories" ,method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getAllCategories(){
        CategoryResponse categoryResponse = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }
    @PostMapping("/public/categories")
    //@RequestMapping(value ="/public/categories" ,method = RequestMethod.POST)
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>("Category added",HttpStatus.CREATED);

    }
    @DeleteMapping("/admin/categories/{categoryId}")
    //@RequestMapping(value ="/admin/categories/{categoryId}" ,method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        String status = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(status, HttpStatus.OK);

    }

    @PutMapping("/admin/categories/{categoryId}")
    //@RequestMapping(value ="/admin/categories/{categoryId}" ,method = RequestMethod.PUT)
    public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category,@PathVariable Long categoryId){

        Category savedCategory = categoryService.updateCategory(category,categoryId);
        return new ResponseEntity<>("Category with category ID:" + categoryId + " Updated",HttpStatus.OK);

    }
}
