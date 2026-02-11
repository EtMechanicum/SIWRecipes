package it.uniroma3.siw.siw_recipes.model.ingredients;

import java.util.Objects;

import it.uniroma3.siw.siw_recipes.model.Recipe;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class IngredientInRecipe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String ingredient;
	private float quantity;
	@Enumerated(EnumType.STRING)
	private Unit unit;
	@ManyToOne
	private Recipe recipe;
	
	public IngredientInRecipe() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ingredient, quantity, unit);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IngredientInRecipe other = (IngredientInRecipe) obj;
		return Objects.equals(ingredient, other.ingredient)
				&& Float.floatToIntBits(quantity) == Float.floatToIntBits(other.quantity) && unit == other.unit;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public String toString() {
		return "IngredientInRecipe [id=" + id + ", ingredient=" + ingredient + ", quantity=" + quantity + ", unit="
				+ unit + ", recipe=" + recipe + "]";
	}
	
	
}
