package it.uniroma3.siw.siw_recipes.dto;

import java.util.ArrayList;
import java.util.List;

import it.uniroma3.siw.siw_recipes.model.Difficulty;
import it.uniroma3.siw.siw_recipes.model.Review;
import it.uniroma3.siw.siw_recipes.model.ingredients.IngredientInRecipe;

public class RecipeDTO {
	
	private Long id;
	private String title;
	private String description;
	private int preparationTime;
	private Difficulty difficulty;
	private Long categoryId;
	private List<IngredientInRecipe> ingredients = new ArrayList<>();
	private List<Review> reviews;
	private float stars;
	private String imageFileName;
	
	public RecipeDTO() {}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public List<IngredientInRecipe> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<IngredientInRecipe> ingredients) {
		this.ingredients = ingredients;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public float getStars() {
		return stars;
	}

	public void setStars(float stars) {
		this.stars = stars;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
}
