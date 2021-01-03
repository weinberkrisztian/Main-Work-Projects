package weinber.szakdolgozat.edzoterem.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.Ticket;
import weinber.szakdolgozat.edzoterem.entity.User;

@Repository
public class TicketDAO implements TicketDAOIF {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Ticket> findAll() {
		Session session=entityManager.unwrap(Session.class);
		
		
		Query<Ticket> ticketQuery=session.createQuery("from Ticket", Ticket.class);
		
		
		List<Ticket> ticketList = null;
		try {
			ticketList = ticketQuery.getResultList();
		} catch (Exception e) {
			ticketList = null;
		}
		

		return ticketList;
	}


	@Override
	public Ticket findById(int ticketId) {
		Session session=entityManager.unwrap(Session.class);
		
		
		Query<Ticket> ticketQuery=session.createQuery("from Ticket where id=:tId", Ticket.class);
				
		ticketQuery.setParameter("tId", ticketId);
		
		Ticket theTicket = null;
		try {
			theTicket = ticketQuery.getSingleResult();
		} catch (Exception e) {
			theTicket = null;
		}
		

		return theTicket;
	}


	@Override
	public Ticket findByType(String type) {
		Session session=entityManager.unwrap(Session.class);
		
		
		Query<Ticket> ticketQuery=session.createQuery("from Ticket where type=:tType", Ticket.class);
				
		ticketQuery.setParameter("tType", type);
		
		Ticket theTicket = null;
		try {
			theTicket = ticketQuery.getSingleResult();
		} catch (Exception e) {
			theTicket = null;
		}
		

		return theTicket;
	}


	@Override
	public void save(Ticket ticket) {
		Session session=entityManager.unwrap(Session.class);
		
		session.saveOrUpdate(ticket);
		
	}


	@Override
	public void removeTicketFromUser(User user) {
		Session session=entityManager.unwrap(Session.class);

		Ticket ticket=user.getTicket();
		ticket.removeUser(user);
		
		
		Query<Ticket> ticketQuery=session.createQuery("from Ticket where id=:tId", Ticket.class);
		
		ticketQuery.setParameter("tId", 1);
		
		Ticket dailyTicket = null;
		try {
			dailyTicket = ticketQuery.getSingleResult();
		} catch (Exception e) {
			dailyTicket = null;
		}
		
		
		
		user.setTicket(dailyTicket);
		user.setWeeklyCheckIn(0);

		dailyTicket.addUser(user);
		
		session.saveOrUpdate(user);
		session.saveOrUpdate(ticket);
		session.saveOrUpdate(dailyTicket);
		
		
		
		
		
	}


	@Override
	public void deleteTicket(Ticket ticket) {
		Session session=entityManager.unwrap(Session.class);

		session.delete(ticket);
		
	}

}
