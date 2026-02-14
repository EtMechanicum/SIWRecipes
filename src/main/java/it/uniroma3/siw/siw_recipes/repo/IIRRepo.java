package it.uniroma3.siw.siw_recipes.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.siw_recipes.model.ingredients.IngredientInRecipe;

public interface IIRRepo extends CrudRepository<IngredientInRecipe, Long>{
	
	public ArrayList<IngredientInRecipe> findAllByIngredient(String ingredient);
}
