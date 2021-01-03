package weinber.szakdolgozat.edzoterem.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import weinber.szakdolgozat.edzoterem.dao.RoleDAOIF;
import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.Email;
import weinber.szakdolgozat.edzoterem.entity.Password;
import weinber.szakdolgozat.edzoterem.entity.Role;
import weinber.szakdolgozat.edzoterem.entity.Ticket;
import weinber.szakdolgozat.edzoterem.entity.User;
import weinber.szakdolgozat.edzoterem.service.EmailSercieIF;
import weinber.szakdolgozat.edzoterem.service.RoleServiceIF;
import weinber.szakdolgozat.edzoterem.service.TicketServiceIF;
import weinber.szakdolgozat.edzoterem.service.UserServiceIF;
import weinber.szakdolgozat.edzoterem.user.CrmUser;
import weinber.szakdolgozat.edzoterem.user.Search;

@Controller
@RequestMapping("/weinbergym")
public class UserController {
	
    private Logger logger = Logger.getLogger(getClass().getName());
    
	@Autowired
	private UserServiceIF userService;
	
	@Autowired
	private EmailSercieIF emailService;
	
	@Autowired
	private RoleServiceIF roleService;
	
	@Value("${spring.mail.username}")
	private String MESSAGE_FROM;
	
	@Value("${email.password.change.subject}")
	private String MESSAGE_SUBJECT_PASSWORD;
	
	@Autowired
	private TicketServiceIF ticketService;
	
	
	/**
	 * Shows the user-search page, to list all Users for the admin.
	 * Called on header.
	 * @param To send a Search object and the userList to the UI.
	 * @return user-search page.
	 */
	@GetMapping("/findUser")
	public String findUser(Model model) {
		
		Search searchObj=new Search();
		model.addAttribute("search", searchObj);
	
		List<User> userList=userService.findAll();
		model.addAttribute("userList", userList);
		
		return "user-search";
		}
	
	
	/**
	 * Handles the searching for the user-search page.
	 * Called on user-search.
	 * @param To get a Search object that contains the searched name.
	 * @param To send the userList to the UI.
	 * @return Redirect to the user-search page.
	 */
	@PostMapping("/findUserPost")
	public String findUserPost(@ModelAttribute("search") Search search,Model model) {
		// send the search object to the UI for further searches
		Search searchObj=new Search();
		model.addAttribute("search", searchObj);
		
		String name=search.getName();
		List<User> userList=null;
		
		// if the search is empty it returns all users, otherwise it sends back all users whose name contains the searched name
		if(name.equals("")) {
			userList=userService.findAll();

		}else {
			userList=userService.findBySearch(name);
		}
		// send the userList to the UI
		model.addAttribute("userList", userList);
		
		return "user-search";
		}

	/**
	 * Handles the checking in and out for the chosen User. Only admins can use it. 
	 * Called on user-search.
	 * @param The id of the chosen User.
	 * @param To send the updated userList to the UI.
	 * @return Redirect to the user-search page.
	 */
	@GetMapping("/findUserPostCheckInNOut")
	public String findUserPostCheckInNOut(@RequestParam("userId") int userId ,Model model) {
		// send the search object to the UI for further searches
		Search searchObj=new Search();
		model.addAttribute("search", searchObj);
		
		
		User user=userService.findById((long) userId);
		userService.checkInOrOut(user,null);
		userService.update(user);
		
		List<User> userList=null;

		userList=userService.findAll();
		
		// send the userList to the UI
		model.addAttribute("userList", userList);
		
		return "user-search";
		}
	
