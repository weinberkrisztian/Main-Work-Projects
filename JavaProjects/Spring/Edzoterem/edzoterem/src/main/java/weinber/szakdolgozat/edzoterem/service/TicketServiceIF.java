package weinber.szakdolgozat.edzoterem.service;

import java.util.List;

import javax.validation.Valid;

import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.Ticket;
import weinber.szakdolgozat.edzoterem.entity.User;
import weinber.szakdolgozat.edzoterem.user.CrmTicket;

public interface TicketServiceIF {

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
	 * Finds the Daily Ticket object.
	 * @return The Daily Ticket object.
	 */    
    public Ticket  findDailyTicket();
    
	/**
	 * Finds a Ticket with the given type.
	 * @param The type of the Ticket.
	 * @return The Ticket.
	 */    
    public Ticket  findByType(String type);
    
    /**
     * Convert a crmTicket into a real Ticket.
     * @param crmTicket The CrmTikcet from the UI that holds the new data.
     * @param newTicket If you want to update it, you have to initialize it before calling it,
     * give null value if you want to save a clearly new Ticket.
     * @return A real Ticket that has the new properties from the UI.
     */
	public Ticket convertCrmTicketToTicket(Ticket newTicket,@Valid CrmTicket crmTicket);
	
	/**
	 * Saving a Ticket to the Database.
	 * @param ticket The Ticket to be saved.
	 */
	public void save(Ticket ticket);

	/**
	 * Convert a Ticket into a CrmTicket for Ticket update.
	 * @param ticket The original Ticket that has the default data.
	 * @return A CrmTicket that holds the data from the original Ticket.
	 */
	public CrmTicket convertTicketToCrmTicket(Ticket ticket);

	/**
	 * Sets a Tcikets UserList to Daily ticket (id=1)
	 * @param ticket The ticket, which contains the User list.
	 */
	public void setTicketsUserListToDailyTicket(Ticket ticket);
	
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
