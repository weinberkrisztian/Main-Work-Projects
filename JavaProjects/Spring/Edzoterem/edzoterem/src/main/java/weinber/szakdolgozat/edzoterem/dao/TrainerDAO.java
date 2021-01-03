package weinber.szakdolgozat.edzoterem.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import weinber.szakdolgozat.edzoterem.entity.Trainer;
import weinber.szakdolgozat.edzoterem.entity.TrainingClass;
import weinber.szakdolgozat.edzoterem.entity.User;
import weinber.szakdolgozat.edzoterem.service.TrainerServiceIF;
import weinber.szakdolgozat.edzoterem.user.CrmTrainer;

@Repository
public class TrainerDAO   implements TrainerDAOIF{

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Trainer> findAll() {
		Session session=entityManager.unwrap(Session.class);
		
		Query<Trainer> trainerQuery=session.createQuery("from Trainer");
		
		List<Trainer> trainerList = null;
		try {
			trainerList = trainerQuery.getResultList();
		} catch (Exception e) {
			trainerList = null;
		}

		return trainerList;
	}

	@Override
	public Trainer findById(int id) {
		Session session=entityManager.unwrap(Session.class);
		
		
		Query<Trainer> trainerQuery=session.createQuery("from Trainer where id=:uId", Trainer.class);
				
		trainerQuery.setParameter("uId", id);
		
		Trainer trainer = null;
		try {
			trainer = trainerQuery.getSingleResult();
		} catch (Exception e) {
			trainer = null;
		}
		

		return trainer;
	}

	@Override
	public Trainer findByEmail(String email) {
		Session session=entityManager.unwrap(Session.class);
		
		
		Query<Trainer> trainerQuery=session.createQuery("from Trainer where email=:uEmail", Trainer.class);
				
		trainerQuery.setParameter("uEmail", email);
		
		Trainer trainer = null;
		try {
			trainer = trainerQuery.getSingleResult();
		} catch (Exception e) {
			trainer = null;
		}
		

		return trainer;
	}

	@Override
	public Trainer save(Trainer trainer) {
		Session session=entityManager.unwrap(Session.class);
		session.saveOrUpdate(trainer);
		return trainer;
	}

	@Override
	public void delete(Trainer trainer) {
		Session session=entityManager.unwrap(Session.class);
		session.delete(trainer);
	}



}
