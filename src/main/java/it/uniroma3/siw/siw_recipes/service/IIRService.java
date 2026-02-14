package it.uniroma3.siw.siw_recipes.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siw_recipes.model.ingredients.IngredientInRecipe;
import it.uniroma3.siw.siw_recipes.repo.IIRRepo;

@Service
public class IIRService {
	
	@Autowired
	private IIRRepo iirRepo;
	
	public void saveIIR(IngredientInRecipe iir) {
		iirRepo.save(iir);
	}
	
	public IngredientInRecipe GetIIRById(Long id) {
		return iirRepo.findById(id).get();
	}
	
	public ArrayList<IngredientInRecipe> getIngredientByName(String name) {
		return iirRepo.findAllByIngredient(name);
	}
}
