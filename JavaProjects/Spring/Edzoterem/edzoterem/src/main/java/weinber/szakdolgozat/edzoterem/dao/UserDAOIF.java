package weinber.szakdolgozat.edzoterem.dao;

import java.util.List;

import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.User;

public interface UserDAOIF {

	/**
	 * Find a user by its userName.
	 * @param The userName of the User.
	 * @return The User.
	 */
    public User findByUserName(String userName);
    
	/**
	 * Find a user by its id.
	 * @param The id of the User.
	 * @return The User.
	 */
    public User findById(Long userId);
    
	/**
	 * Find a user by its email.
	 * @param The email of the User.
	 * @return The User.
	 */
    public User findByEmail(String email);
    
	/**
	 * Saves the User, if it already exist than it updates it with the new properties.
	 * @param The User to be updated.
	 * @return The updated User.
	 */
    public void save(User user);

	/**
	 * Saves an Appointment for a User.
	 * @param The userName of the User.
	 * @param The Appointment.
	 */
    public void saveAppointment(String userName, Appointment appointment);

	/**
	 * Deletes a User from an Appointment.
	 * @param The Appointment.
	 * @param The User.
	 * @return The updated User.
	 */
	public User deleteUserFromAppointment(Appointment appointment, User user);
	
	/**
	 * Lists all users.
	 * @return The List of every user from the database.
	 */
	public List<User> findAll();
	
	/**
	 * Lists all Users whose name contains the name parameter.
	 * @param The searched name of the users.
	 * @return The List of Users.
	 */
	public List<User> findBySearch(String name);
	
	/**
	 * Checks if the generated boxNumber exists in the DB with the same gender as the User.
	 * @param user The user with the new boxNumber.
	 * @return True if it already exists.
	 */
	public Boolean checkExistingBoxNumber(User user);
	
	/**
	 * Logging after checking out. Inserts into the info table.
	 * @param user The checked out user.
	 * @param appointment The appointment.
	 * @param dailyTicket Checks if the User was registered with a daily ticket
	 */
	public void insertLog(User user, Appointment appointment, boolean dailyTicket);

	/**
	 * Finds every User who are not listed on that specific Appointment list.
	 * @param appointmentId The Appointment id.
	 * @return The list of Users who are not taking part of the Appointment.
	 */
	public List<User> findAllUserNotInSpecificAppointment(int appointmentId);

	/**
	 * Deletes the User.
	 * @param user The User to be deleted.
	 */
	public void deleteUser(User user);
	
    
	
}
