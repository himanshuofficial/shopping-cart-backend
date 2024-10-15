package com.shopping.himanshu.service.category;

import com.shopping.himanshu.exceptions.AlreadyExistsException;
import com.shopping.himanshu.exceptions.ResourceNotFound;
import com.shopping.himanshu.model.Category;

import java.util.List;


public interface CategoryServiceInt {
    Category getCategoryById(Long id) throws ResourceNotFound;
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category) throws AlreadyExistsException;
    Category updateCategory(Category category, Long id) throws ResourceNotFound;
    void deleteCategory(Long id);
}