	/**
	 * Shows a detailed page of the chosen User. It is used to modify the Users data for admins(shows the tickets).
	 * Called on training-class-details, user-search.
	 * @param The id of the chosen User.
	 * @param model
	 * @return user-details page.
	 */
	@GetMapping("/chosenUserDetails")
	public String chosenUserDetails(@RequestParam("chosenUserId") Long id,Model model) {
		

		if(id!=null) {
		User user=userService.findById(id);
		CrmUser crmUser=userService.convertUserToCrmUser(user);
		List<Ticket> ticketList=ticketService.findAll();
		model.addAttribute("ticketList", ticketList);

		model.addAttribute("user", crmUser);
		model.addAttribute("modifiedUserId", user.getId());
		// to not show the password modification to the User
		model.addAttribute("display", "display-none");
		// if true, than we do not want to encode the Password
		model.addAttribute("passwordEncoding",true);
	
		return "user-details";
		}
		return "redirect:/weinbergym/welcome";
	}
	
	
	/**
	 * Shows a detailed page of the chosen User. It is used to modify the Users data for Users.
	 * Called on training-class-details, user-search.
	 * @param The id of the chosen User.
	 * @param model
	 * @return user-details page.
	 */
	@GetMapping("/userDetails")
	public String userDetails(Model model) {

		
		String username = userService.getAuthenticatedUserName();
		if(username!=null) {
		// converting the current user to CrmUser to send it to the UI for modification
		User user=userService.findByUserName(username);
		CrmUser crmUser=userService.convertUserToCrmUser(user);
		List<Ticket> ticketList=ticketService.findAll();
		model.addAttribute("ticketList", ticketList);
		
		// modelAttributes
		model.addAttribute("user", crmUser);
		model.addAttribute("modifiedUserId", user.getId());
		// to not show the password modification to the User
		model.addAttribute("display", "display-none");
		// if true, than we do not want to encode the Password
		model.addAttribute("passwordEncoding",true);
		return "user-details";
		}
		return "redirect:/weinbergym/welcome";
	}
	
	/**
	 * Starts the password modification for the User. It send an email with the required passwordCode, wchis is generated during User update.
	 * If it is sent, it redirects us to the user-details-password-confirm page, where we can enter the passwordCode.
	 * Called on header.
	 * @param model To send a Password Object to the UI.
	 * @return redirects to the user-details-password-confirm
	 */
	@GetMapping("/userPasswordModification")
	public String userPasswordModification(Model model) {

		String username = userService.getAuthenticatedUserName();
		if(username!=null) {
		// converting the current user to CrmUser to send it to the UI for modification
		User user=userService.findByUserName(username);
		CrmUser crmUser=userService.convertUserToCrmUser(user);
		// it generates the passwordCode for the User
		user=userService.update(user,crmUser);
		
		// email setup
		String message="Kedves "+user.getFirstName()+"!\nA jelszó módosítás megkezdéséhez az alábbi számsort kell majd megadnia: "+user.getPasswordCode()+"!";
		//TO DO: properties filebe kivezetni es kodolast kijavitani benne
		String subject="Jelszó módosítás";
		Email mail=emailService.createEmail(MESSAGE_FROM, user.getEmail(), subject, message);
		emailService.sendEmail(mail);
		
		// sending Password object to the UI
		Password passwordCode=new Password();
		model.addAttribute("password", passwordCode);
		return "user-details-password-confirm";
		}
		return "redirect:/weinbergym/welcome";
	}
	
	/**
	 * Managing the modifications for User properties.
	 * Called on user-details.
	 * @param crmUser The CrmUser from the UI
	 * @param bindingResult To check for error
	 * @param modifiedUserId The id of the modified User
	 * @param passwordEncoding To determine if the password is already encoded or not(true== encoded)
	 * @param model For sending necessery data to the UI
	 * @param request For logging out.
	 * @param response For logging out.
	 * @return If the update went successfull we log out, if any error occurs we are sent back to the modification page with warnings.
	 */
	@PostMapping("/userDetailsProcessing/{modifiedUserId}/{passwordEncoding}")
	public String userDetailsProcessing(@Valid@ModelAttribute("user") CrmUser crmUser,BindingResult bindingResult,@PathVariable long modifiedUserId,
			@PathVariable boolean passwordEncoding,Model model,HttpServletRequest request, HttpServletResponse response) {
		
		User originalUser=userService.findById(modifiedUserId);
		
		// setting properties that are not required for a User, order is important here, baceause of weeklyCheckIn property.
		userService.setCheckInForCrmUser(crmUser,originalUser);
		userService.setTicketForCrmUser(crmUser,originalUser);
		
		// if true, than we do not want to encode the Password again
		if(passwordEncoding==true) {
			model.addAttribute("display", "display-none");
		}
		
		// checking for errors
		if(bindingResult.hasErrors()) {
			List<Ticket> ticketList=ticketService.findAll();
			model.addAttribute("ticketList", ticketList);
			return "user-details";
		}
		
		//set password encoding, if true we wont encode it again, because it is already encoded
		crmUser.setPasswordEncoded(passwordEncoding);
		
		
		//check if the User already exists
		logger.info("Ellenőrzés hogy a "+ crmUser.getUserName()+" nevű felhasználó létezik e");
		User user=userService.findByUserName(crmUser.getUserName());
		if(user!=null) {
		if(!originalUser.getUserName().equals(user.getUserName())) {
			model.addAttribute("registrationError", "A felhasználó már foglalt.");

			logger.warning("A felhasználó már foglalt.");
			
			return "user-details";
		}
		}
		
		//checking for already taken emails
		user=userService.findByEmail(crmUser.getEmail());
		if(user!=null) {
		if(!originalUser.getUserName().equals(user.getUserName())) {
			model.addAttribute("registrationErrorEmail", "Az email már foglalt.");
			
			logger.warning("Az email már foglalt.");
			
			return "user-details";
		}
		}

		// updating the originalUser, with the CrmUser from the UI
		userService.update(originalUser,crmUser);
		
		//logout after succesfull modification with User role, else it redirects to the welcome page
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		if (auth != null && authorities.contains(userService.getRoleByName(RoleDAOIF.ROLE_USER))){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
			
				return "redirect:/showMyLoginPage?changedUser";
		}
		
		return "redirect:/weinbergym/welcome";
		
	}
	
