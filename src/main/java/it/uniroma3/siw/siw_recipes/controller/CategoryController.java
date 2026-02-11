package it.uniroma3.siw.siw_recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siw_recipes.model.Category;
import it.uniroma3.siw.siw_recipes.service.CategoryService;
import jakarta.validation.Valid;

@Controller
public class CategoryController {
	
	@Autowired
	private CategoryService cs;
	
	@GetMapping("/admin/categories")
	public String categorySection(Model model) {
		model.addAttribute("categories", cs.getAllCategories());
		model.addAttribute("newCategory", new Category());
		return "categorySection";
	}
	
	@PostMapping("/admin/categories/save")
	public String saveCategory(@Valid @ModelAttribute Category newCategory, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "categorySection";
		}
		else {
			cs.saveCategory(newCategory);
			return "redirect:/admin/categories";
		}
	}
}
