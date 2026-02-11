package it.uniroma3.siw.siw_recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siw_recipes.model.Category;
import it.uniroma3.siw.siw_recipes.repo.CategoryRepo;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepo cr;
	
	public Iterable<Category> getAllCategories() {
		return cr.findAll();
	}
	
	public void saveCategory(Category category) {
		cr.save(category);
	}
	
	public Category getCategoryById(Long id) {
		return cr.findById(id).get();
	}
}
