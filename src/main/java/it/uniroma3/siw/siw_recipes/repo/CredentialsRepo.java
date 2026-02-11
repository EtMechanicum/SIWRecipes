package it.uniroma3.siw.siw_recipes.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.siw_recipes.model.Credentials;

public interface CredentialsRepo extends CrudRepository<Credentials, Long>{
	public Optional<Credentials> findByUsername(String username);
	
	public Credentials findByUserId(Long id);

}
