package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.dtos.ReviewRequest;
import com.revature.models.Product;
import com.revature.models.Review;
import com.revature.models.User;
import com.revature.repositories.ReviewRepository;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

	@Mock
	ProductService pServ;
	@Mock
	UserService uServ;
	@Mock
	ReviewRepository mockReviewRepo;

	@InjectMocks
	ReviewService rServ;

	Product dummyProduct;
	Review dummyReview;
	User dummyUser;

	@BeforeEach
	void setUp() throws Exception {
		this.dummyProduct = new Product(1, 1, 12.34, "T-shirt", "image.jpg", "T-shirt", null, null);
		this.dummyUser = new User(1, "dummy@revature.com", "asdf", "Dummy", "User", "Customer", null, null, null);
		this.dummyReview = new Review(1, 5, "Review title", "Review body sample text", null, null, this.dummyProduct,
				this.dummyUser);

		// Set<Review> reviews = new LinkedHashSet<>();
		// reviews.add(this.dummyReview);
		// this.dummyProduct.setReviews(reviews);
	}

	@AfterEach
	void tearDown() throws Exception {
		// GC the dummy objects
		this.dummyReview = null;
		this.dummyUser = null;
		this.dummyProduct = null;
	}

	@Test
	void testAdd_Success() {
		ReviewRequest request = new ReviewRequest(this.dummyProduct.getId(), 5, "Review title",
				"Review body sample text");
		Review newReview = new Review(request.getStars(), request.getTitle(), request.getReview(), this.dummyUser,
				this.dummyProduct);
		Review expected = new Review(2, newReview.getStars(), newReview.getTitle(), newReview.getReview(),
				newReview.getPosted(), newReview.getUpdated(), this.dummyProduct, this.dummyUser);

		given(this.pServ.findById(request.getProductId())).willReturn(Optional.of(this.dummyProduct));
		given(this.mockReviewRepo.save(newReview)).willReturn(expected);

		Review actual = this.rServ.add(request, this.dummyUser);

		assertEquals(expected, actual);
		verify(this.mockReviewRepo, times(1)).save(newReview);
	}

	@Test
	@Disabled("Not yet implemented")
	void testAdd_Failure_ProductNotFound() {
		fail("Not yet implemented");
	}

	@Test
	void testFindAll() {
		User user2 = new User(0, "user2@revature.com", "qwerty123", "Another", "User", "Customer");
		List<Review> expected = new LinkedList<>();
		expected.add(this.dummyReview);
		expected.add(new Review(2, 4, "Another review", "Some review body text", null, null, this.dummyProduct,
				user2));

		given(this.mockReviewRepo.findAll()).willReturn(expected);

		List<Review> actual = this.rServ.findAll();

		assertEquals(expected, actual);
		assertTrue(expected.containsAll(actual));
		assertEquals(expected.size(), actual.size());
		verify(this.mockReviewRepo, times(1)).findAll();
	}

	@Test
	void testFindByProductId_Success() {
		int id = this.dummyProduct.getId();
		List<Review> expected = new LinkedList<>();
		expected.add(this.dummyReview);

		given(this.pServ.findById(id)).willReturn(Optional.of(this.dummyProduct));
		given(this.mockReviewRepo.findByProduct(this.dummyProduct)).willReturn(expected);

		List<Review> actual = this.rServ.findByProductId(id);

		assertEquals(expected, actual);
		assertTrue(actual.containsAll(expected));
		verify(this.mockReviewRepo, times(1)).findByProduct(this.dummyProduct);
	}

	@Test
	@Disabled("Not yet implemented")
	void testFindByProductId_Failure_ProductIdNotFound() {
		fail("Not yet implemented");
	}

	@Test
	void testFindByUserId_Success() {
		int id = this.dummyUser.getId();
		List<Review> expected = new LinkedList<>();
		expected.add(this.dummyReview);

		given(this.uServ.findById(id)).willReturn(Optional.of(this.dummyUser));
		given(this.mockReviewRepo.findByUser(this.dummyUser)).willReturn(expected);

		List<Review> actual = this.rServ.findByUserId(id);

		assertEquals(expected, actual);
		assertTrue(actual.containsAll(expected));
		verify(this.mockReviewRepo, times(1)).findByUser(this.dummyUser);
	}

	@Test
	@Disabled("Not yet implemented")
	void testFindByUserId_Failure_UserIdNotFound() {
		fail("Not yet implemented");
	}

	@Test
	void testFindById() {
		int id = this.dummyReview.getId();
		given(this.mockReviewRepo.findById(id)).willReturn(Optional.of(this.dummyReview));

		Review expected = this.dummyReview;
		Review actual = this.rServ.findById(id).get();

		assertEquals(expected, actual);
		verify(this.mockReviewRepo, times(1)).findById(id);
	}

	@Test
	void testSave() {
		given(this.mockReviewRepo.save(this.dummyReview)).willReturn(this.dummyReview);

		Review expected = this.dummyReview;
		Review actual = this.rServ.save(this.dummyReview);

		assertEquals(expected, actual);
		verify(this.mockReviewRepo, times(1)).save(this.dummyReview);
	}

	@Test
	@Disabled("Not yet implemented")
	void testUpdate_Success() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testUpdate_Failure_UnauthorizedUserId() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testDelete_Success() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testDelete_Failure_UnauthorizedUserId() {
		fail("Not yet implemented");
	}

}
