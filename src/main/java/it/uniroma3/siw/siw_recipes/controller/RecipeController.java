package it.uniroma3.siw.siw_recipes.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.siw_recipes.model.Category;
//import it.uniroma3.siw.siw_recipes.model.Category;
//import it.uniroma3.siw.siw_recipes.dto.IngredientDTO;
//import it.uniroma3.siw.siw_recipes.dto.RecipeDTO;
//import it.uniroma3.siw.siw_recipes.dto.RecipeMapper;
import it.uniroma3.siw.siw_recipes.model.Difficulty;

import it.uniroma3.siw.siw_recipes.model.Recipe;
import it.uniroma3.siw.siw_recipes.model.Review;
import it.uniroma3.siw.siw_recipes.model.User;
import it.uniroma3.siw.siw_recipes.model.ingredients.IngredientInRecipe;
import it.uniroma3.siw.siw_recipes.model.ingredients.Unit;
import it.uniroma3.siw.siw_recipes.service.*;
import jakarta.validation.Valid;

@Controller
public class RecipeController {



	@Autowired
	private RecipeService rs;
	@Autowired
	private IngredientService is;
	@Autowired
	private CategoryService cs;
	@Autowired
	private CredentialsService credServ;
	@Autowired
	private ReviewService revServ;	

	
	@Autowired 
	private IIRService iirs;
	 
	// Questo eventualmente lo metterò in un altro controller.
	// Per ora lo lascio qui
	@GetMapping("/")
	public String homepage(Model model) {
		Optional<Recipe> rotd = rs.getRecipeOfTheDay(); 
		model.addAttribute("recipeOfTheDay", rotd.orElse(null));
		model.addAttribute("newest", rs.getNewestRecipes());
		model.addAttribute("popular", rs.getMostPopular());
		return "homepage";
	}

	@GetMapping("/recipes/new")
	public String newRecipe(Model model) {
		Recipe newRecipe = new Recipe();
		model.addAttribute("newRecipe", newRecipe);
		// model.addAttribute("allIngredients", is.getAllIngredients());
		model.addAttribute("units", Unit.values());
		model.addAttribute("difficulties", Difficulty.values());
		model.addAttribute("categories", cs.getAllCategories());
		return "formNewRecipe";
	}

		
		@PostMapping(value="/recipes/new/save", params="action=addIngredient")
		public String addIngredient(
		    @ModelAttribute("newRecipe") Recipe newRecipe,
		    Model model
		) {
			newRecipe.getIngredients().add(new IngredientInRecipe());
			model.addAttribute("newRecipe", newRecipe);
			model.addAttribute("units", Unit.values());
			model.addAttribute("difficulties", Difficulty.values());
			model.addAttribute("categories", cs.getAllCategories());
			return "formNewRecipe";
		}
		
		@PostMapping(value="/recipes/new/save", params="action=addStep")
		public String addStep(
		    @ModelAttribute("newRecipe") Recipe newRecipe,
		    Model model
		) {
			newRecipe.getSteps().add("");
			model.addAttribute("newRecipe", newRecipe);
			model.addAttribute("units", Unit.values());
			model.addAttribute("difficulties", Difficulty.values());
			model.addAttribute("categories", cs.getAllCategories());
			return "formNewRecipe";
		}
		
		@PostMapping(value="/recipes/new/save", params="action=addCategory")
		public String addCategory(
		    @ModelAttribute("newRecipe") Recipe newRecipe,
		    Model model
		) {
			newRecipe.getCategories().add(new Category());
			model.addAttribute("newRecipe", newRecipe);
			model.addAttribute("units", Unit.values());
			model.addAttribute("difficulties", Difficulty.values());
			model.addAttribute("categories", cs.getAllCategories());
			return "formNewRecipe";
		}


		
		@PostMapping(value="/recipes/new/save", params="action=save")
		public String saveRecipe(
		    @Valid @ModelAttribute("newRecipe") Recipe newRecipe,
		    BindingResult bindingResult,
		    Model model
		) {
			 // SOLO QUI VALIDAZIONE
		    if(bindingResult.hasErrors()) {
				model.addAttribute("units", Unit.values()); 
				model.addAttribute("difficulties", Difficulty.values()); 
				model.addAttribute("categories", cs.getAllCategories()); 
				model.addAttribute("newRecipe", newRecipe); 
				System.out.println(newRecipe.getId() + "Is recipe id null?" + 
				"Lista ingredienti: " + newRecipe.getIngredients() + 
				"Lista steps: " + newRecipe.getSteps() + 
				"quantity del primo ingrediente: " + 
				newRecipe.getIngredients().get(0).getQuantity() + 
				"categoria id: " + newRecipe.getCategories().get(0)); 
				return "formNewRecipe"; 
			} 
			else {
				newRecipe.setCreatedAt(LocalDateTime.now()); 
				UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().
						getAuthentication().getPrincipal(); 
				User user = credServ.getCredentialsByUsername(userDetails.getUsername()).getUser(); 
				newRecipe.setAuthor(user);  
				
				/*Gestione categorie*/
				List<Category> managedCategories = new ArrayList<>();

				for (Category c : newRecipe.getCategories()) {
				    Category managed = cs.getCategoryById(c.getId());
				    managedCategories.add(managed);
				}
				newRecipe.setCategories(managedCategories);
				/*Gestione ingredienti*/
				
				List<String> invalidIngredients =
			            iirs.validateIngredients(newRecipe.getIngredients());

			    if (!invalidIngredients.isEmpty()) {

			        bindingResult.rejectValue(
			            "ingredients",
			            "invalid.ingredients",
			            "Ingredienti non validi: " + invalidIngredients
			        );
			        
			        model.addAttribute("units", Unit.values()); 
					model.addAttribute("difficulties", Difficulty.values()); 
					model.addAttribute("categories", cs.getAllCategories()); 
					model.addAttribute("newRecipe", newRecipe); 
					return "formNewRecipe"; 
			    }
				
				for(IngredientInRecipe item : newRecipe.getIngredients()) {
					item.getIngredient().toLowerCase(); //così ci assicuriamo che sono scritti tutti uguali
					item.setRecipe(newRecipe);
				 }
				  
				//iirs.saveIIR(item); 
				rs.saveRecipe(newRecipe);
			}
			return "redirect:/recipes/" + newRecipe.getId(); 
		}




