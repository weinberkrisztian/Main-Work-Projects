package weinber.szakdolgozat.edzoterem.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weinber.szakdolgozat.edzoterem.dao.AppointMentDAOIF;
import weinber.szakdolgozat.edzoterem.dao.RoleDAOIF;
import weinber.szakdolgozat.edzoterem.dao.TicketDAOIF;
import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.Role;
import weinber.szakdolgozat.edzoterem.entity.Ticket;
import weinber.szakdolgozat.edzoterem.entity.User;

@Service
public class RoleService implements RoleServiceIF{
	
	@Autowired
	private RoleDAOIF roleDAO;

	@Override
	public Role findRoleByName(String roleName) {
		Role role=roleDAO.findRoleByName(roleName);
		return role;
	}

	@Override
	public List<Role> findMissingRolesForUser(User user) {
		List<Role> roleList=findAll();
		List<Role> userRoleList=(List<Role>) user.getRoles();
		List<Role> missingRoleList=new ArrayList<Role>();
		
		for (int i = 0; i < roleList.size(); i++) {
			if(!userRoleList.contains(roleList.get(i))) {
				missingRoleList.add(roleList.get(i));
			}
			
		}
		
		return missingRoleList;
	}

	@Override
	public List<Role> findAll() {
		List<Role> roleList=roleDAO.findAll();
		return roleList;
	}

	@Override
	@Transactional
	public void addRoleToUser(Role role, User user) {
		roleDAO.addRoleToUser(role, user);
		
	}

	@Override
	public Role findById(Long id) {
		Role role=roleDAO.findById(id);
		return role;
	}

	@Override
	@Transactional
	public void removeRoleFromUser(Role theRole, User user) {
		roleDAO.removeRoleFromUser(theRole, user);
		
	}
	
	
}

