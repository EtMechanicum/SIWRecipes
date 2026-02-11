package it.uniroma3.siw.siw_recipes.repo;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.siw_recipes.model.User;

public interface UserRepo extends CrudRepository<User, Long>{

}
