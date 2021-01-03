package weinber.szakdolgozat.edzoterem.dao;

import java.text.ParseException;
import java.util.List;

import weinber.szakdolgozat.edzoterem.entity.Appointment;

public interface AppointMentDAOIF {
	
	/**
	 * Lists all appointments of the User with the given userName.
	 * @param The userName of the User.
	 * @return The List of appointments.
	 */
	public List<Appointment> findAllByUserName(String userName);
	
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
	 * Lists all appointments on a given time span.
	 * @param The time_from_id id.
	 * @return The List of appointments of the time span in order.
	 * @throws ParseException 
	 */
	public List<Appointment> findAppointmentsByStartFromInOrder(int timeSpan) throws ParseException;

	/**
	 * Updates an Appointment.
	 * @param appointment The actual Appointment to be updated.
	 */
	void saveAppointment(Appointment appointment);
	
	

}
