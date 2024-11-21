package com.dimas.invetoryManager.Controllers;

import com.dimas.invetoryManager.Entities.Product;
import com.dimas.invetoryManager.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping()
    public ResponseEntity<List<Product>> productList() {
        Optional<List<Product>> products = Optional.ofNullable(service.findAllProducts());
        if (products.isPresent()) return ResponseEntity.ok(service.findAllProducts());
        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public ResponseEntity<Product> productCreate(@RequestBody Product product) {
        return ResponseEntity.ok(service.productCreate(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> productPut(@PathVariable Long id, @RequestBody Product product){

        return ResponseEntity.ok(service.productPut(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> productDelete(@PathVariable Long id){
        return service.productDelete(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

}
