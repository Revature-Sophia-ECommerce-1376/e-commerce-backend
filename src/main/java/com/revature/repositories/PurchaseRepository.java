package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Purchase;
import com.revature.models.User;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
	
	List<Purchase> findByOwnerUser(User ownerUser);

}
