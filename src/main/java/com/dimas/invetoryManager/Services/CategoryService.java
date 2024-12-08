package com.dimas.invetoryManager.Services;

import com.dimas.invetoryManager.Entities.Category;
import com.dimas.invetoryManager.Entities.Product;
import com.dimas.invetoryManager.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> findAllCategories() {
        return repository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return repository.findById(id);
    }

    public Category findByName(String name) {
        return repository.findFirstByName(name);
    }

    public Category productcreate(Category category) {
        return repository.save(category);
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