	@GetMapping("/recipes/{id}")
	public String toSingleRecipe(@PathVariable Long id, Model model) {
		Recipe recipe = rs.getRecipeById(id);
		model.addAttribute("recipe", recipe);
		model.addAttribute("newReview", new Review());
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = credServ.getCredentialsByUsername(userDetails.getUsername()).getUser();
		model.addAttribute("idLoggedUser", user.getId()); // this one is for the recipe
		
		  System.out.println("Id utente loggato: " + user.getId());
		  System.out.println("Id autore della ricetta: " + recipe.getAuthor().getId());
		 
		return "singleRecipe";
	}

	@GetMapping("/recipes/allRecipes")
	public String allRecipes(Model model) {
		model.addAttribute("recipes", rs.getAllRecipes());
		return "allRecipes";
	}

	@PostMapping("/recipes/{id}/newReview")
	public String saveReview(@PathVariable Long id, @Valid @ModelAttribute("newReview") Review review, 
				BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			Recipe recipe = rs.getRecipeById(id);
	        model.addAttribute("recipe", recipe);
			return "singleRecipe";
		}
		else {
			Recipe recipe = rs.getRecipeById(id);

			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = credServ.getCredentialsByUsername(userDetails.getUsername()).getUser();

			review.setUser(user); // prima del save
			review.setRecipe(recipe); // prima del save

			revServ.saveReview(review); // salva nuovo oggetto

			recipe.addRating(review.getRating());
			rs.saveRecipe(recipe); // aggiorna rating
			return "redirect:/recipes/" + id;
		}
	}

	@GetMapping("/recipes/{id}/edit")
	public String editRecipe(@PathVariable Long id, Model model) {
		Recipe recipe = rs.getRecipeById(id);

		model.addAttribute("newRecipe", recipe);
		model.addAttribute("allIngredients", is.getAllIngredients());
		model.addAttribute("units", Unit.values());
		model.addAttribute("difficulties", Difficulty.values());
		model.addAttribute("categories", cs.getAllCategories());
		return "formNewRecipe"; // stessa view per nuova ricetta
	}

	/* Edit Review con pagina separata per l'edit */
	@GetMapping("/reviews/{id}/edit") // Questo poi ha come Post lo stesso link di newReview
	public String editReview(@PathVariable Long id, Model model) {
		Review review = revServ.getReviewById(id);
		model.addAttribute("newReview", review);
		return "editReviewForm";
	}
	
	/*Per avere le ricette in base alla categoria*/
	@GetMapping("/recipes/category/{category_name}")
	public String getAllByCategory(@PathVariable String category_name, Model model) {
		Category category = cs.getCategoryByName(category_name);
		model.addAttribute("recipes", rs.getRecipesByCategory(category));
		return "allRecipes";
	}
	
	/*Ricette in base all'ingrediente*/
	@GetMapping("/recipes/ingredients/{ingredient_name}")
	public String getAllByIngredient(@PathVariable String ingredient_name, Model model) {
		ArrayList<IngredientInRecipe> iir = iirs.getIngredientByName(ingredient_name); //tutti gli ingredienti con quel nome
		ArrayList<Recipe> recipes = new ArrayList<>();
		for(IngredientInRecipe ingredient : iir) {
			recipes.add(ingredient.getRecipe());
		}
		model.addAttribute("recipes", recipes);
		return "allRecipes";
	}
	
	/*Ricette veloci*/
	@GetMapping("/recipes/fastRecipes")
	public String fastRecipes(Model model) {
		model.addAttribute("recipes", rs.getFastestToPrepare(25));
		return "allRecipes";
	}
	
	@PostMapping("/recipes/{id}/delete")
	public String deleteRecipe(@PathVariable Long id) {
		rs.deleteRecipe(rs.getRecipeById(id));
		return "redirect:/recipes/allRecipes"; //Per ora lo mando alla lista di tutte le ricette.Meglio redirect a "successful" 
	}
	
	@PostMapping("/admin/recipes/{id}/recipeOfTheDay")
	public String promoteToRecipeOfTheDay(@PathVariable Long id) {
	    rs.promoteToRecipeOfTheDay(id);
	    return "redirect:/recipes/" + id;
	}

	@GetMapping("/search")
	public String search(@RequestParam String query, Model model) {
		model.addAttribute("recipes", rs.searchByRecipeName(query));
		return "allRecipes";
	}
	
}