	/**
	 * Processing the new passord modification for the User. 
	 * It send us to the user-deatails page with acces to modify the User password. 
	 * Called on user-details-password-confrim.
	 * @param password
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@PostMapping("/userDetailsPaaswordProcessing")
	public String userDetailsProcessing(@Valid@ModelAttribute("password") Password password,
			BindingResult bindingResult, Model model) {
		
		
		// checking for errors
		if(bindingResult.hasErrors()) {
			return "user-details-password-confirm";
		}
		
		String username = userService.getAuthenticatedUserName();
		if(username!=null) {
		User user=userService.findByUserName(username);
		// checking if the password code is correct
		if(password.getPasswordCode().equals(user.getPasswordCode())) {
			System.out.println("megegyezik");
			
			CrmUser crmUser=userService.convertUserToCrmUser(user);
			crmUser.setPassword("");
			crmUser.setMatchingPassword("");
			
			// modelAttributes
			model.addAttribute("user", crmUser);
			model.addAttribute("modifiedUserId", user.getId());
			// to show the password modification to the User
			model.addAttribute("display", "display-password");
			// setting the passwordEncoding to false, to allow the User to modify the password
			model.addAttribute("passwordEncoding",false);
			return "user-details";
		}
		
		
	}
		return "redirect:/weinbergym/welcome";
	}
	
	@GetMapping("/userRole")
	public String userRole(@RequestParam("userId") long userId, Model model) {
		
		User user=userService.findById(userId);
		List<Role> userRoleList=(List<Role>) user.getRoles();
		
		List<Role> missingUserRoleList=roleService.findMissingRolesForUser(user);
		
		Role role=new Role();
		
		model.addAttribute("userId", user.getId());
		model.addAttribute("roleAdd", role);
		model.addAttribute("userRoleList", userRoleList);
		model.addAttribute("missingUserRoleList", missingUserRoleList);
		
		return "user-role";
	}
	
	@PostMapping("/userRoleAddProcessing/{userId}")
	public String userRoleAddProcessing(@ModelAttribute("roleAdd") Role role, @PathVariable("userId") long userId, Model model) {
		
		Role theRole=roleService.findById(role.getId());
		User user=userService.findById(userId);
		
		roleService.addRoleToUser(theRole, user);
		
//		return "redirect:/weinbergym/user-role?userId";
		return "welcome";
	}
	
	@PostMapping("/userRoleRemoveProcessing/{userId}")
	public String userRoleRemoveProcessing(@ModelAttribute("roleRemove") Role role, @PathVariable("userId") long userId, Model model) {
		
		Role theRole=roleService.findById(role.getId());
		User user=userService.findById(userId);
		
		roleService.removeRoleFromUser(theRole, user);
		
//		return "redirect:/weinbergym/user-role?userId";
		return "welcome";
	}
	
	@GetMapping("/userDelete")
	public String userDelete(@RequestParam("userId") long userId, Model model) {
		
		User user=userService.findById(userId);
		

		long autheticatedId = userService.getAuthenticatedUser().getId();
		
		userService.deleteUserFromEveryAppointment(user);
		userService.removeEveryRoleFromUser(user);
		userService.deleteUser(user);
		
		if(autheticatedId == userId) {
			return "redirect:/logout";
		}
		
		
		return "redirect:/weinbergym/findUser";
	}
	
}
