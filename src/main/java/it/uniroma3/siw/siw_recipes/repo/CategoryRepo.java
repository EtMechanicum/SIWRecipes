package it.uniroma3.siw.siw_recipes.repo;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.siw_recipes.model.Category;

public interface CategoryRepo extends CrudRepository<Category, Long>{
	
	public Category findByName(String name);
}
