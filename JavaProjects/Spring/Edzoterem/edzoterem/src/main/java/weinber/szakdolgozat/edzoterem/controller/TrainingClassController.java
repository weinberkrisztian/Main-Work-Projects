package weinber.szakdolgozat.edzoterem.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import weinber.szakdolgozat.edzoterem.entity.Trainer;
import weinber.szakdolgozat.edzoterem.entity.TrainingClass;
import weinber.szakdolgozat.edzoterem.service.AppointmentServiceIF;
import weinber.szakdolgozat.edzoterem.service.TrainerServiceIF;
import weinber.szakdolgozat.edzoterem.service.TrainingClassServiceIF;
import weinber.szakdolgozat.edzoterem.user.CrmTrainer;
import weinber.szakdolgozat.edzoterem.user.CrmTrainingClass;

@Controller
@RequestMapping("/weinbergym")
public class TrainingClassController {

	@Autowired
	private TrainingClassServiceIF trainingClassService;
	
	@Autowired
	private AppointmentServiceIF appointmentService;
	
	@Autowired
	private TrainerServiceIF trainerService;
	
    private Logger logger = Logger.getLogger(getClass().getName());


	
	/**
	 * Lists the trainig classes.
	 * Called on header. 
	 * @param Model to send the TrainingClass list to the UI.
	 * @return training-class page.
	 */
	@GetMapping("/trainingClass")
	public String traininClass(Model model) {
	
		List<TrainingClass> trainingClassList=trainingClassService.findAll();
		model.addAttribute("trainingClassList", trainingClassList);
		
		return "training-class";
	}
	
	
	// TODO: viszgald meg a URLt
	/**
	 * Show the detailed page of a training-class.
	 * Called on trainer-details,trainers,training-class. 
	 * @param The id of the trainingClass.
	 * @param Model to send the TrainingClass list to the UI.
	 * @return training-class-details page.
	 */
	@GetMapping("/trainingClassDetails")
	public String trainingClassDetails(@RequestParam("id") long id,Model model) {
		
		TrainingClass trainingClass=trainingClassService.findById(id);
		
		// modelAttributes
		model.addAttribute("trainingClass", trainingClass);

		return "training-class-details-basic-new";
	}
	
	/**
	 * Handles the add-trainingClass page, which enables to add new TrainingClasses for the admins.
	 * Called on header.
	 * @param Model to send data to the UI.  
	 * @return Add-TrainingClass page.
	 */
	@GetMapping("/addTrainingClass")
	public String addTrainingClass(Model model) {
		
		CrmTrainingClass crmTrainingClass=new CrmTrainingClass();
		List<Trainer> trainerWithNoTrainingClassList=trainerService.findAllWithNoTraningClass();
		
		model.addAttribute("trainingClass", crmTrainingClass);
		model.addAttribute("trainerList", trainerWithNoTrainingClassList);

		return "add-trainingClass";
	}
	
	/**
	 * Handles the add-trainingClass page, which enables to add new TrainingClasses for the admins.
	 * Called on header.
	 * @param Model to send data to the UI.  
	 * @return add-trainingClass.
	 */
	@PostMapping("/trainingClassAddingDetailsProcessing")
	public String trainingClassAddingDetailsProcessing(@Valid@ModelAttribute("trainingClass") CrmTrainingClass crmTrainingClass,
			BindingResult bindingResult,Model model) {

		if(crmTrainingClass.getTrainerId() != null) {
			crmTrainingClass.setTrainer(trainerService.findById(crmTrainingClass.getTrainerId()));
		}
		// ha tartalmaz errort
		if(bindingResult.hasErrors()) {
			List<Trainer> trainerWithNoTrainingClassList=trainerService.findAllWithNoTraningClass();
			model.addAttribute("trainingClassList", trainerWithNoTrainingClassList);
			System.out.println(bindingResult.getAllErrors());
			return "add-trainingClass";
		}
		String trainingClassName = crmTrainingClass.getName();
		logger.info("Ellenőrzés hogy a "+ trainingClassName+" nevű edzés fajta létezik e");
		
		TrainingClass existingTrainingClass=trainingClassService.findByName(trainingClassName);
		if(existingTrainingClass!=null) {
			// modelAttributes
			model.addAttribute("trainingClass", crmTrainingClass);
			model.addAttribute("nameError", "Az adott név  már használatban van.");
			List<Trainer> trainerWithNoTrainingClassList=trainerService.findAllWithNoTraningClass();
			model.addAttribute("trainingClassList", trainerWithNoTrainingClassList);
			logger.warning("A név már foglalt.");		
			return "add-trainingClass";
		}
		trainingClassService.save(crmTrainingClass,null);
		
		return "redirect:/weinbergym/trainingClass";
	}
	
