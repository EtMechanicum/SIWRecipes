package it.uniroma3.siw.siw_recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siw_recipes.model.ingredients.Ingredient;
import it.uniroma3.siw.siw_recipes.service.IngredientService;

@Controller
public class IngredientController {
	
	@Autowired
	private IngredientService is;
	
	@GetMapping("/ingredients/allIngredients")
	public String allIngredients(Model model) {
		model.addAttribute("list", is.getAllIngredients());
		return "allIngredients";
	}
	
	@GetMapping("/ingredients/newIngredient")
	public String formNewIngredient(Model model) {
		model.addAttribute("newIngredient", new Ingredient());
		return "formNewIngredient";
	}
	
	
	/*Questo metodo va ultimato. Se l'operazione va a buon fine, fa il redirect
	 * verso una pagina custom. Credo che per farlo mi serve una @BindingResult
	 * Ma ancora non so come si usa. Comunque l'idea sarebbe questa*/
	@PostMapping("/ingredients/newIngredient")
	public String newIngredient(@ModelAttribute Ingredient newIngredient) {
		is.saveIngredient(newIngredient);
		return "redirect:/recipes/new";
	}
	
	@GetMapping("/ingredients/ingredientSuccess")
	public String ingredientSuccess() {
		return "ingredientSuccess";
	}
}
