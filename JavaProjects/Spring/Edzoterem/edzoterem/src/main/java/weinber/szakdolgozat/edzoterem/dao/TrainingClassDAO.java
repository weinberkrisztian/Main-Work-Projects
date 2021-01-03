package weinber.szakdolgozat.edzoterem.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import weinber.szakdolgozat.edzoterem.entity.TrainingClass;
import weinber.szakdolgozat.edzoterem.entity.User;
import weinber.szakdolgozat.edzoterem.service.TrainingClassServiceIF;
import weinber.szakdolgozat.edzoterem.user.CrmTrainingClass;

@Repository
public class TrainingClassDAO   implements TrainingClassDAOIF{

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public TrainingClass findByName(String name) {
		Session session=entityManager.unwrap(Session.class);
		
		
		Query<TrainingClass> userQuery=session.createQuery("from TrainingClass where name=:uName", TrainingClass.class);
				
		userQuery.setParameter("uName", name);
		
		TrainingClass trainingClass = null;
		try {
			trainingClass = userQuery.getSingleResult();
		} catch (Exception e) {
			trainingClass = null;
		}
		

		return trainingClass;
	}

	@Override
	public List<TrainingClass> findAll() {
		Session session=entityManager.unwrap(Session.class);
		
		
		Query<TrainingClass> userQuery=session.createQuery("from TrainingClass");
				
		
		List<TrainingClass> trainingClassList = null;
		try {
			trainingClassList = userQuery.getResultList();
		} catch (Exception e) {
			trainingClassList = null;
		}
		

		return trainingClassList;
	}

	@Override
	public TrainingClass findById(long id) {
		Session session=entityManager.unwrap(Session.class);
		
		
		Query<TrainingClass> userQuery=session.createQuery("from TrainingClass where id=:uId", TrainingClass.class);
				
		userQuery.setParameter("uId", id);
		
		TrainingClass trainingClass = null;
		try {
			trainingClass = userQuery.getSingleResult();
		} catch (Exception e) {
			trainingClass = null;
		}
		

		return trainingClass;
	}

	@Override
	public TrainingClass save(TrainingClass trainingClass) {
		Session session=entityManager.unwrap(Session.class);

		session.saveOrUpdate(trainingClass);
		
		return trainingClass;
	}

	@Override
	public void delete(TrainingClass theTrainingClass) {
		Session session=entityManager.unwrap(Session.class);		
		
		session.delete(theTrainingClass);
	}



}
