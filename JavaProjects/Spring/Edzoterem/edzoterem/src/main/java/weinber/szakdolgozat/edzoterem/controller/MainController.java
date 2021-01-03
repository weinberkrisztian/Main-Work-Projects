package weinber.szakdolgozat.edzoterem.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.service.AppointmentServiceIF;

@Controller
public class MainController {

	@Autowired
	private AppointmentServiceIF appointmentService;
	
	/**
	 * Handles the default page, which is sends it to the welcome page.
	 * @param model
	 * @return Welcome page.
	 */
	@RequestMapping("/")
	public String index(Model model) {
		
		
		
		return "index";
	}
	

	/**
	 * Handles the welcome page.
	 * @param model
	 * @return Welcome page.
	 */
	@RequestMapping("/weinbergym/welcome")
	public String welcome(Model model) {

		
//        int days=LocalDate.now().getDayOfYear();
//		
//		List<Appointment> appointmentList=appointmentService.findAll();
//		for (int i = 0; i < appointmentList.size(); i++) {
//			LocalDate appointmentDate=LocalDate.parse(appointmentList.get(i).getDay().toString());
//			if(appointmentDate.getDayOfYear()==LocalDate.now().getDayOfYear()) {
//				String [] arrStr=appointmentList.get(i).getTimeFrom().toString().split(":");
//				LocalTime appTime=LocalTime.of(Integer.valueOf(arrStr[0]), Integer.valueOf(arrStr[1]),Integer.valueOf(arrStr[2]));
//
// 				if(appTime.getHour()-1<=LocalTime.now().getHour()) {
// 					appointmentService.updateAppointmentDate(appointmentList.get(i));
//				}
//					
//			}else if(appointmentDate.getDayOfYear()<LocalDate.now().getDayOfYear()) {
//				appointmentService.updateAppointmentDate(appointmentList.get(i));
//			}
//			
//			
//		}
		
		
		return "welcome";
	}
	
}
