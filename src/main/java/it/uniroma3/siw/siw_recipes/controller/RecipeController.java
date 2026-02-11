package it.uniroma3.siw.siw_recipes.controller;

import java.time.LocalDateTime;
import java.util.Optional;

//import java.time.LocalDateTime;

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

		model.addAttribute("newRecipe", new Recipe());
		// model.addAttribute("allIngredients", is.getAllIngredients());
		model.addAttribute("units", Unit.values());
		model.addAttribute("difficulties", Difficulty.values());
		model.addAttribute("categories", cs.getAllCategories());

		return "formNewRecipe";
	}

	/*
	 * @PostMapping("/recipes/new") public String handleRecipe(
	 * 
	 * @ModelAttribute RecipeDTO newRecipe,
	 * 
	 * @RequestParam(required = false) String addIngredient, Model model ) {
	 * 
	 * // 👉 CASO 1: aggiungo ingrediente if (addIngredient != null) {
	 * newRecipe.getIngredients().add(new IngredientDTO());
	 * 
	 * model.addAttribute("newRecipe", newRecipe);
	 * model.addAttribute("allIngredients", is.getAllIngredients());
	 * model.addAttribute("units", Unit.values());
	 * model.addAttribute("difficulties", Difficulty.values());
	 * model.addAttribute("categories", cs.getAllCategories());
	 * 
	 * return "formNewRecipe"; }
	 * 
	 * // 👉 CASO 2: salvo davvero Recipe recipe = new Recipe();
	 * recipe.setTitle(newRecipe.getTitle());
	 * recipe.setDescription(newRecipe.getDescription());
	 * recipe.setPreparationTime(newRecipe.getPreparationTime());
	 * recipe.setDifficulty(newRecipe.getDifficulty());
	 * recipe.setCategory(cs.getCategoryById(newRecipe.getCategoryId()));
	 * recipe.setCreatedAt(LocalDateTime.now()); UserDetails userDetails =
	 * (UserDetails)
	 * SecurityContextHolder.getContext().getAuthentication().getPrincipal(); User
	 * user =
	 * credServ.getCredentialsByUsername(userDetails.getUsername()).getUser();
	 * recipe.setAuthor(user);
	 * 
	 * for (IngredientDTO ingDto : newRecipe.getIngredients()) { Ingredient
	 * ingredient = is.getIngredientById(ingDto.getIngredientId());
	 * 
	 * IngredientInRecipe iir = new IngredientInRecipe(); iir.setRecipe(recipe);
	 * iir.setIngredient(ingredient); iir.setQuantity(ingDto.getQuantity());
	 * iir.setUnit(ingDto.getUnit());
	 * 
	 * recipe.getIngredients().add(iir); }
	 * 
	 * rs.saveRecipe(recipe); return "redirect:/recipes/allRecipes"; }
	 */
	/*
	 * @PostMapping("/recipes/new") public String
	 * saveRecipe(@ModelAttribute("newRecipe") Recipe recipe,
	 * 
	 * @RequestParam(required = false) String addIngredient, Model model) {
	 * 
	 * 
	 * if (recipe.getId() != null) { // MODIFICA recipe =
	 * rs.getRecipeById(dto.getId()); recipe.getIngredients().clear(); // importante
	 * } else { // CREAZIONE recipe = new Recipe();
	 * recipe.setCreatedAt(LocalDateTime.now()); } if (addIngredient != null) {
	 * dto.getIngredients().add(new IngredientDTO());
	 * 
	 * model.addAttribute("newRecipe", dto); model.addAttribute("allIngredients",
	 * is.getAllIngredients()); model.addAttribute("units", Unit.values());
	 * model.addAttribute("difficulties", Difficulty.values());
	 * model.addAttribute("categories", cs.getAllCategories());
	 * 
	 * return "formNewRecipe"; } recipe.setTitle(dto.getTitle());
	 * recipe.setDescription(dto.getDescription());
	 * recipe.setPreparationTime(dto.getPreparationTime());
	 * recipe.setDifficulty(dto.getDifficulty());
	 * recipe.setCategory(cs.getCategoryById(dto.getCategoryId()));
	 * recipe.setImageFileName(dto.getImageFileName());
	 * recipe.setCreatedAt(LocalDateTime.now()); UserDetails userDetails =
	 * (UserDetails)
	 * SecurityContextHolder.getContext().getAuthentication().getPrincipal(); User
	 * user =
	 * credServ.getCredentialsByUsername(userDetails.getUsername()).getUser();
	 * recipe.setAuthor(user);
	 * 
	 * 
	 * for (IngredientDTO ingDto : dto.getIngredients()) {
	 * System.out.println(ingDto.toString()); IngredientInRecipe ingredient = new
	 * IngredientInRecipe(); ingredient.setId(ingDto.getIngredientId());
	 * ingredient.setRecipe(recipe); ingredient.setIngredient(ingDto.getName());
	 * ingredient.setQuantity(ingDto.getQuantity());
	 * ingredient.setUnit(ingDto.getUnit());
	 * 
	 * IngredientInRecipe iir = new IngredientInRecipe(); iir.setRecipe(recipe);
	 * iir.setIngredient(ingredient); iir.setQuantity(ingDto.getQuantity());
	 * iir.setUnit(ingDto.getUnit());
	 * 
	 * recipe.getIngredients().add(iir);
	 * 
	 * }
	 * 
	 * rs.saveRecipe(recipe); return "redirect:/recipes/" + recipe.getId();
	 * }
	 */
	
	@PostMapping("/recipes/new") 
	public String handleRecipe(@Valid @ModelAttribute("newRecipe") Recipe newRecipe, BindingResult bindingResult ,@RequestParam (required = false) String addIngredient,
				@RequestParam(required=false) String addStep, @RequestParam(required=false) String addCategory ,Model model) {
		if(addIngredient != null) {
			newRecipe.getIngredients().add(new IngredientInRecipe());
			model.addAttribute("units", Unit.values());
			model.addAttribute("difficulties", Difficulty.values());
			model.addAttribute("categories", cs.getAllCategories());
			return "formNewRecipe";
		}
		if(addStep != null) {
			newRecipe.getSteps().add(new String());
			model.addAttribute("units", Unit.values());
			model.addAttribute("difficulties", Difficulty.values());
			model.addAttribute("categories", cs.getAllCategories());
			return "formNewRecipe";
		}
		if(addCategory != null) {
			newRecipe.getCategories().add((long) 0);
			model.addAttribute("units", Unit.values());
			model.addAttribute("difficulties", Difficulty.values());
			model.addAttribute("categories", cs.getAllCategories());
			return "formNewRecipe";
		}
		if(bindingResult.hasErrors()) {
			return "formNewRecipe";
		}
		else {
			newRecipe.setCreatedAt(LocalDateTime.now()); UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
			User user = credServ.getCredentialsByUsername(userDetails.getUsername()).getUser();
			newRecipe.setAuthor(user);
			rs.saveRecipe(newRecipe);
			for(IngredientInRecipe item : newRecipe.getIngredients()) {
				System.out.println(item.toString());
				item.setRecipe(newRecipe);
				iirs.saveIIR(item);
			}
			return "redirect:/recipes/" + newRecipe.getId();
		}
	}

	@GetMapping("/recipes/{id}")
	public String toSingleRecipe(@PathVariable Long id, Model model) {
		Recipe recipe = rs.getRecipeById(id);
		model.addAttribute("recipe", recipe);
		model.addAttribute("newReview", new Review());
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = credServ.getCredentialsByUsername(userDetails.getUsername()).getUser();
		model.addAttribute("idLoggedUser", user.getId()); // this one is for the recipe
		/*
		 * System.out.println("Id utente loggato: " + user.getId());
		 * System.out.println("Id autore della ricetta: " + recipe.getAuthor().getId());
		 */
		return "singleRecipe";
	}

	@GetMapping("/recipes/allRecipes")
	public String allRecipes(Model model) {
		model.addAttribute("recipes", rs.getAllRecipes());
		return "allRecipes";
	}

	@PostMapping("/recipes/{id}/newReview")
	public String saveReview(@PathVariable Long id, @Valid @ModelAttribute("newReview") Review review, 
				BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "redirect:/recipes/{id}";
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
	@GetMapping("/recipes/category/{category_id}")
	public String getAllByCategory(@PathVariable("category_id") Long id, Model model) {
		model.addAttribute("recipes", rs.getRecipesByCategory(id));
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


}
