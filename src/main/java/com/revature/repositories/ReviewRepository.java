package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Product;
import com.revature.models.Review;
import com.revature.models.User;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

	List<Review> findByProduct(Product p);
	List<Review> findByUser(User u);
}
