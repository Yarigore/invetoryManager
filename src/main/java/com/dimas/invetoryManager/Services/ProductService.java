package com.dimas.invetoryManager.Services;

import com.dimas.invetoryManager.Entities.Category;
import com.dimas.invetoryManager.Entities.Product;
import com.dimas.invetoryManager.Repositories.CategoryRepository;
import com.dimas.invetoryManager.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Product productCreate(Product product) {
        if (product.getCategory() != null) {
            Long categoryId = product.getCategory().getId();
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
            product.setCategory(category);
        }
        return productRepository.save(product);
    }


    public Product productById(Long id){
        return productRepository.findFirstById(id);
    }

    public Product productPut(Long id, Product product){
        Product existingProduct = productRepository.findFirstById(id);
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setStock(product.getStock());
        existingProduct.setPrice(product.getPrice());
        return productRepository.save(existingProduct);
    }

    public Optional<Product> productDelete(Long id){

        Optional<Product> productDelete = productRepository.findById(id);
        productRepository.deleteById(id);
        return productDelete;
    }
}
