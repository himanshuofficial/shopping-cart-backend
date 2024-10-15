package com.shopping.himanshu.service.category;

import com.shopping.himanshu.exceptions.AlreadyExistsException;
import com.shopping.himanshu.exceptions.ResourceNotFound;
import com.shopping.himanshu.model.Category;
import com.shopping.himanshu.repository.CategoryRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class categoryService implements CategoryServiceInt{

    private final CategoryRepository categoryRepo;

    @Override
    public Category getCategoryById(Long id) throws ResourceNotFound {
        return categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepo.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return null;
    }

    @Override
    public Category addCategory(Category category) throws AlreadyExistsException {
        return Optional.ofNullable(category).filter(c -> !categoryRepo.existsByName(c.getName()))
                .map(categoryRepo::save)
                .orElseThrow(() -> new AlreadyExistsException(category.getName() + " already exists"));
    }

    @Override
    public Category updateCategory(Category category, Long id) throws ResourceNotFound {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return categoryRepo.save(oldCategory);
                }).orElseThrow(() -> new ResourceNotFound("Category not found"));

    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepo.findById(id)
                .ifPresentOrElse(categoryRepo::delete, () -> {
                    try {
                        throw new ResourceNotFound("Category not found");
                    } catch (ResourceNotFound e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
