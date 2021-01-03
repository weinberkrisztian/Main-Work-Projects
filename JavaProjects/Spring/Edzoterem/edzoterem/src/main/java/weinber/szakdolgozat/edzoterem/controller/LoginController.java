package weinber.szakdolgozat.edzoterem.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import weinber.szakdolgozat.edzoterem.entity.User;
import weinber.szakdolgozat.edzoterem.service.UserServiceIF;

@Controller
public class LoginController {
	
	@Autowired
	private UserServiceIF userService;

	/**
	 * Handles the login page.
	 * Called on registration-conformation, registrationForm,header.
	 * @return The login page.
	 */
	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage() {
		
		return "login";
		
	}
	
	
	/**
	 * Handles the access-denied .
	 */
	@GetMapping("/access-denied")
	public String showAccessDenied() {
		
		return "access-denied";
		
	}

	/**
	 * Handles the logout.
	 * Called on header.
	 * @param request
	 * @param response
	 * @return The Login page with logout PathVarialbe.
	 */
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
			
		}
		
		
	    return "redirect:/showMyLoginPage?logout";
	}
	
}
