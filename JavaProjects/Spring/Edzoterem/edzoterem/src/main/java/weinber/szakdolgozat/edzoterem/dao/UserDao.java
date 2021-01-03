package weinber.szakdolgozat.edzoterem.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.Info;
import weinber.szakdolgozat.edzoterem.entity.User;

@Repository
public class UserDao implements UserDAOIF {

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private AppointMentDAOIF appointmentDAOIF;

	@Override
	public User findByUserName(String userName) {
		Session session=entityManager.unwrap(Session.class);
		
		
		Query<User> userQuery=session.createQuery("from User where userName=:uName", User.class);
				
		userQuery.setParameter("uName", userName);
		
		User theUser = null;
		try {
			theUser = userQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}
		

		return theUser;
	}

	@Override
	public void save(User user) {
		Session session=entityManager.unwrap(Session.class);
		
		
		session.saveOrUpdate(user);
	}

	@Override
	public User findById(Long userId) {
		Session session=entityManager.unwrap(Session.class);
		
		
		Query<User> userQuery=session.createQuery("from User where id=:uId", User.class);
				
		userQuery.setParameter("uId", userId);
		
		User theUser = null;
		try {
			theUser = userQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}
		

		return theUser;
	}

	@Override
	public User findByEmail(String email) {
		Session session=entityManager.unwrap(Session.class);
		
		
		Query<User> userQuery=session.createQuery("from User where email=:uEmail", User.class);
				
		userQuery.setParameter("uEmail", email);
		
		User theUser = null;
		try {
			theUser = userQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}
		

		return theUser;
	}

	@Override
	public void saveAppointment(String userName, Appointment appointment) {
		
		Session session=entityManager.unwrap(Session.class);

		
		
		Query<User> userQuery=session.createQuery("from User where userName=:uName", User.class);
		
		userQuery.setParameter("uName", userName);
		
		User theUser = null;
		try {
			theUser = userQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}
		
		appointment.setCurrentPerson(appointment.getCurrentPerson()+1);
		theUser.addAppointment(appointment);
		

		session.save(appointment);
		

		
//		session.getTransaction().commit();
		
	}

	@Override
	public User deleteUserFromAppointment(Appointment appointment, User user) {
		Session session=entityManager.unwrap(Session.class);

		
		Query<User> userQuery=session.createQuery("from User where userName=:uName", User.class);
		
		userQuery.setParameter("uName", user.getUserName());
		
		User theUser = null;
		try {
			theUser = userQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}
		
		theUser.deleteAppointment(appointment);
		appointment.removeUser(user);
		appointment.setCurrentPerson(appointment.getCurrentPerson()-1);
		session.saveOrUpdate(appointment);
		session.saveOrUpdate(theUser);
		return theUser;
	}

	@Override
	public List<User> findAll() {
	Session session=entityManager.unwrap(Session.class);
		
		
		Query<User> userQuery=session.createQuery("from User", User.class);
				
		
		List<User> userList = null;
		try {
			userList = userQuery.getResultList();
		} catch (Exception e) {
			userList = null;
		}
		

		return userList;
	}

	@Override
	public List<User> findBySearch(String name) {
		Session session=entityManager.unwrap(Session.class);

		
		
		Query<User> userQuery=session.createQuery("from User where firstName LIKE :serachKeyword OR lastName LIKE :serachKeyword", User.class);
		
		userQuery.setParameter("serachKeyword","%"+name+"%");
		
		List<User> userList = null;
		try {
			userList = userQuery.getResultList();
		} catch (Exception e) {
			userList = null;
		}
		

		return userList;
	}

	@Override
	public Boolean checkExistingBoxNumber(User user) {
		Session session=entityManager.unwrap(Session.class);

		
		
		Query<User> userQuery=session.createQuery("from User where (NOT id=:uId) and gender=:uGender and box_number=:uBoxNumber", User.class);
		
		userQuery.setParameter("uId", user.getId());
		userQuery.setParameter("uGender", user.getGender());
		userQuery.setParameter("uBoxNumber", user.getBoxNumber());
		
		User theUser = null;
		try {
			theUser = userQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}
		
		if(theUser == null) {
			return false;
		}else {
			return true;
		}
		
	}

	@Override
	public void insertLog(User user, Appointment appointment, boolean dailyTicket) {
		Session session=entityManager.unwrap(Session.class);
		Info info=null;
		String dailyTicketStr="Bérlet";
		if(dailyTicket) 
			 dailyTicketStr="Napijegy";
		if(appointment !=null) {
			String trainerFullName=appointment.getTrainingClass().getTrainer().getFirstName()+" "+appointment.getTrainingClass().getTrainer().getLastName();
			// the user has a ticket, but reached the weekly limit of checking in

		info=new Info(user.getId(), user.getCheckIn(), user.getCheckOut(), appointment.getDay(),
		user.getUserName(), appointment.getTrainingClass().getName(), trainerFullName,user.getTicket().getType(),user.getTicketStart(),dailyTicketStr);
			
		}
		else {
			java.util.Date date=new java.util.Date(); 
			 info=new Info(user.getId(), user.getCheckIn(), user.getCheckOut(),date, user.getUserName(),
			"Nincs külön edzés", "Nincs edző",user.getTicket().getType(),user.getTicketStart(),dailyTicketStr);

			}
		
		
		session.save(info);
	}

	@Override
	public List<User> findAllUserNotInSpecificAppointment(int appointmentId) {
		Session session=entityManager.unwrap(Session.class);
		
		List<User> userListAll=findAll();
		Appointment appointment = appointmentDAOIF.findById(appointmentId);
		List<User> appointmentUserList=(List<User>) appointment.getUser();
		List<User> userListResult=new ArrayList<User>();
		
		for (int i = 0; i < userListAll.size(); i++) {
			if(!userListAll.get(i).getAppointment().contains(appointment)) {
				userListResult.add(userListAll.get(i));
			}
		}
		
//		Boolean exists=null;
//		for (int i = 0; i < userListAll.size(); i++) {
//			for (int j = 0; j < appointmentUserList.size(); j++) {
//				exists=true;
//				if(appointmentUserList.get(j).getId() != (userListAll.get(i).getId())){
//					for (int e = 0; e < userListResult.size(); e++) {
//						   exists=false;
//						if(userListResult.get(e).getId() == appointmentUserList.get(j).getId()) {
//							exists=true;
//							break;
//						}
//					}	
//				}
//			if(!exists)
//			userListResult.add(userListAll.get(i));
//			}
//		}
	return userListResult;
	}

	@Override
	public void deleteUser(User user) {
		Session session=entityManager.unwrap(Session.class);
		
		
		session.delete(user);
		
	}
		
	


}