	/**
	 * Handles the Trainer modification from the Trainer-list page, which enables to modify current trainers for the admins.
	 * Called on trainers.
	 * @param Model to send data to the UI.  
	 * @return modify-trainer page.
	 */
	@GetMapping("/trainingClassModification")
	public String trainingClassModification(@RequestParam("id") int trainingClassId,Model model) {
		TrainingClass trainingClassToBeModified=trainingClassService.findById(trainingClassId);
		
		CrmTrainingClass crmTrainingClass=trainingClassService.convertTrainingClassToCrmTrainingClass(trainingClassToBeModified);
		List<Trainer> trainerWithNoTrainingClassList=trainerService.findAllWithNoTraningClass();
		
		model.addAttribute("trainingClass", crmTrainingClass);
		model.addAttribute("trainerList", trainerWithNoTrainingClassList);
		model.addAttribute("existingTrainingClassId", trainingClassToBeModified.getId());
		return "modify-training-class";
	}
	
	/**
	 * Handles the modify-trainer page, which enables to modify trainers for the admins.
	 * Called on trainers.
	 * @param Model to send data to the UI.  
	 * @return modify-trainer page.
	 */
	@PostMapping("/trainingClassModificationDetailsProcessing/{existingTrainingClassId}")
	public String trainingClassModificationDetailsProcessing(@Valid@ModelAttribute("trainingClass") CrmTrainingClass crmTrainingClass,
			BindingResult bindingResult,@PathVariable int existingTrainingClassId, Model model) {
		if(crmTrainingClass.getTrainerId() != null) {
		crmTrainingClass.setTrainer(trainerService.findById(crmTrainingClass.getTrainerId()));
		}
		TrainingClass trainingClassToBeModified=trainingClassService.findById(existingTrainingClassId);
		model.addAttribute("existingTrainingClassId", trainingClassToBeModified.getId());
		
		if(bindingResult.hasErrors()) {
			List<Trainer> trainerWithNoTrainingClassList=trainerService.findAllWithNoTraningClass();
			model.addAttribute("trainingClassList", trainerWithNoTrainingClassList);
			System.out.println(bindingResult.getAllErrors());
			return "modify-training-class";
		}
		
		String trainingClassName = crmTrainingClass.getName();
		logger.info("Ellenőrzés hogy a "+ trainingClassName+" nevű edzés fajta létezik e");
		
		TrainingClass existingTrainingClass=trainingClassService.findByName(trainingClassName);
		if(existingTrainingClass!=null && existingTrainingClass.getId() != existingTrainingClassId) {
			
			// modelAttributes
			model.addAttribute("trainingClass", crmTrainingClass);
			model.addAttribute("nameError", "Az adott név  már használatban van.");
			List<Trainer> trainerWithNoTrainingClassList=trainerService.findAllWithNoTraningClass();
			model.addAttribute("trainingClassList", trainerWithNoTrainingClassList);
			logger.warning("A név már foglalt.");
			
			return "modify-training-class";
			
		}

		TrainingClass theUpdatedTrainingClass=trainingClassService.findById(existingTrainingClassId);
		trainingClassService.save(crmTrainingClass,theUpdatedTrainingClass);

		return "redirect:/weinbergym/trainingClass";
	}
	
	/**
	 * Handles the TrainingClass deletion from the TrainingClass-list page, which enables to delete current trainingClasses for the admins.
	 * Called on trainingClass.
	 * @param Model to send data to the UI.  
	 * @return trainingClass page.
	 */
	@GetMapping("/trainingClassDelete")
	public String trainingClassDelete(@RequestParam("id") int trainingClassId,Model model) {
		TrainingClass theTrainingClass=trainingClassService.findById(trainingClassId);
		
		if(theTrainingClass.getTrainer() != null) {
			theTrainingClass.getTrainer().setTrainingClass(null);
			theTrainingClass.setTrainer(null);
			trainingClassService.save(null, theTrainingClass);
		}
		appointmentService.deleteTrainingClassFromAppointment(theTrainingClass);
		trainingClassService.delete(theTrainingClass);
		
		return "redirect:/weinbergym/trainingClass";
	}
	
}
