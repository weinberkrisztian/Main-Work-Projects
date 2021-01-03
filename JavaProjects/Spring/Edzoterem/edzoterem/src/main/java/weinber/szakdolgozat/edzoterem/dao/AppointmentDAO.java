package weinber.szakdolgozat.edzoterem.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.User;

@Repository
public class AppointmentDAO implements AppointMentDAOIF {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Appointment> findAllByUserName(String userName) {
		Session session=entityManager.unwrap(Session.class);
		
		
		Query<User> userQuery=session.createQuery("from User where userName=:uName", User.class);
				
		userQuery.setParameter("uName", userName);
		
		User theUser = null;
		try {
			theUser = userQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}
		

		return (List<Appointment>) theUser.getAppointment();
	}

	@Override
	public List<Appointment> findAll() {
		Session session=entityManager.unwrap(Session.class);
		
		
		Query<Appointment> appointmetnQuery=session.createQuery("from Appointment", Appointment.class);
				
		
		List<Appointment> appointmentList = null;
		try {
			appointmentList = appointmetnQuery.getResultList();
		} catch (Exception e) {
			appointmentList = null;
		}
		

		return appointmentList;
	}
	



	@Override
	public Appointment findById(int id) {
		Session session=entityManager.unwrap(Session.class);
		
		
		Query<Appointment> appointmetnQuery=session.createQuery("from Appointment where id=:aId", Appointment.class);
				
		appointmetnQuery.setParameter("aId", id);
		
		Appointment theAppointment = null;
		try {
			theAppointment = appointmetnQuery.getSingleResult();
		} catch (Exception e) {
			theAppointment = null;
		}
		

		return theAppointment;
	}

	@Override
	public Appointment updateCurrentPerson(Appointment appointment) {
		Session session=entityManager.unwrap(Session.class);
		if(appointment.getMaxPerson()!=appointment.getCurrentPerson())
		appointment.setCurrentPerson(appointment.getCurrentPerson()+1);
		
		session.saveOrUpdate(appointment);
		return appointment;
	}

	@Override
	public void updateAppointmentDate(Appointment appointment) {
		Session session=entityManager.unwrap(Session.class);
		
//		User user=null;
//		for (int i = 0; i < appointment.getUser().size(); i++) {
//			List<User> userList=(List<User>) appointment.getUser();
//			user=userList.get(i);
//			user.deleteAppointment(appointment);
//			session.saveOrUpdate(user);
//			session.saveOrUpdate(appointment);
//		}
		
		appointment.getUser().clear();
		appointment.setCurrentPerson(0);
		Calendar c = Calendar.getInstance();
        c.setTime(appointment.getDay());
        c.add(Calendar.DATE, 7);
        
        appointment.setDay(c.getTime());
		session.saveOrUpdate(appointment);

	}

	@Override
	public List<Appointment> findAppointmentsByStartFromInOrder(int timeSpan) throws ParseException {
		Session session=entityManager.unwrap(Session.class);
		Query<Appointment> appQuery=session.createQuery("from Appointment where timeFromId=:uTimeFromId", Appointment.class);
				
		appQuery.setParameter("uTimeFromId", timeSpan);
		
		List<Appointment> appList = null;
		try {
			appList = appQuery.getResultList();
		} catch (Exception e) {
			appList = null;
		}
		

		return appList;
	}
	
	@Override
	public void saveAppointment(Appointment appointment) {
		
		Session session=entityManager.unwrap(Session.class);


		

		session.saveOrUpdate(appointment);
		

		
//		session.getTransaction().commit();
		
	}
	
}
