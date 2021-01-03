package weinber.szakdolgozat.edzoterem.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weinber.szakdolgozat.edzoterem.dao.AppointMentDAOIF;
import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.TrainingClass;
import weinber.szakdolgozat.edzoterem.entity.User;

@Service
public class AppointmentService implements AppointmentServiceIF{

	@Autowired
	private AppointMentDAOIF appointmentDAO;
	
	@Autowired
	private UserServiceIF userService;
	
	@Override
	public List<Appointment> findAllByUsername(String username) {
		List<Appointment> appointmentList=appointmentDAO.findAllByUserName(username);
		return appointmentList;
	}
	
	@Override
	public List<Appointment> findAll() {
		List<Appointment> appointmentList=appointmentDAO.findAll();
		return appointmentList;
	}
	
	@Override
	public Appointment getAppointmentByName(List<Appointment> appointmentList,String traininClassName) {
		
		for (int i = 0; i < appointmentList.size(); i++) {
			if(appointmentList.get(i).getTrainingClass().getName()==traininClassName) {
				return appointmentList.get(i);
			}
		}
		
		
		return null;
	}

	@Override
	public Appointment findById(int id) {
		Appointment theAppointment=appointmentDAO.findById(id);
		return theAppointment;
	}

	@Override
	public Appointment updateCurrentPerson(Appointment appointment) {
		Appointment theAppointment=appointmentDAO.updateCurrentPerson(appointment);
		return theAppointment;
	}

	@Override
	@Transactional
	public void updateAppointmentDate(Appointment appointment) {
		appointmentDAO.updateAppointmentDate(appointment);
		
	}

	@Override
	public List<Appointment> findAppointmentsByStartFromInOrder(int timeSpan) throws ParseException {
		List<Appointment> appointmentList=appointmentDAO.findAppointmentsByStartFromInOrder(timeSpan);
		return appointmentList;
	}

	@Override
	@Transactional
	public void saveAppointment(Appointment appointment) {
		appointmentDAO.saveAppointment(appointment);
		
	}

	@Override
	public List<Appointment> findAllWithNoTrainingClass() {
		List<Appointment> appointmentList=appointmentDAO.findAll();
		List<Appointment> appointmentListwithNoTrainingClass=new ArrayList<Appointment>();
		for (int i = 0; i < appointmentList.size(); i++) {
			if(appointmentList.get(i).getTrainingClass() == null) {
				appointmentListwithNoTrainingClass.add(appointmentList.get(i));
			}
		}
		
		return appointmentListwithNoTrainingClass;
	}

	@Override
	@Transactional
	public void deleteTrainingClassFromAppointment(TrainingClass theTrainingClass) {

		List<Appointment> appointmentList=theTrainingClass.getApppointment();
		for (int i = 0; i < appointmentList.size(); i++) {
			Appointment appointment=appointmentList.get(i);
			List<User> userList=(List<User>) appointment.getUser();
			for (int j = 0; j < userList.size(); j++) {
				userService.deleteUserFromAppointment(appointment, userList.get(j));
			}
			appointment.setTrainingClass(null);
			appointmentDAO.saveAppointment(appointment);
		}
		
		
	}

	@Override
	public List<Appointment> findAllWithTrainingClass() {
		List<Appointment> appoinmentList=appointmentDAO.findAll();
		List<Appointment> appointmentWithtTrainingClassList=new ArrayList<Appointment>();
		
		for (int i = 0; i < appoinmentList.size(); i++) {
			
			if(appoinmentList.get(i).getTrainingClass() != null) {
				appointmentWithtTrainingClassList.add(appoinmentList.get(i));
			}
			
		}
		
		return appointmentWithtTrainingClassList;
	}

}
