package weinber.szakdolgozat.edzoterem.service;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weinber.szakdolgozat.edzoterem.dao.AppointMentDAOIF;
import weinber.szakdolgozat.edzoterem.dao.TicketDAOIF;
import weinber.szakdolgozat.edzoterem.dao.TrainerDAOIF;
import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.Ticket;
import weinber.szakdolgozat.edzoterem.entity.User;
import weinber.szakdolgozat.edzoterem.user.CrmTicket;

@Service
public class TicketService implements TicketServiceIF{
	
	@Autowired
	private TicketDAOIF ticketDAOIF;
	
	@Autowired
	private UserServiceIF userService;
	

	@Override
	@Transactional
	public List<Ticket> findAll() {
		List<Ticket> ticketList = ticketDAOIF.findAll();
		return ticketList;
	}

	@Override
	@Transactional
	public Ticket findById(int ticketId) {
		Ticket theTicket= ticketDAOIF.findById(ticketId);
		return theTicket;
	}

	@Override
	public Ticket findDailyTicket() {
		Ticket theTicket= findById(1);
		return theTicket;
	}

	@Override
	@Transactional
	public Ticket findByType(String type) {
		Ticket theTicket= ticketDAOIF.findByType(type);
		return theTicket;
	}

	@Override
	public Ticket convertCrmTicketToTicket(Ticket newTicket,@Valid CrmTicket crmTicket) {
		
		if(newTicket == null) {
		newTicket=new Ticket();
		}
		
		newTicket.setType(crmTicket.getType());
		newTicket.setCheckingLimit(crmTicket.getCheckingLimit());
		newTicket.setNormalPrice(crmTicket.getNormalPrice());
		newTicket.setStudentPrice(crmTicket.getStudentPrice());
		
		if(!(crmTicket.getUsers() == null)) {
			newTicket.setUsers(crmTicket.getUsers());
		}
		
		
		
		
		return newTicket;
	}

	@Override
	@Transactional
	public void save(Ticket ticket) {
		ticketDAOIF.save(ticket);
		
	}

	@Override
	public CrmTicket convertTicketToCrmTicket(Ticket ticket) {
		CrmTicket crmTicket=new CrmTicket();
		
		crmTicket.setId(ticket.getId());
		crmTicket.setType(ticket.getType());
		crmTicket.setCheckingLimit(ticket.getCheckingLimit());
		crmTicket.setNormalPrice(ticket.getNormalPrice());
		crmTicket.setStudentPrice(ticket.getStudentPrice());
		crmTicket.setUsers(ticket.getUsers());
		
		
		return crmTicket;
		
	}

	@Override
	@Transactional
	public void setTicketsUserListToDailyTicket(Ticket ticket) {
		while(ticket.getUsers().size() != 0) {
			List<User> userList = ticket.getUsers();
			removeTicketFromUser(userList.get(0));
		}
		
	}

	@Override
	@Transactional
	public void removeTicketFromUser(User user) {
		ticketDAOIF.removeTicketFromUser(user);
		
	}

	@Override
	@Transactional
	public void deleteTicket(Ticket ticket) {
		ticketDAOIF.deleteTicket(ticket);
	}

}
