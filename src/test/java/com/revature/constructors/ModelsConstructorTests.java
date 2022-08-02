package com.revature.constructors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
	void testNewUserRequiredArgs() {
		User testUser = new User("email", "pass", "first", "last", "customer");
		assertEquals(0, testUser.getId());
		assertEquals("email", testUser.getEmail());
		assertEquals("pass", testUser.getPassword());
		assertEquals("first", testUser.getFirstName());
		assertEquals("last", testUser.getLastName());
		assertEquals("customer", testUser.getRole());
		assertEquals(new HashSet<Purchase>(), testUser.getPurchases());
		assertEquals(new HashSet<Review>(), testUser.getReviews());
		assertEquals(new HashSet<Address>(), testUser.getAddresses());
	}
	@Test
	void testNewUserAllArgs(){
		User testUser = new User(1, "email@email.com", "Password","First" , "Last", "Customer", new HashSet<Purchase>(), new HashSet<Review>(), new HashSet<Address>());
		assertEquals(1, testUser.getId());
		assertEquals("email@email.com", testUser.getEmail());
		assertEquals("Password", testUser.getPassword());
		assertEquals("First", testUser.getFirstName());
		assertEquals("Last", testUser.getLastName());
		assertEquals("Customer", testUser.getRole());
		assertEquals(new HashSet<Purchase>(), testUser.getPurchases());
		assertEquals(new HashSet<Review>(), testUser.getReviews());
		assertEquals(new HashSet<Address>(), testUser.getAddresses());
		
	}
	
	@Test
	void testNewUserNoArgs() {
		User testUser = new User();
		assertEquals(0, testUser.getId());
		assertEquals(null, testUser.getEmail());
		assertEquals(null, testUser.getPassword());
		assertEquals(null, testUser.getFirstName());
		assertEquals(null, testUser.getLastName());
		assertEquals(null, testUser.getRole());
		assertEquals(new HashSet<Purchase>(), testUser.getPurchases());
		assertEquals(new HashSet<Review>(), testUser.getReviews());
		assertEquals(new HashSet<Address>(), testUser.getAddresses());
	}
	@Test
	void testProductRequieredArgs(){
		Product testProduct = new Product(1, 10.2, "desc", "url", "name");
		assertEquals(0, testProduct.getId());
		assertEquals(1, testProduct.getQuantity());
		assertEquals(10.2, testProduct.getPrice());
		assertEquals("desc", testProduct.getDescription());
		assertEquals("url", testProduct.getImage());
		assertEquals("name", testProduct.getName());
		assertEquals(new HashSet<Review>(), testProduct.getReviews());
		assertEquals(new HashSet<Purchase>(), testProduct.getPurchases());
	}
	@Test
	void testNewProductAllArgs() {
		Product testProduct = new Product(1, 2, 3.25, "A widget", "Random Url" , "widget", new HashSet<Review>(), new HashSet<Purchase>());
		assertEquals(1, testProduct.getId());
		assertEquals(2, testProduct.getQuantity());
		assertEquals(3.25, testProduct.getPrice());
		assertEquals("A widget", testProduct.getDescription());
		assertEquals("Random Url", testProduct.getImage());
		assertEquals("widget", testProduct.getName());
		assertEquals(new HashSet<Review>(), testProduct.getReviews());
		assertEquals(new HashSet<Purchase>(), testProduct.getPurchases());		
	}
	
	@Test
	void testNewProductNoId() {
		Product testProduct = new Product(2, 3.25, "A widget", "Random Url" , "widget", new HashSet<Review>(), new HashSet<Purchase>());
		assertEquals(2, testProduct.getQuantity());
		assertEquals(3.25, testProduct.getPrice());
		assertEquals("A widget", testProduct.getDescription());
		assertEquals("Random Url", testProduct.getImage());
		assertEquals("widget", testProduct.getName());
		assertEquals(new HashSet<Review>(), testProduct.getReviews());
		assertEquals(new HashSet<Purchase>(), testProduct.getPurchases());	
		
	}
	
	@Test
	void testNewProductNoArgs() {
		Product testProduct = new Product();
		assertEquals(0, testProduct.getId());
		assertEquals(0, testProduct.getQuantity());
		assertEquals(0, testProduct.getPrice());
		assertEquals(null, testProduct.getDescription());
		assertEquals(null, testProduct.getImage());
		assertEquals(null, testProduct.getName());
		assertEquals(new HashSet<Review>(), testProduct.getReviews());
		assertEquals(new HashSet<Purchase>(), testProduct.getPurchases());	
	}
	@Test
	void testNewReviewRequiredArgs() {
		User testUser = new User(1, "email@email.com", "Password","First" , "Last", "Customer", new HashSet<Purchase>(), new HashSet<Review>(), new HashSet<Address>());
		Product testProduct = new Product(2, 3.25, "A widget", "Random Url" , "widget", new HashSet<Review>(), new HashSet<Purchase>());
		Review testReview = new Review(3, "title", "review", testUser, testProduct);
		assertEquals(0, testReview.getId());
		assertEquals(3, testReview.getStars());
		assertEquals("title", testReview.getTitle());
		assertEquals("review", testReview.getReview());
		assertNull(testReview.getPosted());
		assertNull(testReview.getUpdated());
		assertEquals(testProduct, testReview.getProduct());
		assertEquals(testUser, testReview.getUser());
		
		testReview = new Review(3, "title", "review", testProduct, testUser);
		assertEquals(0, testReview.getId());
		assertEquals(3, testReview.getStars());
		assertEquals("title", testReview.getTitle());
		assertEquals("review", testReview.getReview());
		assertNull(testReview.getPosted());
		assertNull(testReview.getUpdated());
		assertEquals(testProduct, testReview.getProduct());
		assertEquals(testUser, testReview.getUser());
		
	}
	@Test
	void testNewReviewAllArgs() {
		User testUser = new User(1, "email@email.com", "Password","First" , "Last", "Customer", new HashSet<Purchase>(), new HashSet<Review>(), new HashSet<Address>());
		Product testProduct = new Product(2, 3.25, "A widget", "Random Url" , "widget", new HashSet<Review>(), new HashSet<Purchase>());
		Review testReview = new Review(1, 4, "test", "Good Item", null, null, testProduct, testUser);
		assertEquals(1, testReview.getId());
		assertEquals(4, testReview.getStars());
		assertEquals("test", testReview.getTitle());
		assertEquals("Good Item", testReview.getReview());
		assertEquals(null, testReview.getPosted());
		assertEquals(null, testReview.getUpdated());
		assertEquals(testProduct, testReview.getProduct());
		assertEquals(testUser, testReview.getUser());
	}
	@Test
	void testNewReviewLittleDetail() {
		User testUser = new User(1, "email@email.com", "Password","First" , "Last", "Customer", new HashSet<Purchase>(), new HashSet<Review>(), new HashSet<Address>());
		Product testProduct = new Product(2, 3.25, "A widget", "Random Url" , "widget", new HashSet<Review>(), new HashSet<Purchase>());
		Review testReview = new Review(4, "test", "Good Item", testProduct, testUser);
		assertEquals(4, testReview.getStars());
		assertEquals("test", testReview.getTitle());
		assertEquals("Good Item", testReview.getReview());
		assertEquals(testUser, testReview.getUser());
		assertEquals(testProduct, testReview.getProduct());
	}
	@Test
	void testNewReviewNoArgs() {
		Review testReview = new Review();
		assertEquals(0, testReview.getStars());
		assertEquals(null, testReview.getTitle());
		assertEquals(null, testReview.getReview());
		assertEquals(null, testReview.getUser());
		assertEquals(null, testReview.getProduct());
	}
	@Test
	void testNewPurchaseAllArgs() {
		Product testProduct = new Product(1, 2, 3.25, "A widget", "Random Url" , "widget", null, null);
		User testUser = new User(1, "email@email.com", "Password","First" , "Last", "Customer", null, null, null);
		Purchase testPurchase = new Purchase(1, null, testProduct, testUser, 10);
		assertEquals(1, testPurchase.getId());
		assertEquals(null, testPurchase.getOrderPlaced());
		assertEquals(testProduct, testPurchase.getProduct());
		assertEquals(testUser, testPurchase.getOwnerUser());
		assertEquals(10, testPurchase.getQuantity());
	}
	@Test
	void testNewPurchaseNoArgs() {
		Purchase testPurchase = new Purchase();
		assertEquals(0, testPurchase.getId());
		assertEquals(null, testPurchase.getOrderPlaced());
		assertEquals(null, testPurchase.getProduct());
		assertEquals(null, testPurchase.getOwnerUser());
		assertEquals(0, testPurchase.getQuantity());
	}
	@Test
	void testNewAddressRequired() {
		Address testAddress = new Address("street", "city", "zip", "state");
		assertEquals(0, testAddress.getId());
		assertEquals("street", testAddress.getStreet());
		assertNull(testAddress.getSecondary());
		assertEquals("state", testAddress.getState());
		assertEquals("zip", testAddress.getZip());
		assertEquals("city", testAddress.getCity());
		assertEquals(new User(), testAddress.getUsers());
		
	}
	@Test
	void testNewAddressAllArgs() {
		User testUser = new User(1, "email@email.com", "Password","First" , "Last", "Customer", null, null, null);
		Set<User> users = new HashSet<>();
		users.add(testUser);
		Address testAddress = new Address(1, "123 walaby way", "3", "Sydney", "1234", "big apple", users);
		assertEquals(1, testAddress.getId());
		assertEquals("123 walaby way", testAddress.getStreet());
		assertEquals("3", testAddress.getSecondary());
		assertEquals("big apple", testAddress.getState());
		assertEquals("1234", testAddress.getZip());
		assertEquals("Sydney", testAddress.getCity());
		assertEquals(users, testAddress.getUsers());
	}
	@Test
	void testNewAddressNoArgs() {
		Address testAddress = new Address();
		assertEquals(0, testAddress.getId());
		assertEquals(null, testAddress.getStreet());
		assertEquals(null, testAddress.getSecondary());
		assertEquals(null, testAddress.getState());
		assertEquals(null, testAddress.getZip());
		assertEquals(null, testAddress.getCity());
		assertEquals(new HashSet<User>(), testAddress.getUsers());
	}
}
