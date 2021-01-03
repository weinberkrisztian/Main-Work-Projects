package weinber.szakdolgozat.edzoterem.controller;

import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import weinber.szakdolgozat.edzoterem.entity.Email;
import weinber.szakdolgozat.edzoterem.entity.User;
import weinber.szakdolgozat.edzoterem.service.EmailSercieIF;
import weinber.szakdolgozat.edzoterem.service.UserServiceIF;
import weinber.szakdolgozat.edzoterem.user.CrmUser;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
	
	@Autowired
	private UserServiceIF userSerice;
	
	@Autowired
	private EmailSercieIF emailService;

	@Value("${spring.mail.username}")
	private String MessageFrom;
	
    private Logger logger = Logger.getLogger(getClass().getName());
	
    
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	/**
	 * Handles the registration page.
	 * Called on login, registrationForm.
	 * @param Send a new CrmUser to the UI.
	 * @return Registration page.
	 */
	@GetMapping("/registrationForm")
	public String registration(Model model) {
		
		
		model.addAttribute("crmUser", new CrmUser());
		
		return "registrationForm";
	}
	
	/**
	 * Handles the validation of the new User from the registration page, it sends an email to the new User if there was no error.
	 * Called on registrationForm.
	 * @param The CrmUser from the UI.
	 * @param To check if there was any error in validation.
	 * @param To send data to the UI.
	 * @return Registration-confirmation page, if it has any errors it send you back to the Registration page and binds the pathvariables.
	 */
	@PostMapping("/processRegistrationForm")
	public String registrationProcess(@Valid@ModelAttribute("crmUser") CrmUser crmUser,
			BindingResult bindingResult, Model model) {
		
		String userName = crmUser.getUserName();
		logger.info("Ellenőrzés hogy a "+ userName+" nevű felhasználó létezik e");
		
		
		// ha tartalmaz errort
		if(bindingResult.hasErrors()) {
			return "registrationForm";
		}
		
		User existingUser=userSerice.findByUserName(userName);
		if(existingUser!=null) {
			crmUser=userSerice.convertUserToCrmUser(existingUser);
			
			// modelAttributes
			model.addAttribute("crmUser", crmUser);
			model.addAttribute("registrationError", "A felhasználó már létezik.");
			
			logger.warning("A felhasználó már létezik.");
			
			return "registrationForm";
			
		}
		
		String emailCheck = crmUser.getEmail();
		logger.info("Ellenőrzés hogy a "+ userName+" nevű felhasználó létezik e");
		User existingEmail=userSerice.findByEmail(emailCheck);
		if(existingEmail!=null) {
			crmUser=userSerice.convertUserToCrmUser(existingEmail);
			// modelAttributes
			model.addAttribute("crmUser", crmUser);
			model.addAttribute("registrationErrorEmail", "Az email már foglalt.");
			
			logger.warning("Az email már létezik.");
			
			return "registrationForm";
			
		}
		
		userSerice.save(crmUser);
		
		logger.info("A felhasználó sikeresen létrehozva: "+userName);
		
		//setup Email
		String subject="Sikeres Regisztráció Visszaigazolás";
		String message="Kedves "+userName+"!\nSok sikert kívánunk a céljaid elérésében!";
		Email email=emailService.createEmail(MessageFrom, crmUser.getEmail(), subject, message);
		emailService.sendEmail(email);
		
		return "registration-confirmation";
		
	}
	
	
	
	
	
}
