/*
 * package it.uniroma3.siw.siw_recipes.dto;
 * 
 * import it.uniroma3.siw.siw_recipes.model.Recipe; import
 * it.uniroma3.siw.siw_recipes.model.ingredients.IngredientInRecipe;
 * 
 * public class RecipeMapper {
 * 
 * public static RecipeDTO toDTO(Recipe recipe) { RecipeDTO dto = new
 * RecipeDTO(); dto.setId(recipe.getId()); dto.setTitle(recipe.getTitle());
 * dto.setDescription(recipe.getDescription());
 * dto.setPreparationTime(recipe.getPreparationTime());
 * dto.setDifficulty(recipe.getDifficulty()); if (recipe.getCategory() != null)
 * { dto.setCategoryId(recipe.getCategory().getId()); }
 * 
 * for (IngredientInRecipe iir : recipe.getIngredients()) { IngredientDTO ingDTO
 * = new IngredientDTO(); ingDTO.setIngredientId(iir.getId());
 * ingDTO.setQuantity(iir.getQuantity()); ingDTO.setUnit(iir.getUnit());
 * dto.getIngredients().add(ingDTO); }
 * 
 * return dto; } }
 */