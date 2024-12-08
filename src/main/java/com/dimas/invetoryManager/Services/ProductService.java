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

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
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


    public Product productById(Long id) {
        return productRepository.findFirstById(id);
    }

    public Product productPut(Long id, Product product) {
        Product existingProduct = productRepository.findFirstById(id);
        if (existingProduct == null) {
            throw new RuntimeException("Product not found with ID: " + id);
        }

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setStock(product.getStock());
        existingProduct.setPrice(product.getPrice());

        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Long categoryId = product.getCategory().getId();
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
            existingProduct.setCategory(category);
        } else {
            existingProduct.setCategory(null); // Limpia la categoría si no se proporciona un ID válido
        }

        return productRepository.save(existingProduct);
    }


    public Optional<Product> productDelete(Long id) {

        Optional<Product> productDelete = productRepository.findById(id);
        productRepository.deleteById(id);
        return productDelete;
    }

    public List<Product> findProductsWithLowStock(int threshold) {
        // Suponiendo que tienes un repositorio que puede buscar productos con stock bajo
        return productRepository.findByStockLessThan(threshold);
    }

    public List<Product> filterProducts(Optional<Category> category, Double minPrice, Double maxPrice) {

        Category categoryOptional = category.orElse(null);

        if (categoryOptional != null && minPrice != null && maxPrice != null) {
            return productRepository.findByCategoryAndPriceBetween(categoryOptional, minPrice, maxPrice);
        } else if (categoryOptional != null) {
            return productRepository.findByCategory(categoryOptional);
        } else if (minPrice != null && maxPrice != null) {
            return productRepository.findByPriceBetween(minPrice, maxPrice);
        } else {
            return productRepository.findAll();
        }
    }


}
