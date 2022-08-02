package com.revature.constructors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.contains;

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
		UserRequest testReq = new UserRequest("apple@email", "First", "Last");
		assertEquals("apple@email", testReq.getEmail());
		assertEquals("First", testReq.getFirstName());
		assertEquals("Last", testReq.getLastName());
		assertThat(testReq.toString().contains("email=apple@email"));
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
		assertThat(testReq.toString().contains("password=password"));
	}
	@Test
	void testUserRequestNoArgs() {
		UserRequest testReq = new UserRequest();
		assertEquals(null, testReq.getId());
		assertEquals(null, testReq.getEmail());
		assertEquals(null, testReq.getPassword());
		assertEquals(null, testReq.getFirstName());
		assertEquals(null, testReq.getLastName());
		assertEquals(null, testReq.getRole());
		assertThat(testReq.toString().contains("id=null"));
	}
	@Test
	void testReviewRequestRequiredArgs() {
		ReviewRequest testReq = new ReviewRequest("title", "review");
		assertEquals("title", testReq.getTitle());
		assertEquals("review", testReq.getReview());
		assertThat(testReq.toString().contains("title=title"));
	}
	@Test
	void testReviewRequestAllArgs() {
		ReviewRequest testReq = new ReviewRequest(1, 1, 4, "Cool", "Good stuff");
		assertEquals(1, testReq.getUserId());
		assertEquals(1, testReq.getProductId());
		assertEquals(4, testReq.getStars());
		assertEquals("Cool", testReq.getTitle());
		assertEquals("Good stuff", testReq.getReview());
		assertThat(testReq.toString().contains("title=Cool"));
	}
	@Test
	void testReviewRequestNoArgs() {
		ReviewRequest testReq = new ReviewRequest();
		assertEquals(0, testReq.getUserId());
		assertEquals(0, testReq.getProductId());
		assertEquals(0, testReq.getStars());
		assertEquals(null, testReq.getTitle());
		assertEquals(null, testReq.getReview());
		assertThat(testReq.toString().contains("id=0"));
	}
	
	@Test
	void testRegisterRequestAllArgs() {
		RegisterRequest testReq = new RegisterRequest("email@email.com", "password", "First", "Last");
		assertEquals("email@email.com", testReq.getEmail());
		assertEquals("password", testReq.getPassword());
		assertEquals("First", testReq.getFirstName());
		assertEquals("Last", testReq.getLastName());
		assertThat(testReq.toString().contains("email=email@email.com"));
	}
	@Test
	void testRegisterRequestNoArgs() {
		RegisterRequest testReq = new RegisterRequest();
		assertEquals(null, testReq.getEmail());
		assertEquals(null, testReq.getPassword());
		assertEquals(null, testReq.getFirstName());
		assertEquals(null, testReq.getLastName());
		assertThat(testReq.toString().contains("password=null"));
	}
	@Test
	void testPurchaseRequestAllArgs() {
		PurchaseRequest testReq = new PurchaseRequest(1, 13, 3);
		assertEquals(1, testReq.getId());
		assertEquals(13, testReq.getUserId());
		assertEquals(3, testReq.getQuantity());
		assertThat(testReq.toString().contains("id=1"));
	}
	@Test
	void testPurchaseRequestNoArgs() {
		PurchaseRequest testReq = new PurchaseRequest();
		assertEquals(0, testReq.getId());
		assertEquals(0, testReq.getUserId());
		assertEquals(0, testReq.getQuantity());
		assertThat(testReq.toString().contains("id=0"));
	}
	@Test
	void ProductInfoRequiredArgs() {
		ProductInfo testReq = new ProductInfo(1, 10, "desc", "url", "name");
		assertEquals(1, testReq.getQuantity());
		assertEquals(10, testReq.getPrice());
		assertEquals("desc", testReq.getDescription());
		assertEquals("url", testReq.getImage());
		assertEquals("name", testReq.getName());
		assertThat(testReq.toString().contains("description=desc"));
	}
	@Test
	void ProductInfoAllArgs() {
		ProductInfo testReq = new ProductInfo(1, 5, 10.25, "nice headphones", "randomUrl", "Headphones");
		assertEquals(1, testReq.getId());
		assertEquals(5, testReq.getQuantity());
		assertEquals(10.25, testReq.getPrice());
		assertEquals("nice headphones", testReq.getDescription());
		assertEquals("randomUrl", testReq.getImage());
		assertEquals("Headphones", testReq.getName());
		assertThat(testReq.toString().contains("description=nice headphones"));
	}
	@Test
	void ProductInfoNoArgs() {
		ProductInfo testReq = new ProductInfo();
		assertEquals(0, testReq.getId());
		assertEquals(0, testReq.getQuantity());
		assertEquals(0, testReq.getPrice());
		assertEquals(null, testReq.getDescription());
		assertEquals(null, testReq.getImage());
		assertEquals(null, testReq.getName());
		assertThat(testReq.toString().contains("description=null"));
	}
	@Test
	void PriceRangeRequestAllArgs() {
		PriceRangeRequest testReq = new PriceRangeRequest(1, 100);
		assertEquals(1, testReq.getMinPrice());
		assertEquals(100, testReq.getMaxPrice());
		assertThat(testReq.toString().contains("minPrice=1"));
	}
	@Test
	void PriceRangeRequestNoArgs() {
		PriceRangeRequest testReq = new PriceRangeRequest();
		assertEquals(0, testReq.getMinPrice());
		assertEquals(0, testReq.getMaxPrice());
		assertThat(testReq.toString().contains("minPrice=0"));
	}
	@Test
	void LoginRequestAllArgs() {
		LoginRequest testReq = new LoginRequest("email@email.com", "password");
		assertEquals("email@email.com", testReq.getEmail());
		assertEquals("password", testReq.getPassword());
		assertThat(testReq.toString().contains("password=password"));
	}
	@Test
	void LoginRequestNoArgs() {
		LoginRequest testReq = new LoginRequest();
		assertEquals(null, testReq.getEmail());
		assertEquals(null, testReq.getPassword());
		assertThat(testReq.toString().contains("password=null"));
	}
	@Test
	void CreateUpdateRequestAllArgs() {
		CreateUpdateRequest testReq = new CreateUpdateRequest(1, 10, 12.0, "More Headphones", "Url", "Headphones");
		assertEquals(1, testReq.getId());
		assertEquals(10, testReq.getQuantity());
		assertEquals(12.0, testReq.getPrice());
		assertEquals("More Headphones", testReq.getDescription());
		assertEquals("Url", testReq.getImage());
		assertEquals("Headphones", testReq.getName());
		assertThat(testReq.toString().contains("image=Url"));
	}
	@Test
	void CreateUpdateRequestNoArgs() {
		CreateUpdateRequest testReq = new CreateUpdateRequest();
		assertEquals(0, testReq.getId());
		assertEquals(0, testReq.getQuantity());
		assertEquals(0, testReq.getPrice());
		assertEquals(null, testReq.getDescription());
		assertEquals(null, testReq.getImage());
		assertEquals(null, testReq.getName());
		assertThat(testReq.toString().contains("description=null"));
	}
	@Test
	void AddressRequestAllArgs() {
		AddressRequest testReq = new AddressRequest(1, "123 mayburry", "43", "victoria", "77142", "Texas");
		assertEquals(1, testReq.getId());
		assertEquals("123 mayburry", testReq.getStreet());
		assertEquals("43", testReq.getSecondary());
		assertEquals("victoria", testReq.getCity());
		assertEquals("77142", testReq.getZip());
		assertEquals("Texas", testReq.getState());
		assertThat(testReq.toString().contains("state=Texas"));
	}
	@Test
	void AddressRequestNoArgs() {
		AddressRequest testReq = new AddressRequest();
		assertEquals(null, testReq.getId());
		assertEquals(null, testReq.getStreet());
		assertEquals(null, testReq.getSecondary());
		assertEquals(null, testReq.getCity());
		assertEquals(null, testReq.getZip());
		assertEquals(null, testReq.getState());
		assertThat(testReq.toString().contains("state=null"));
	}
}
