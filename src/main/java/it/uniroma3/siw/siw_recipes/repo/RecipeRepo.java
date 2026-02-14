package it.uniroma3.siw.siw_recipes.repo;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.siw_recipes.model.Category;
import it.uniroma3.siw.siw_recipes.model.Recipe;

public interface RecipeRepo extends CrudRepository<Recipe, Long>{
	
	public Iterable<Recipe> findRecipeByCategories(Category category);
	
	public Optional<Recipe> findByRecipeOfTheDayTrue();
	
	public Iterable<Recipe> findTop3ByOrderByCreatedAtDesc();
	
	public Iterable<Recipe> findTop3ByOrderByStarsDesc();
	
	

}
