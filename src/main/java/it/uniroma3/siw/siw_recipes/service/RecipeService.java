package it.uniroma3.siw.siw_recipes.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siw_recipes.model.Category;
import it.uniroma3.siw.siw_recipes.model.Recipe;
import it.uniroma3.siw.siw_recipes.repo.RecipeRepo;
import jakarta.transaction.Transactional;

@Service
public class RecipeService {
	
	@Autowired
	private RecipeRepo rr;
	
	//Get all
	public Iterable<Recipe> getAllRecipes() {
		return rr.findAll();
	}
	//Get single by id
	public Recipe getRecipeById(Long id) {
		return rr.findById(id).get();
	}
	
	
	public Recipe saveRecipe(Recipe recipe) {
	    return rr.save(recipe);
	}

	
	
	public Iterable<Recipe> getRecipesByCategory(Category category) {
		return rr.findRecipeByCategories(category); 
	}
	
	public void deleteRecipe(Recipe recipe) {
		rr.delete(recipe);
	}
	
	public Optional<Recipe> getRecipeOfTheDay() {
		return rr.findByRecipeOfTheDayTrue();
	}
	
    @Transactional
    public void promoteToRecipeOfTheDay(Long id) {

        for (Recipe item : rr.findAll()) {
            if (item.isRecipeOfTheDay()) {
                item.setRecipeOfTheDay(false);
            }
        }

        Recipe recipe = rr.findById(id)
                .orElseThrow();

        recipe.setRecipeOfTheDay(true);
    }
    
    public Iterable<Recipe> getNewestRecipes() {
    	return rr.findTop3ByOrderByCreatedAtDesc();
    }
    
    public Iterable<Recipe> getMostPopular() {
    	return rr.findTop3ByOrderByStarsDesc();
    }
    
    public Iterable<Recipe> getFastestToPrepare(int minutes) {
    	return rr.findByPreparationTimeLessThanEqual(minutes);
    }
}
