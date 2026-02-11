package it.uniroma3.siw.siw_recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siw_recipes.model.Credentials;
import it.uniroma3.siw.siw_recipes.repo.CredentialsRepo;


@Service
public class CredentialsService implements UserDetailsService {
	
	@Autowired
	private CredentialsRepo cr;
	
	public Credentials getCredentialsById(Long id) {
		return cr.findById(id).get();
	}
	
	public Credentials getCredentialsByUsername(String username) {
		return cr.findByUsername(username).get();
	}
	
	public Credentials saveCredentials(Credentials credentials) {
		return cr.save(credentials);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Credentials c = cr.findByUsername(username).get();
		
		return org.springframework.security.core.userdetails.User
				.withUsername(c.getUsername())
				.password(c.getPassword())
				.authorities(c.getRole())
				.disabled(!c.isEnabled())
				.build();
	}
	
	public Credentials getCredentialsByUserId(Long id) {
		return cr.findByUserId(id);
	}

	public Iterable<Credentials> getAllCredentials(){
		return cr.findAll();
	}
}
