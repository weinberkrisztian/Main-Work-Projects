package weinber.szakdolgozat.edzoterem.dao;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import weinber.szakdolgozat.edzoterem.entity.Role;
import weinber.szakdolgozat.edzoterem.entity.User;

@Repository
public class RoleDao implements RoleDAOIF {

	@Autowired
	private EntityManager entityManager;
	
	
	@Override
	public Role findRoleByName(String roleName) {
		Session session=entityManager.unwrap(Session.class);
		
		
		Query<Role> roleQuery=session.createQuery("from Role where name =: roleName", Role.class);
		
		roleQuery.setParameter("roleName", roleName);
		
		Role role=null;
		try {
			 role=roleQuery.getSingleResult();
		}catch (Exception e) {
			role=null;
		}
		
		return role;
	}


	@Override
	public List<Role> findAll() {
		Session session=entityManager.unwrap(Session.class);
		
		
		Query<Role> roleQuery=session.createQuery("from Role", Role.class);
		
		
		List<Role> roleList=null;
		try {
			 roleList=roleQuery.getResultList();
		}catch (Exception e) {
			roleList=null;
		}
		
		return roleList;
	}


	@Override
	public void addRoleToUser(Role role, User user) {
		Session session=entityManager.unwrap(Session.class);
		
		user.addRole(role);
		
		session.saveOrUpdate(user);
		
	}


	@Override
	public Role findById(Long id) {
		Session session=entityManager.unwrap(Session.class);
		
		
		Query<Role> roleQuery=session.createQuery("from Role where id =: roleId", Role.class);
		
		roleQuery.setParameter("roleId", id);
		
		Role role=null;
		try {
			 role=roleQuery.getSingleResult();
		}catch (Exception e) {
			role=null;
		}
		
		return role;
	}


	@Override
	public void removeRoleFromUser(Role theRole, User user) {
		Session session=entityManager.unwrap(Session.class);
		
		user.removwRole(theRole);
		
		session.saveOrUpdate(user);
		
	}

}
