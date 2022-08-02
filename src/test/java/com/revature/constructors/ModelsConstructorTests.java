package com.revature.constructors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.revature.models.Address;
import com.revature.models.Product;
import com.revature.models.Purchase;
import com.revature.models.Review;
import com.revature.models.User;

class ModelsConstructorTests {
	
	@Test
	void testNewUserAllArgs(){
		User testUser = new User(1, "email@email.com", "Password","First" , "Last", "Customer", null, null, null);
		int actual = testUser.getId();
		assertEquals(1, actual);
	}
	
	@Test
	void testNewUserNoArgs() {
		User testUser = new User();
		assertEquals(0, testUser.getId());
	}
	@Test
	void testNewProductAllArgs() {
		Product testProduct = new Product(1, 2, 3.25, "A widget", "Random Url" , "widget", null, null);
		int actual = testProduct.getId();
		assertEquals(1, actual);
	}
	
	@Test
	void testNewProductNoId() {
		Product testProduct = new Product(2, 3.25, "A widget", "Random Url" , "widget", null, null);
		int actual = testProduct.getQuantity();
		assertEquals(2, actual);
	}
	
	@Test
	void testNewProductNoArgs() {
		Product testProduct = new Product();
		assertEquals(0, testProduct.getId());
	}
	@Test
	void testNewReviewAllArgs() {
		User testUser = new User(1, "email@email.com", "Password","First" , "Last", "Customer", null, null, null);
		Product testProduct = new Product(1, 2, 3.25, "A widget", "Random Url" , "widget", null, null);
		Review testReview = new Review(1, 4, "test", "Good Item", null, null, testProduct, testUser);
		Product actual = testReview.getProduct();
		assertEquals(testProduct, actual);
	}
	@Test
	void testNewReviewLittleDetail() {
		User testUser = new User(1, "email@email.com", "Password","First" , "Last", "Customer", null, null, null);
		Product testProduct = new Product(1, 2, 3.25, "A widget", "Random Url" , "widget", null, null);
		Review testReview = new Review(4, "test", "Good Item", testUser, testProduct);
		Product actual = testReview.getProduct();
		assertEquals(testProduct, actual);
	}
	@Test
	void testNewReviewNoArgs() {
		Review testReview = new Review();
		assertEquals(null, testReview.getProduct());
	}
	@Test
	void testNewPurchaseAllArgs() {
		Product testProduct = new Product(1, 2, 3.25, "A widget", "Random Url" , "widget", null, null);
		User testUser = new User(1, "email@email.com", "Password","First" , "Last", "Customer", null, null, null);
		Purchase testPurchase = new Purchase(1, null, testProduct, testUser, 10);
		Product actual = testPurchase.getProduct();
		assertEquals(testProduct, actual);
	}
	@Test
	void testNewPurchaseNoArgs() {
		Purchase testPurchase = new Purchase();
		assertEquals(null, testPurchase.getProduct());
	}
	@Test
	void testNewAddressAllArgs() {
		User testUser = new User(1, "email@email.com", "Password","First" , "Last", "Customer", null, null, null);
		Set<User> users = new HashSet<>();
		users.add(testUser);
		Address testAddress = new Address(1, "123 walaby way", "", "Sydney", "1234", "big apple", users);
		assertEquals(users, testAddress.getUsers());
	}
	@Test
	void testNewAddressNoArgs() {
		Address testAddress = new Address();
		assertEquals(0, testAddress.getId());
	}
}
