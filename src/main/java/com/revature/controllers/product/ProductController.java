package com.revature.controllers.product;


import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.PriceRangeRequest;
import com.revature.models.Product;
import com.revature.services.ProductService;

@RestController
@RequestMapping("/api/public/product" )
@CrossOrigin(origins = "*", allowCredentials = "true")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getInventory() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
        Optional<Product> optional = productService.findById(id);

        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/partial-search/{name}")
    public ResponseEntity<List<Product>> getProductsByNameContains(@PathVariable("name") String name) {
    	
        return ResponseEntity.ok(productService.findByNameContains(name));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(@RequestBody PriceRangeRequest priceRangeRequest) {
    	
        return ResponseEntity.ok(productService.findByPriceRange(priceRangeRequest.getMinPrice(),priceRangeRequest.getMaxPrice()));
    }

    @GetMapping("/filter-rating")
    public ResponseEntity<List<Product>> filterByRating() {
    	
        return ResponseEntity.ok(productService.filterByRating());
    }
    

    
}
