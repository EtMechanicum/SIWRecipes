package it.uniroma3.siw.siw_recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siw_recipes.model.Review;
import it.uniroma3.siw.siw_recipes.repo.ReviewRepo;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepo rr;
	
	public void saveReview(Review review) {
		rr.save(review);
	}
	
	public Review getReviewById(Long id) {
		return rr.findById(id).get();
	}
	
	public void deleteReview(Review review) {
		rr.delete(review);
	}
}
