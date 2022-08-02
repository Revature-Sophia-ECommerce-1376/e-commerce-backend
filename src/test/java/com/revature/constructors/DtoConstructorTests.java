package com.revature.constructors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.revature.dtos.AddressRequest;
import com.revature.dtos.CreateUpdateRequest;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.PriceRangeRequest;
import com.revature.dtos.ProductInfo;
import com.revature.dtos.PurchaseRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.dtos.ReviewRequest;
import com.revature.dtos.UserRequest;

class DtoConstructorTests {
	@Test
	void testUserRequiredArgs() {
		assertThrows(NullPointerException.class, () ->{			
			UserRequest testReq = new UserRequest(null, null, null);
		});
	}
	@Test
	void testUserRequestAllArgs() {
		UserRequest testReq = new UserRequest(1, "email@email", "password", "First", "Last", "ADMIN");
		assertEquals(1, testReq.getId());
		assertEquals("email@email", testReq.getEmail());
		assertEquals("password", testReq.getPassword());
		assertEquals("First", testReq.getFirstName());
		assertEquals("Last", testReq.getLastName());
		assertEquals("ADMIN", testReq.getRole());
	}
	@Test
	void testUserRequestNoArgs() {
		UserRequest testReq = new UserRequest();
		testReq.setId(0);
		assertEquals(0, testReq.getId());
	}
	@Test
	void testReviewRequestAllArgs() {
		ReviewRequest testReq = new ReviewRequest(1, 1, 4, "Cool", "Good stuff");
		assertEquals("Cool", testReq.getTitle());
	}
	@Test
	void testReviewRequestNoArgs() {
		ReviewRequest testReq = new ReviewRequest();
		assertEquals(0, testReq.getUserId());
	}
	@Test
	void testRegisterRequestAllArgs() {
		RegisterRequest testReq = new RegisterRequest("email@email.com", "password", "First", "Last");
		assertEquals("password", testReq.getPassword());
	}
	@Test
	void testRegisterRequestNoArgs() {
		RegisterRequest testReq = new RegisterRequest();
		testReq.setPassword("password");
		assertEquals("password", testReq.getPassword());
	}
	@Test
	void testPurchaseRequestAllArgs() {
		PurchaseRequest testReq = new PurchaseRequest(1, 13, 3);
		assertEquals(1, testReq.getId());
	}
	@Test
	void testPurchaseRequestNoArgs() {
		PurchaseRequest testReq = new PurchaseRequest();
		assertEquals(0, testReq.getId());
	}
	@Test
	void ProductInfoAllArgs() {
		ProductInfo testReq = new ProductInfo(1, 5, 10.25, "nice headphones", "randomUrl", "Headphones");
		assertEquals(10.25, testReq.getPrice());
	}
	@Test
	void ProductInfoNoArgs() {
		ProductInfo testReq = new ProductInfo();
		assertEquals(0, testReq.getId());
	}
	@Test
	void PriceRangeRequestAllArgs() {
		PriceRangeRequest testReq = new PriceRangeRequest(1, 100);
		assertEquals(100, testReq.getMaxPrice());
	}
	@Test
	void PriceRangeRequestNoArgs() {
		PriceRangeRequest testReq = new PriceRangeRequest();
		assertEquals(0, testReq.getMaxPrice());
	}
	@Test
	void LoginRequestAllArgs() {
		LoginRequest testReq = new LoginRequest("email@email", "password");
		assertEquals("password", testReq.getPassword());
	}
	@Test
	void LoginRequestNoArgs() {
		LoginRequest testReq = new LoginRequest();
		testReq.setPassword("password");
		assertEquals("password", testReq.getPassword());
	}
	@Test
	void CreateUpdateRequestAllArgs() {
		CreateUpdateRequest testReq = new CreateUpdateRequest(1, 10, 12.0, "More Headphones", "Url", "Headphones");
		assertEquals("Headphones", testReq.getName());
	}
	@Test
	void CreateUpdateRequestNoArgs() {
		CreateUpdateRequest testReq = new CreateUpdateRequest();
		testReq.setName("Cool Name");
		assertEquals("Cool Name", testReq.getName());
	}
	@Test
	void AddressRequestAllArgs() {
		AddressRequest testReq = new AddressRequest(1, "123 mayburry", "", "victoria", "77142", "Texas");
		assertEquals("123 mayburry", testReq.getStreet());
	}
	@Test
	void AddressRequestNoArgs() {
		AddressRequest testReq = new AddressRequest();
		testReq.setStreet("123 apple");
		assertEquals("123 apple", testReq.getStreet());
	}
}
