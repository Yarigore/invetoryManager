package com.dimas.invetoryManager.Repositories;

import com.dimas.invetoryManager.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Category findFirstById(Long id);

    public Category findFirstByName(String name);


}
