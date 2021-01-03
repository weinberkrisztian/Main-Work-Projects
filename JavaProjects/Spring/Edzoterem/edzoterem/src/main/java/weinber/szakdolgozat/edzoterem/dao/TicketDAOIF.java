package weinber.szakdolgozat.edzoterem.dao;

import java.util.List;

import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.Ticket;
import weinber.szakdolgozat.edzoterem.entity.User;

public interface TicketDAOIF {

	/**
	 * Lists all Ticket.
	 * @return The List of all Ticket.
	 */
    public List<Ticket>findAll();
    
	/**
	 * Finds a Ticket with the given id.
	 * @param The id of the Ticket.
	 * @return The Ticket.
	 */
    public Ticket findById(int ticketId);

	/**
	 * Finds a Ticket with the given type.
	 * @param The type of the Ticket.
	 * @return The Ticket.
	 */    
    public Ticket  findByType(String type);
    

	/**
	 * Saving a Ticket to the Database.
	 * @param ticket The Ticket to be saved.
	 */
	public void save(Ticket ticket);

	/**
	 * Updates the User Ticket to a daily ticket.
	 * @param user The User that needs to be updated.
	 */
	public void removeTicketFromUser(User user);

	/**
	 *  Deletes the Ticket.
	 * @param ticket The ticket to be deleted.
	 */
	public void deleteTicket(Ticket ticket);
}
