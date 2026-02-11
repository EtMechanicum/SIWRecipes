package it.uniroma3.siw.siw_recipes.dto;

import it.uniroma3.siw.siw_recipes.model.ingredients.Unit;

public class IngredientDTO {
	
	private Long ingredientId;
	private String name;
	private float quantity;
	private Unit unit;
	
	public IngredientDTO() {}

	public Long getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Long id) {
		this.ingredientId = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "IngredientDTO [ingredientId=" + ingredientId + ", name=" + name + ", quantity=" + quantity + ", unit="
				+ unit + "]";
	}
	
	
}
