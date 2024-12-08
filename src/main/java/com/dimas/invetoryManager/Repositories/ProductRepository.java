package com.dimas.invetoryManager.Repositories;

import com.dimas.invetoryManager.Entities.Category;
import com.dimas.invetoryManager.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    public Product findFirstById(Long id);

    public List<Product> findByStockLessThan(int threshold);
    public List<Product> findByCategory(Category category);
    public List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    public List<Product> findByCategoryAndPriceBetween(Category category, Double minPrice, Double maxPrice);

}
