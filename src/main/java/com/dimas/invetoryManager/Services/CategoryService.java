package com.dimas.invetoryManager.Services;

import com.dimas.invetoryManager.Entities.Category;
import com.dimas.invetoryManager.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category productcreate(Category category) {
        return repository.save(category);
    }

    public Optional<Category> findById(Long id) {
        return repository.findById(id);
    }

    public Category categoryPut(Long id, Category category) {
        Optional<Category> categoryOptional = repository.findById(id);
        categoryOptional.get().setName(category.getName());
        repository.save(categoryOptional.get());
        return categoryOptional.get();
    }

    public Optional<Category> categoryDelete(Long id) {

        Optional<Category> categoryDelete = repository.findById(id);
        repository.deleteById(id);
        return categoryDelete;
    }

}
