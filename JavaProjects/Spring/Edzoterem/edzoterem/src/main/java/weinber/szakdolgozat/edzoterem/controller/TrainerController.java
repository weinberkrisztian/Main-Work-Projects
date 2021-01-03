package weinber.szakdolgozat.edzoterem.controller;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import weinber.szakdolgozat.edzoterem.entity.Email;
import weinber.szakdolgozat.edzoterem.entity.Trainer;
import weinber.szakdolgozat.edzoterem.entity.TrainingClass;
import weinber.szakdolgozat.edzoterem.entity.User;
import weinber.szakdolgozat.edzoterem.service.EmailSercieIF;
import weinber.szakdolgozat.edzoterem.service.TrainerServiceIF;
import weinber.szakdolgozat.edzoterem.service.TrainingClassServiceIF;
import weinber.szakdolgozat.edzoterem.service.UserServiceIF;
import weinber.szakdolgozat.edzoterem.user.CrmTrainer;


@Controller
@RequestMapping("/weinbergym")
public class TrainerController {

	@Autowired
	private TrainerServiceIF trainerService;
	
	@Autowired
	private TrainingClassServiceIF trainingClassService;
	
	@Autowired
	private EmailSercieIF emailService;
	
	@Autowired
	private UserServiceIF userService;
	
	@Value("${spring.mail.username}")
	private String MessageFrom;
	
    private Logger logger = Logger.getLogger(getClass().getName());

	@RequestMapping("/trainers")
	public String trainers(Model model) {
		
		model.addAttribute("trainerList", trainerService.findAll());
		return "trainers";
	}
	

	/**
	 * Handles the Trainer detail page, which shows the given trainer with extra details.
	 * Called on trainer-details, trainers, trainer-class-details.
	 * @param The id of the Trainer
	 * @param Model to send the Trainer to the UI.  
	 * @return Trainer detail page.
	 */
	@GetMapping("/trainerDetails")
	public String trainerDetails(@RequestParam("id")int trainerId, Model model) {
		
		Trainer trainer=trainerService.findById(trainerId);
		
		model.addAttribute("trainer", trainer);
		
		String userName=userService.getAuthenticatedUserName();
		System.out.println(userName);
		
		// modelAttributes
		model.addAttribute("username", userName);
		model.addAttribute("trainerId", trainer.getId());
		model.addAttribute("email", new Email());
		
		
		return "trainer-details";
	}
	
	/**
	 * Processes the email-message from the User, and sends it to the given Trainer.
	 * Called on trainer-contact.
	 * @param An email object which contains the email-message from the User. 
	 * @param The id of the given Trainer.
	 * @param model
	 * @return Redirects to the welcome page.
	 */
	@PostMapping("/emailSender/{trainerId}")
	public String email(@ModelAttribute("email")Email userEmail,@PathVariable int trainerId, Model model) {
		
		Trainer trainer=trainerService.findById(trainerId);
		User user=userService.getAuthenticatedUser();
		
		//set Properties for email
		String fullName=user.getFirstName()+" "+user.getLastName();
		String subject="Személyi edzés kérelem "+fullName+" részére!";
		String message=userEmail.getMessage().concat("\nEmail: "+user.getEmail()+"\nNév: "+fullName);
		userEmail=emailService.createEmail(MessageFrom, trainer.getEmail(), subject, message);
		emailService.sendEmail(userEmail);
		
		return "welcome";
	}
	
	/**
	 * Handles the add-trainer page, which enables to add new trainers for the admins.
	 * Called on header.
	 * @param Model to send data to the UI.  
	 * @return Add Trainer page.
	 */
	@GetMapping("/addTrainer")
	public String addTrainer(Model model) {
		
		CrmTrainer crmTrainer=new CrmTrainer();
		List<TrainingClass> trainingClassWithNoTrainerList=trainingClassService.findAllWithNoTrainer();
		
		model.addAttribute("trainer", crmTrainer);
		model.addAttribute("trainingClassList", trainingClassWithNoTrainerList);

		return "add-trainer";
	}
	
	/**
	 * Handles the Trainer modification from the Trainer-list page, which enables to modify current trainers for the admins.
	 * Called on trainers.
	 * @param Model to send data to the UI.  
	 * @return modify-trainer page.
	 */
	@GetMapping("/trainerModification")
	public String trainerModification(@RequestParam("id") int trainerId,Model model) {
		Trainer trainerToBeModified=trainerService.findById(trainerId);
		
		CrmTrainer crmTrainer=trainerService.convertTrainerToCrmTrainer(trainerToBeModified);
		List<TrainingClass> trainingClassWithNoTrainerList=trainingClassService.findAllWithNoTrainer();
		
		model.addAttribute("trainer", crmTrainer);
		model.addAttribute("trainingClassList", trainingClassWithNoTrainerList);
		model.addAttribute("existingTrainerId", trainerToBeModified.getId());

		return "modify-trainer";
	}
	
