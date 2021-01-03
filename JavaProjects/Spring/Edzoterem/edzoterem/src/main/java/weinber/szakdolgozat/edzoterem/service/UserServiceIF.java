package weinber.szakdolgozat.edzoterem.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.userdetails.UserDetailsService;

import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.Role;
import weinber.szakdolgozat.edzoterem.entity.User;
import weinber.szakdolgozat.edzoterem.user.CrmUser;

public interface UserServiceIF extends UserDetailsService {

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
	 * Converts a Crmuser from the UI into a real User. Saves the user, it is used for brand new Users.
	 * It is used for registrating new Users, it only sets the necessary properties. 
	 * @param A CrmUser from the UI.
	 */
    public void save(CrmUser CrmUuser);

	/**
	 * Converts an User into a CrmUser.
	 * @param An User.
	 * @return The CrmUser for the UI.
	 */
    public CrmUser convertUserToCrmUser(User user);
    
	/**
	 * Converts a CrmUser into an User.
	 * @param An CrmUser.
	 * @return The User.
	 */
    public User convertCrmUserToUser(CrmUser user);

	/**
	 * For updating User from the UI, using a CrmUser wchich holds the updated properties.
	 * It is for already existing users.
	 * Converts a Crmuser from the UI into a real User. Saves the user, if it is an existing user, then updates it.
	 * Updates every property. 
	 * @param A User from the DB.
	 * @param A CrmUser from the UI.
	 * @return The updated User.
	 */
	public User update(User user,CrmUser CrmUser);

	/**
	 * Generates a password code for the renewing process.
	 * @return The new password code in String form.
	 */
	public String generatePasswordCode();
	
	/**
	 * Generates a random number between 1-100 for users boxNumber property.
	 * @return The generated number.
	 */
	public String generateBoxNumber();
	
	/**
	 * Generates and sets a new unique box number between 1-100. The uniqueness is based of the Users gender.
	 * It doesn't update the User in the DB.
	 * @param The checked in Users with updated boxNumber property. 
	 * @return The generated box number.
	 */
	public User setUserBoxNumber(long userId);

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
	public User deleteUserFromAppointment(Appointment appointment,User user);

	/**
	 * Sets the Ticket property for a CrmUser from an actual User. If it already has a ticket, than the TicketStart will not be updated.
	 * @param The CrmUser.
	 * @param The real User.
	 */
	public void setTicketForCrmUser(CrmUser crmUser, User originalUser);
	
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
	 * Sets the checkedIn and WeeklyCkednIn property for a CrmUser from an actual User.
	 * @param The CrmUser.
	 * @param The real User.
	 */
	public void setCheckInForCrmUser(CrmUser crmUser, User originalUser);
	
	/**
	 * For updating Users on the backend, without data comming from the UI.
	 * Updates the User.
	 * @param The User to be updated.
	 * @return The updated User.
	 */
	public User update(User user);
	
	/**
	 * If the given User is checked in than it sets it to out, the same with out.
	 * It also set a new boxNumber during checking and removes it during during checkout.
	 * It does not contains updating it in the DB.
	 * @param The User to be checked in or out.
	 */
	public void checkInOrOut(User user, Appointment appointment);

	/**
	 * Finds the current authenticated user.
	 * @return The current authenticated user
	 */
	public User getAuthenticatedUser();
	
	/**
	 * Finds the current authenticated users username.
	 * @return The current authenticated users username.
	 */
	public String getAuthenticatedUserName();
	
	/**
	 * Finds a Role based on the roleName. 
	 * @param roleName The name of the role.
	 * @return The searched Role.
	 */
	public Role getRoleByName(String roleName);
	
	
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
	 * Removes every user from a specific Appointment.
	 * @param appointment The Appointment that holds the Users.
	 */
	void deleteEveryUserFromAppointment(Appointment appointment);

	/**
	 * Deletes the User from every Appointment the he is booked for.
	 * @param user The User with the Appointments.
	 */
	public void deleteUserFromEveryAppointment(User user);

	/**
	 * Removes every Role from the User.
	 * @param user The User with the Role list.
	 */
	public void removeEveryRoleFromUser(User user);

	/**
	 * Deletes the User.
	 * @param user The User to be deleted.
	 */
	public void deleteUser(User user);
	
	
	
	
}
