package weinber.szakdolgozat.edzoterem.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.xml.bind.v2.schemagen.xmlschema.Appinfo;

import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.TrainingClass;
import weinber.szakdolgozat.edzoterem.entity.User;
import weinber.szakdolgozat.edzoterem.service.AppointmentService;
import weinber.szakdolgozat.edzoterem.service.AppointmentServiceIF;
import weinber.szakdolgozat.edzoterem.service.TrainingClassServiceIF;
import weinber.szakdolgozat.edzoterem.service.UserServiceIF;

@Controller
@RequestMapping("/weinbergym")
public class ScheduleController {

	@Autowired
	private UserServiceIF userService;
	
	@Autowired
	private AppointmentServiceIF appointmentService;
	
	@Autowired
	private TrainingClassServiceIF trainingClassService;
	
	/**
	 * Handles the schedule page.
	 * Called on training-class-details, header.
	 * @param Model to send all Appointments to the UI.
	 * @return The schedule page.
	 * @throws ParseException 
	 */
	@GetMapping("/schedule")
	public String schedule(Model model) throws ParseException {
		
		List<Appointment> appointmentListEight=appointmentService.findAppointmentsByStartFromInOrder(1);
		List<Appointment> appointmentListNine=appointmentService.findAppointmentsByStartFromInOrder(2);
		List<Appointment> appointmentListTen=appointmentService.findAppointmentsByStartFromInOrder(3);
		List<Appointment> appointmentListEleven=appointmentService.findAppointmentsByStartFromInOrder(4);
		List<Appointment> appointmentListSeventeen=appointmentService.findAppointmentsByStartFromInOrder(5);
		List<Appointment> appointmentListEightTeen=appointmentService.findAppointmentsByStartFromInOrder(6);
		List<Appointment> appointmentListNineTeen=appointmentService.findAppointmentsByStartFromInOrder(7);
		
		List<Appointment> appointmentList=appointmentService.findAll();
		List<Appointment> appointmentWithTrainingClassList=appointmentService.findAllWithTrainingClass();
		
		List<TrainingClass> trainingClassList=trainingClassService.findAll();
		
		model.addAttribute("eight", appointmentListEight);
		model.addAttribute("nine", appointmentListNine);
		model.addAttribute("ten", appointmentListTen);
		model.addAttribute("eleven", appointmentListEleven);
		model.addAttribute("seventeen", appointmentListSeventeen);
		model.addAttribute("eightteen", appointmentListEightTeen);
		model.addAttribute("nineteen", appointmentListNineTeen);
		model.addAttribute("newAppointment", new Appointment());
		model.addAttribute("removedAppointment", new Appointment());
		model.addAttribute("appointmentList", appointmentList);
		model.addAttribute("appointmentWithTrainingClassList", appointmentWithTrainingClassList);
		model.addAttribute("trainingClassList", trainingClassList);
		
		return "schedule-form";
	}
	
	/**
	 * Shows the page of a TrainingClass with detailed booking from the schedule page, shows booked users depending on user priviliges.
	 * Called on schedule-form.
	 * @param The id of the Appointment.
	 * @param Model to send all Appointments to the UI.
	 * @return The training-class-details page.
	 * @RequestParam("appId") int appId,
	 */
	@GetMapping("/chosenClass")
	public String chosenClass(@RequestParam(value = "id" , required = false) int id,Model model) {
		 Appointment theAppointment=appointmentService.findById(id);
		 User user = userService.getAuthenticatedUser();
		 
		 if(theAppointment.getUser().contains(user)) { 
			 String booked="Már sikeresen foglaltál helyet az adott időpontra!";
			model.addAttribute("alreadyBooked", booked);
		 }
		if(theAppointment.getMaxPerson()==theAppointment.getCurrentPerson()) {
			String full="Sajnos, nincs már több hely erre az időpontra!";
			model.addAttribute("theCourseIsFull", full);
		}
		List<User> userList=userService.findAllUserNotInSpecificAppointment(theAppointment.getId());
	
		model.addAttribute("appointment", theAppointment);
		model.addAttribute("user", new User());
		model.addAttribute("userList", userList);
		return "training-class-details";
	}
	
