package it.uniroma3.siw.siw_recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siw_recipes.model.Review;
import it.uniroma3.siw.siw_recipes.service.ReviewService;

@Controller
public class ReviewController {
	@Autowired
	private ReviewService rs;
	
	@PostMapping("/reviews/{review_id}/deleteReview")
	public String deleteReview(@PathVariable Long review_id) {
		Review review = rs.getReviewById(review_id);
		Long recipeId = review.getRecipe().getId();
		rs.deleteReview(review);
		return "redirect:/recipes/" + recipeId;
	}
}
