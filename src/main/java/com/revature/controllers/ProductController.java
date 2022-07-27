package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
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

import com.revature.annotations.Authorized;
import com.revature.annotations.AuthorizedAdmin;
import com.revature.dtos.CreateUpdateRequest;
import com.revature.dtos.ProductInfo;
import com.revature.exceptions.InvalidProductInputException;
import com.revature.exceptions.InvalidRoleException;
import com.revature.models.Product;
import com.revature.services.ProductService;
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

    @Authorized
    @GetMapping
    public ResponseEntity<List<Product>> getInventory() {
        return ResponseEntity.ok(productService.findAll());
    }

    @Authorized
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
        Optional<Product> optional = productService.findById(id);

        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @Authorized
    @AuthorizedAdmin
    @PutMapping("/create-update")
    public ResponseEntity<Product> insertAndUpdate(@RequestBody CreateUpdateRequest createupdateRequest) {
    	int id =createupdateRequest.getId();
    	Optional<Product> currPd = productService.findById(id);
    	
    	int quantity = createupdateRequest.getQuantity();	
    	double price = createupdateRequest.getPrice();
    	String description = createupdateRequest.getDescription();
    	String image = createupdateRequest.getImage();
    	String name = createupdateRequest.getName();
    	
     
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
}
