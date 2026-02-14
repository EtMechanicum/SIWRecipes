package it.uniroma3.siw.siw_recipes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siw_recipes.model.ingredients.IngredientInRecipe;
import it.uniroma3.siw.siw_recipes.repo.IIRRepo;
import it.uniroma3.siw.siw_recipes.repo.IngredientRepo;
import jakarta.transaction.Transactional;

@Service
public class IIRService {
	
	@Autowired
	private IIRRepo iirRepo;
	@Autowired
	private IngredientRepo ir;
	
	public void saveIIR(IngredientInRecipe iir) {
		iirRepo.save(iir);
	}
	
	public IngredientInRecipe GetIIRById(Long id) {
		return iirRepo.findById(id).get();
	}
	
	public ArrayList<IngredientInRecipe> getIngredientByName(String name) {
		return iirRepo.findAllByIngredient(name);
	}
	
	@Transactional
	public List<String> validateIngredients(List<IngredientInRecipe> ingredients) {

	    List<String> errors = new ArrayList<>();

	    for (IngredientInRecipe i : ingredients) {

	        if (!ir.existsByNameIgnoreCase(i.getIngredient())) {
	            errors.add(i.getIngredient());
	        }
	    }
	    return errors;
	}
}
