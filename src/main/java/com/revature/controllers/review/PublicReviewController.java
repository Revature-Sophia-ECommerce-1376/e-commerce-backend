package com.revature.controllers.review;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.revature.models.Review;
import com.revature.services.ReviewService;

@RestController
@RequestMapping("/api/public/review")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class PublicReviewController {

	private ReviewService reviewService;
	
	public PublicReviewController(ReviewService reviewService) {
		super();
		this.reviewService = reviewService;
	}
	
	// Get All
	@GetMapping
	public ResponseEntity<List<Review>> getReviews() {
		return ResponseEntity.ok(reviewService.findAll());
	}
	
	// Get all reviews about a given product
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getReviewsOfProduct(@PathVariable("productId") int productId) {
		try {
			return ResponseEntity.ok(reviewService.findByProductId(productId));
		} catch(ResourceAccessException e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(null);
		}
	}
	
	// Get all reviews written by a given user
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable("userId") int userId) {
		try {
			return ResponseEntity.ok(reviewService.findByUserId(userId));
		} catch(ResourceAccessException e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(null);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Review> getReviewById(@PathVariable("id") int id) {
		Optional<Review> optR = reviewService.findById(id);
		if(optR.isPresent()) {
			return ResponseEntity.ok(optR.get());
		} else {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(null);
		}
	}
	
}
