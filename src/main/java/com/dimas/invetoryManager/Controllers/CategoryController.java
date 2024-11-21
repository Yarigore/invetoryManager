package com.dimas.invetoryManager.Controllers;

import com.dimas.invetoryManager.Entities.Category;
import com.dimas.invetoryManager.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping()
    public ResponseEntity<Category> categoryCreate(@RequestBody Category category){
        return ResponseEntity.ok(service.productcreate(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



}
