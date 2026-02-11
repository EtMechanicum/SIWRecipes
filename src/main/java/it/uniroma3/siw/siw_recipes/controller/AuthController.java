package it.uniroma3.siw.siw_recipes.controller;

import static it.uniroma3.siw.siw_recipes.model.Credentials.DEFAULT_ROLE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import it.uniroma3.siw.siw_recipes.model.Credentials;
import it.uniroma3.siw.siw_recipes.model.User;
import it.uniroma3.siw.siw_recipes.service.CredentialsService;
import it.uniroma3.siw.siw_recipes.service.UserService;


@Controller
public class AuthController {
	
	@Autowired
	private CredentialsService cs;
	@Autowired
	private UserService us;
	@Autowired
    private PasswordEncoder passwordEncoder; 
	
	@GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("newCredentials", new Credentials());
		return "registrationForm";
	}
	
	@GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "loginForm";
	}
	
    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = cs.getCredentialsByUsername(userDetails.getUsername());
    	if (credentials.getRole().equals("ADMIN")) {
            return "/admin/adminHome";
        }
        return "redirect:/";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("newUser") User user,
                               BindingResult userBindingResult,
                               @Valid @ModelAttribute("newCredentials") Credentials credentials,
                               BindingResult credentialsBindingResult,
                               Model model) {

        if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            us.saveUser(user);

            // 🔥 encode raw password before saving
            credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));

            credentials.setUser(user);
            credentials.setRole(DEFAULT_ROLE);
            cs.saveCredentials(credentials);

            model.addAttribute("newUser", user);
            return "registrationSuccessful";
        }
        return "registrationForm";
    }
    
    @PostMapping("/admin/users/{id}/ban")
    public String banUser(@PathVariable Long id) {//prende l'id dell'utente
    	Credentials c = cs.getCredentialsById(id);
    	c.setEnabled(false);
    	cs.saveCredentials(c);
    	return "redirect:/admin/users"; //admin/users -> lista di tutti gli utenti con lo status
    }
    
    @GetMapping("/admin/users")
    public String userList(Model model) {
    	model.addAttribute("users", cs.getAllCredentials());
    	return "/admin/allUsers";
    }
    
    @GetMapping("/users/{username}")
    public String userProfile(@PathVariable String username, Model model) {
    	User user = cs.getCredentialsByUsername(username).getUser();
    	model.addAttribute("user", user); //Posso accedere a reviews e a recipes dal form
    	model.addAttribute("username", username);
    	return "singleUser";
    }
    
}
