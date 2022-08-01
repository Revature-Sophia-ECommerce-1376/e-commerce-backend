package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.dtos.PriceRangeRequest;
import com.revature.dtos.ProductInfo;
import com.revature.models.Product;
import com.revature.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.revature.annotations.AuthorizedAdmin;
import com.revature.dtos.CreateUpdateRequest;
import com.revature.exceptions.InvalidProductInputException;
import com.revature.exceptions.InvalidRoleException;
import com.revature.services.StorageService;


@RestController
@RequestMapping("/api/product" )
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class ProductController {

    private final ProductService productService;
    private final StorageService s3Srv;

    public ProductController(ProductService productService, StorageService storageService) {
        this.productService = productService;
        this.s3Srv = storageService;
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
    
    /**
     * create new product or update information to the existing product
     * 
     * @param createupdateRequest an object contains the information about
     * product (id, quantity, price, description, image, name)
     * 
     * @throws InvalidProductInputException if the implementation detect the 
     * inputs are not valid
     * 
     * @return Product - new product object or updated product object
     */
    @Authorized
    @AuthorizedAdmin
    @PutMapping("/create-update")
    public ResponseEntity<Product> insertAndUpdate(@RequestBody CreateUpdateRequest createupdateRequest) {
    	// get current product Id
    	int id =createupdateRequest.getId();
    	// get product by Id
    	Optional<Product> currPd = productService.findById(id);
    	// get updated information
    	int quantity = createupdateRequest.getQuantity();	
    	double price = createupdateRequest.getPrice();
    	String description = createupdateRequest.getDescription();
    	String image = createupdateRequest.getImage();
    	String name = createupdateRequest.getName();
    	
    	// update product if the product exist in db
    	if(currPd.isPresent()) {
    	
				Product updatePd = currPd.get();
				updatePd.setId(id);
				if(name != null) {
					updatePd.setName(name );
				}
				
				if(price > 0) {
					updatePd.setPrice(price);
				}
  	
				if(quantity > 0) {
  
					updatePd.setQuantity(quantity);    			
				}
				if(description != null) {
					updatePd.setDescription(description);
				}
				if(image!= null) {
					updatePd.setImage(image);
					
				}
				
				
				return ResponseEntity.ok(productService.save(updatePd));
    	}
    	// instantiate the product and insert to db
    	Product newPd;
		try {
			newPd = new Product(quantity,price,description,image,name);
		} catch (Exception e) {
		
			throw new InvalidProductInputException("Null value is not allowed");
		}
    	
    	return ResponseEntity.ok(productService.save(newPd));
    }

    
    @Authorized
    @AuthorizedAdmin
    @PutMapping("/uploadFile")
    public ResponseEntity<String> uploadImage(@RequestPart (value = "file") MultipartFile file){
    	return this.s3Srv.uploadFile(file);
    }
    
    @Authorized
    @PatchMapping
    public ResponseEntity<List<Product>> purchase(@RequestBody List<ProductInfo> metadata) { 	
    	List<Product> productList = new ArrayList<Product>();
    	
    	for (int i = 0; i < metadata.size(); i++) {
    		Optional<Product> optional = productService.findById(metadata.get(i).getId());

    		if(!optional.isPresent()) {
    			return ResponseEntity.notFound().build();
    		}

    		Product product = optional.get();

    		if(product.getQuantity() - metadata.get(i).getQuantity() < 0) {
    			return ResponseEntity.badRequest().build();
    		}
    		
    		product.setQuantity(product.getQuantity() - metadata.get(i).getQuantity());
    		productList.add(product);
    	}
        
        productService.saveAll(productList, metadata);

        return ResponseEntity.ok(productList);
    }

    @Authorized
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") int id) {
        Optional<Product> optional = productService.findById(id);

        if(!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productService.delete(id);

        return ResponseEntity.ok(optional.get());
    }
    // 
    /**
     * Creates a list of product that partially match the name of product
     * 
     * @param name - the name of product
     * 
     * @return List<Product> - a list of products that matching the name
     */
    @GetMapping("/partial-search/{name}")
    public ResponseEntity<List<Product>> getProductsByNameContains(@PathVariable("name") String name) {
    	
        return ResponseEntity.ok(productService.findByNameContains(name));
    }
    /**
     * create a list product sorted by the range of price
     * 
     * @param priceRangeRequest - an object contains the range of the price(minPrice, maxPrice)
     * 
     * @return List<Product> - a list of product within the price range
     */
    @Authorized
    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(@RequestBody PriceRangeRequest priceRangeRequest) {
    	
        return ResponseEntity.ok(productService.findByPriceRange(priceRangeRequest.getMinPrice(),priceRangeRequest.getMaxPrice()));
    }

    /**
     * create a list of product sorted by rating(average of star) in descending order
     * 
     * @return List<Product> - a list of product sorting by starts of product
     */
    @Authorized
    @GetMapping("/filter-rating")
    public ResponseEntity<List<Product>> filterByRating() {
    	
        return ResponseEntity.ok(productService.filterByRating());
    }
    

    
}
