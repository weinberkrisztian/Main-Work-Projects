package weinber.szakdolgozat.edzoterem.dao;

import java.util.List;

import weinber.szakdolgozat.edzoterem.entity.Role;
import weinber.szakdolgozat.edzoterem.entity.User;

public interface RoleDAOIF {
	
	static final String ROLE_USER="ROLE_USER";
	
	static final String ROLE_EMPLOYEE="ROLE_EMPLOYEE";
	
	static final String ROLE_MANAGER="ROLE_MANAGER";
	
	static final String ROLE_ADMIN="ROLE_ADMIN";
	
	/**
	 * Finds a Role with the given roleName.
	 * @param The roleName of the Role.
	 * @return The Role.
	 */
	public Role findRoleByName(String roleName);

	/**
	 * Finds every ROLE.
	 * @return List of every ROLE.
	 */
	public List<Role> findAll();
	
	/**
	 * Add a Role to an User.
	 * @param role The Role.
	 * @param user The User.
	 */
	public void addRoleToUser(Role role, User user);

	/**
	 * Finds a Role by Id.
	 * @param id The searched role's id.
	 * @return The searched Role.
	 */
	public Role findById(Long id);
	
	/**
	 * Removes a Role from the User's role list.
	 * @param theRole The Role to be removed.
	 * @param user The User.
	 */
	public void removeRoleFromUser(Role theRole, User user);

}
