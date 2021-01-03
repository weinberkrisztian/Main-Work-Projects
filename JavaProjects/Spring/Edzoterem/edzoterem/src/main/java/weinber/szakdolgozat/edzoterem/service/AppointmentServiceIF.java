package weinber.szakdolgozat.edzoterem.service;

import java.text.ParseException;
import java.util.List;

import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.TrainingClass;

public interface AppointmentServiceIF {

	static final String JIU_JITSU="Jiu-Jitsu";

	/**
	 * Lists all appointments of the User with the given userName.
	 * @param The userName of the User.
	 * @return The List of appointments.
	 */
	public List<Appointment> findAllByUsername(String username);
	
	
	/**
	 * Lists all appointments on a given time span.
	 * @param The time_from_id id.
	 * @return The List of appointments of the time span in order.
	 * @throws ParseException 
	 */
	public List<Appointment> findAppointmentsByStartFromInOrder(int timeSpan) throws ParseException;
	
	/**
	 * Finds an Appointment with the given traininClassName.
	 * @param The List of Appointments to search from.
	 * @param The name of the TrainingClass.
	 * @return The Appointment.
	 */
	public Appointment getAppointmentByName(List<Appointment> appointmentList, String traininClassName);

	/**
	 * Lists all Appointment.
	 * @return The List of all Appointment.
	 */
	public List<Appointment> findAll();
	
	/**
	 * Finds an Appointment with the given id.
	 * @param The id of the Appointment.
	 * @return The Appointment.
	 */
	public Appointment findById(int id);
	
	/**
	 * Updates the given Appointments CurrentPersons property.
	 * @param The Appointment with the new CurrentPersons property.
	 * @return The updated Appointment.
	 */
	public Appointment updateCurrentPerson(Appointment appointment);

	/**
	 * Updates the given Appointments AppointmentDate property.
	 * @param The Appointment with the new AppointmentDate property.
	 */
	public void updateAppointmentDate(Appointment appointment);

	/**
	 * Updates an Appointment.
	 * @param appointment The actual Appointment to be updated.
	 */
	void saveAppointment(Appointment appointment);
	
	/**
	 * Lists all Appointment with no TrainingClass.
	 * @return The List of Appointments.
	 */
	List<Appointment> findAllWithNoTrainingClass();


	/**
	 * Removing the TrainingClass type from the Appointments and refreshes them.
	 * @param theTrainingClass The TrainingClass to be removed.
	 */
	public void deleteTrainingClassFromAppointment(TrainingClass theTrainingClass);


	/**
	 * Finds every Appointment with TrainingClass.
	 * @return The list of every Appointment with a TrainingClass.
	 */
	public List<Appointment> findAllWithTrainingClass();
	
}
