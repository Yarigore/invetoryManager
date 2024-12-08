package com.dimas.invetoryManager.Controllers;

import com.dimas.invetoryManager.Entities.Category;
import com.dimas.invetoryManager.Entities.Product;
import com.dimas.invetoryManager.Services.CategoryService;
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
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<Product>> productList() {
        Optional<List<Product>> products = Optional.ofNullable(productService.findAllProducts());
        if (products.isPresent()) return ResponseEntity.ok(productService.findAllProducts());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Product> productCreate(@RequestBody Product product) {
        return ResponseEntity.ok(productService.productCreate(product));
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Product> productPut(@PathVariable Long id, @RequestBody Product product) {

        return ResponseEntity.ok(productService.productPut(id, product));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Product> productDelete(@PathVariable Long id) {
        return productService.productDelete(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    // Endpoint para mostrar productos con bajo stock (cantidad < X)
    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> getLowStockProducts(@RequestParam int threshold) {
        List<Product> lowStockProducts = productService.findProductsWithLowStock(threshold);
        if (!lowStockProducts.isEmpty()) {
            return ResponseEntity.ok(lowStockProducts);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterProducts(
            @RequestParam(required = false) String categoryName,  // Recibe solo el nombre de la categoría
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        // Busca la categoría en el repositorio
        Optional<Category> category = Optional.ofNullable(categoryService.findByName(categoryName));

        // Si el nombre de la categoría no corresponde a ninguna, devolver un mensaje específico
        if (categoryName != null && category.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("La categoría con el nombre '" + categoryName + "' no existe en el sistema.");
        }

        // Solo imprime si la categoría está presente
        if (category.isPresent()) {
            System.out.println("ID: " + category.get().getId());
            System.out.println("Name: " + category.get().getName());
        } else {
            System.out.println("No se proporcionó una categoría válida.");
        }

        // Filtrar productos
        List<Product> filteredProducts = productService.filterProducts(category, minPrice, maxPrice);

        // Retornar respuesta
        if (!filteredProducts.isEmpty()) {
            return ResponseEntity.ok(filteredProducts);
        }
        return ResponseEntity.noContent().build();
    }
    
}
