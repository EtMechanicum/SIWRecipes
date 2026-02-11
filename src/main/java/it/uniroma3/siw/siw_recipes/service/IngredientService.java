package it.uniroma3.siw.siw_recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siw_recipes.model.ingredients.Ingredient;
import it.uniroma3.siw.siw_recipes.repo.IngredientRepo;

@Service
public class IngredientService {
	
	@Autowired
	private IngredientRepo ir;
	
	public Iterable<Ingredient> getAllIngredients() {
		return ir.findAll();
	}
	
	public void saveIngredient(Ingredient ingredient) {
		ir.save(ingredient);
	}
	
	public Ingredient getIngredientById(Long id) {
		return ir.findById(id).get();
	}
}
