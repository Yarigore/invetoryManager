package com.dimas.invetoryManager.Repositories;

import com.dimas.invetoryManager.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

    public Product findFirstById(Long id);

}
