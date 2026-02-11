package it.uniroma3.siw.siw_recipes.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import it.uniroma3.siw.siw_recipes.model.ingredients.IngredientInRecipe;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
//import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class Recipe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String title;
	@NotBlank
	@Size(min = 50)
	private String description;
	@NotEmpty
	private List<String> steps = new ArrayList<>();
	@OneToMany(
		    mappedBy = "recipe",
		    cascade = CascadeType.ALL,
		    orphanRemoval = true
		)
	@NotEmpty
	private List<IngredientInRecipe> ingredients = new ArrayList<>();
	@NotNull
	private int preparationTime;
    @Enumerated(EnumType.STRING)
    @NotNull
	private Difficulty difficulty;
    /*Questo tocca farlo diventare List<Categories> probabilmente*/
    @NotEmpty
    @Size(min = 1)
	private List<Long> categories = new ArrayList<>();
    @NotNull
	private LocalDateTime createdAt;
	
	@ManyToOne
	private User author;
	 
	/*This is for the smiles (rating)*/
	/*
	 * @Positive
	 * Stars are calculated internally based on reviews
	 * @Max(5)
	 */
	private float stars = 0;
	private int totalVotes = 0;
	private float totalStars = 0;
	@OneToMany(mappedBy = "recipe")
	private List<Review> reviews = new ArrayList<>();
	@NotBlank
	private String imageFileName;
	private boolean recipeOfTheDay = false;
	
	public Recipe() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public List<IngredientInRecipe> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<IngredientInRecipe> ingredients) {
		this.ingredients = ingredients;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	/*
	 * public User getAuthor() { return author; }
	 * 
	 * public void setAuthor(User author) { this.author = author; }
	 */

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof Recipe)) return false;
	    Recipe other = (Recipe) o;
	    return id != null && id.equals(other.id);
	}

	@Override
	public int hashCode() {
	    return getClass().hashCode();
	}
	
	// getter/setter per aggiornare media
    public void addRating(float reviewStars) {
        totalStars += reviewStars;
        totalVotes++;
        this.setStars(totalStars/totalVotes);
    }
    
	public float getAvgStars() {
		return getStars();
	}

	public void setAvgStars(int avgStars) {
		this.setStars(avgStars);
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}


	public List<String> getSteps() {
		return steps;
	}

	public void setSteps(List<String> steps) {
		this.steps = steps;
	}

	public float getStars() {
		return stars;
	}

	public void setStars(float stars) {
		this.stars = stars;
	}

	public List<Long> getCategories() {
		return categories;
	}

	public void setCategories(List<Long> categories) {
		this.categories = categories;
	}

	public boolean isRecipeOfTheDay() {
		return recipeOfTheDay;
	}

	public void setRecipeOfTheDay(boolean recipeOfTheDay) {
		this.recipeOfTheDay = recipeOfTheDay;
	}
}
/*
 * @PrePersist public void onCreate() { this.createdAt = LocalDateTime.now(); }
 * }
 */
