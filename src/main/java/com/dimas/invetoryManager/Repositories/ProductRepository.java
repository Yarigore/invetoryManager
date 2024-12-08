package com.dimas.invetoryManager.Repositories;

import com.dimas.invetoryManager.Entities.Category;
import com.dimas.invetoryManager.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Product findFirstById(Long id);

    List<Product> findByStockLessThan(int threshold);
    List<Product> findByCategory(Category category);
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Product> findByCategoryAndPriceBetween(Category category, Double minPrice, Double maxPrice);

}