	/**
	 * Adding a new Appointment to the schedule-form, this function is only available for admins.
	 * After the Admin chose which date and TrainingClass needs to be added/updated to the new Appointment.
	 * Called on training-class-details.
	 * @param ModelAttribute The actual Appointment.
	 * @param Model to send all Appointments to the UI.
	 * @return redirects to the /weinbergym/chosenClass -- Training-details-form
	 */
	@PostMapping("/addAppointment")
	public String addAppointment(@ModelAttribute() Appointment appointment,
			Model model,RedirectAttributes redirectAttributes) {
		
		// find appointment
		Appointment theAppointmentToBeUpdated = appointmentService.findById(appointment.getId());
		if(theAppointmentToBeUpdated.getTrainingClass() != null) {
			theAppointmentToBeUpdated.getUser().clear();
			theAppointmentToBeUpdated.setCurrentPerson(0);
		}
		TrainingClass trainingClass=trainingClassService.findById(appointment.getTrainingClass().getId());
		trainingClass.addAppointment(theAppointmentToBeUpdated);
		theAppointmentToBeUpdated.setTrainingClass(trainingClass);
		theAppointmentToBeUpdated.setMaxPerson(appointment.getMaxPerson());
		// update the appointment
		appointmentService.saveAppointment(theAppointmentToBeUpdated);
		
		return "redirect:/weinbergym/schedule";
	}	
	
	/**
	 * Removing an Appointment from the schedule-form, this function is only available for admins.
	 * @param ModelAttribute The actual Appointment.
	 * @param Model to send all Appointments to the UI.
	 * @return redirects to the /weinbergym/chosenClass -- Training-details-form
	 */
	@PostMapping("/removeAppointment")
	public String removeAppointment(@ModelAttribute() Appointment appointment,
			Model model,RedirectAttributes redirectAttributes) {
		
		// find appointment
		Appointment theAppointmentToBeRemoved = appointmentService.findById(appointment.getId());

		
		
		TrainingClass trainingClass=trainingClassService.findById(theAppointmentToBeRemoved.getTrainingClass().getId());
		trainingClass.deleteAppointment(theAppointmentToBeRemoved);
		theAppointmentToBeRemoved.setTrainingClass(null);
		userService.deleteEveryUserFromAppointment(theAppointmentToBeRemoved);
		
		
		theAppointmentToBeRemoved.setCurrentPerson(0);
		// remove the appointment
		appointmentService.saveAppointment(theAppointmentToBeRemoved);
		
		return "redirect:/weinbergym/schedule";
	}	
	
	/**
	 * Adding a new User to an appointment on the training-class-details page, this function is only available for admins.
	 * Called on training-class-details.
	 * @param ModelAttribute The actual User.
	 * @param The id of the appointment.
	 * @param Model to send all Appointments to the UI.
	 * @return redirects to the /weinbergym/chosenClass -- Training-details-form
	 */
	@PostMapping("/addUser")
	public String addUser(@ModelAttribute(value = "user") User user,
			@RequestParam(value = "appointmentId", required = false) int appointmentId,Model model,RedirectAttributes redirectAttributes) {
		
		// find appointment
		Appointment appointment = appointmentService.findById(appointmentId);
		
		User theRealUser = userService.findById(user.getId());
		// add user to the appointment
		if(appointment.getCurrentPerson() != appointment.getMaxPerson()) {
		userService.saveAppointment(theRealUser.getUserName(), appointment);
		}
		
		// redirects to the /weinbergym/chosenClass -- Training-details-form
		redirectAttributes.addAttribute("id",appointment.getId());
		return "redirect:/weinbergym/chosenClass";
	}
	
	/**
	 * Handles the checking in and out for the User, on the Training-class-details page, this function is only avaliable for admins.
	 * Called on training-class-details.
	 * @param The id of the User.
	 * @param The id of the appointment.
	 * @param Model to send all Appointments to the UI.
	 * @return redirects to the /weinbergym/chosenClass -- Training-details-form
	 */
	@GetMapping("/chosenClassUserCheckInNOut")
	public String chosenClassUserCheckInNOut(@RequestParam(value = "userId" , required = false) int userId,
			@RequestParam(value = "appointmentId", required = false) int appointmentId,
			Model model,RedirectAttributes redirectAttributes) {
		
		// find appointment
		Appointment appointment = appointmentService.findById(appointmentId);
		

		// update users checking
		User user=userService.findById((long) userId);
		userService.checkInOrOut(user,appointment);
		userService.update(user);

		
		
		// redirects to the /weinbergym/chosenClass -- Training-details-form
		redirectAttributes.addAttribute("id",appointment.getId());
		return "redirect:/weinbergym/chosenClass";
	}
	
