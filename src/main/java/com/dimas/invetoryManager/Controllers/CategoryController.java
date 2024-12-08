package com.dimas.invetoryManager.Controllers;

import com.dimas.invetoryManager.Entities.Category;
import com.dimas.invetoryManager.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping()
    public ResponseEntity<List<Category>> categoryList() {
        Optional<List<Category>> products = Optional.ofNullable(service.findAllCategories());
        if (products.isPresent()) return ResponseEntity.ok(service.findAllCategories());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Category> categoryCreate(@RequestBody Category category) {
        return ResponseEntity.ok(service.productcreate(category));
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Category> categoryPut(@PathVariable Long id, @RequestBody Category category) {
        return ResponseEntity.ok(service.categoryPut(id, category));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Category> categoryDelete(@PathVariable Long id) {
        return service.categoryDelete(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}