	/**
	 * Handles the modify-trainer page, which enables to modify trainers for the admins.
	 * Called on trainers.
	 * @param Model to send data to the UI.  
	 * @return modify-trainer page.
	 */
	@PostMapping("/trainerModificationDetailsProcessing/{existingTrainerId}")
	public String trainerModificationDetailsModificationProcessing(@Valid@ModelAttribute("trainer") CrmTrainer crmTrainer,BindingResult bindingResult,@PathVariable int existingTrainerId, Model model) {
		String trainerEmail = crmTrainer.getEmail();
		logger.info("Ellenőrzés hogy a "+ trainerEmail+" nevű edző létezik e");
		
		if(crmTrainer.getTrainingClassId() != null) {
		crmTrainer.setTrainingClass(trainingClassService.findById(crmTrainer.getTrainingClassId()));
		}
		Trainer trainerToBeModified=trainerService.findById(existingTrainerId);
		model.addAttribute("existingTrainerId", trainerToBeModified.getId());
		
		// ha tartalmaz errort
		if(bindingResult.hasErrors()) {
			List<TrainingClass> trainingClassWithNoTrainerList=trainingClassService.findAllWithNoTrainer();
			model.addAttribute("trainingClassList", trainingClassWithNoTrainerList);
	
			return "modify-trainer";
		}
		
		Trainer existingTrainer=trainerService.findByEmail(trainerEmail);
		if(existingTrainer!=null && existingTrainer.getId() != existingTrainerId) {
			
			// modelAttributes
			model.addAttribute("trainer", crmTrainer);
			model.addAttribute("emailError", "Az adott email már használatban van.");
			
			logger.warning("Az email már foglalt.");
			
			return "modify-trainer";
			
		}
		Trainer theUpdatedTrainer=trainerService.findById(existingTrainerId);
		trainerService.save(crmTrainer,theUpdatedTrainer);
		
		
		return "redirect:/weinbergym/trainers";
	}
	
	/**
	 * Handles the add-trainer page, which enables to add new trainers for the admins.
	 * Called on header.
	 * @param Model to send data to the UI.  
	 * @return Add Trainer page.
	 */
	@PostMapping("/trainerAddingDetailsProcessing")
	public String trainerAddingDetailsProcessing(@Valid@ModelAttribute("trainer") CrmTrainer crmTrainer,BindingResult bindingResult,Model model) {
		String trainerEmail = crmTrainer.getEmail();
		logger.info("Ellenőrzés hogy a "+ trainerEmail+" nevű edző létezik e");

		if(crmTrainer.getTrainingClassId() != null) {
		crmTrainer.setTrainingClass(trainingClassService.findById(crmTrainer.getTrainingClassId()));
		}
		
		if(bindingResult.hasErrors()) {
			List<TrainingClass> trainingClassWithNoTrainerList=trainingClassService.findAllWithNoTrainer();
			model.addAttribute("trainingClassList", trainingClassWithNoTrainerList);
			System.out.println(bindingResult.getAllErrors());
			return "add-trainer";
		}
		
		Trainer existingTrainer=trainerService.findByEmail(trainerEmail);
		if(existingTrainer!=null) {
			model.addAttribute("trainer", crmTrainer);
			model.addAttribute("emailError", "Az adott email már használatban van.");
			
			logger.warning("Az email már foglalt.");
			return "add-trainer";
		}
		Trainer theUpdatedTrainer=null;
		trainerService.save(crmTrainer,theUpdatedTrainer);
		
		return "redirect:/weinbergym/trainers";
	}
	
	
	
	/**
	 * Handles the Trainer deletion from the Trainer-list page, which enables to delete current trainers for the admins.
	 * Called on trainers.
	 * @param Model to send data to the UI.  
	 * @return trainers page.
	 */
	@GetMapping("/trainerDelete")
	public String trainerDelete(@RequestParam("id") int trainerId,Model model) {
		
		Trainer trainer=trainerService.findById(trainerId);
		
		if(trainer.getTrainingClass() != null) {
			trainer.getTrainingClass().setTrainer(null);
			trainer.setTrainingClass(null);
			trainerService.save(null, trainer);
		}
		
		trainerService.delete(trainer);
		
		
		return "redirect:/weinbergym/trainers";
	}
	
//	public static String fileNameWithOutExt (String fileName) {
//	    return Optional.of(fileName.lastIndexOf(".")).filter(i-> i >= 0)
//	            .map(i-> fileName.substring(0, i)).orElse(fileName);
//	}
}