	/**
	 * Handles the booking for an User to an TrainingClass Appointment.
	 * Called on training-class-details.
	 * @param The id of the appointment.
	 * @param model
	 * @return redirects to the /weinbergym/chosenClass -- Training-details-form
	 */
	@GetMapping("/booking")
	public String bookClass(@RequestParam("id") int id,Model model,RedirectAttributes redirectAttributes) {
		
		Appointment appointment=appointmentService.findById(id);
		
		 User user = userService.getAuthenticatedUser();
		if(user.getAppointment().contains(appointment)) {
			return "training-class-details";
		}
		Appointment updatedAppointment=null;
		
		if(appointment.getMaxPerson()!=appointment.getCurrentPerson()) {
		 updatedAppointment=appointmentService.updateCurrentPerson(appointment);
		 appointment=updatedAppointment;
		 userService.saveAppointment(user.getUserName(), appointment);
		}
		redirectAttributes.addAttribute("id", appointment.getId());
		return "redirect:/weinbergym/chosenClass";
	}
	
	/**
	 * Manages the unbooking of an User. Deletes the chosen User from the Appointment.
	 * Called on training-class-details.
	 * @param The id of the Apointment.
	 * @param model
	 * @return Redirects to the /weinbergym/chosenClass -- Training-details-form
	 */
	@GetMapping("/unbooking")
	public String unBookClass(@RequestParam("id") int id,Model model,RedirectAttributes redirectAttributes) {
		Appointment appointment=appointmentService.findById(id);
		 User user = userService.getAuthenticatedUser();
		 
		if(user.getAppointment().contains(appointment)) {
			userService.deleteUserFromAppointment(appointment,user);
		}
		
		redirectAttributes.addAttribute("id", appointment.getId());
		return "redirect:/weinbergym/chosenClass";
	}
	
	/**
	 * Manages the unbooking of an User by the admin. Deletes the chosen User from the Appointment. Redirects to /weinbergym/chosenClass.
	 * Called on training-class-details.
	 * @param The id of the Apointment.
	 * @param The userName of the User.
	 * @param redirectAttributes
	 * @return Redirects to /weinbergym/chosenClass.
	 */
	@RequestMapping("/unbookingAdmin")
	public String adminUnbooking(@RequestParam int id,@RequestParam String uName, RedirectAttributes redirectAttributes) {
		
		Appointment appointment=appointmentService.findById(id);
		
		User user = userService.findByUserName(uName);

		// Redirects to the /weinbergym/chosenClass -- Training-details-form

		redirectAttributes.addAttribute("id", appointment.getId());
		userService.deleteUserFromAppointment(appointment, user);
		return "redirect:/weinbergym/chosenClass";
		
		

	}
	
	
//	private void generateModelAttributes(List<Appointment> appointmentList, Model model) {
//		String appointmentString = null;
//		Appointment appointment = null;
//		for (int i = 0; i < appointmentList.size(); i++) {
//
//			appointment = appointmentList.get(i);
//			String day = getDayStringInHungarian(appointment.getDay());
//			appointmentString = "appointment" + day + appointment.getTimespan();
//			model.addAttribute(appointmentString, appointment);
//
//		}
//	}
	
	public static String getDayStringInHungarian(Date date) {
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		int dayOfTheWeek=c.get(Calendar.DAY_OF_WEEK)-1;
		switch(dayOfTheWeek){
		case 1: return "Hetfo";
		case 2: return "Kedd";
		case 3: return "Szerda";
		case 4: return "Csutortok";
		case 5: return "Pentek";
		case 6: return "Szombat";
		case 7: return "Vasarnap";

		}
		
		return "";
	}
	
}
