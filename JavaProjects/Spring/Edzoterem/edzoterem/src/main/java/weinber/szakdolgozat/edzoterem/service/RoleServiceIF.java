package weinber.szakdolgozat.edzoterem.service;

import java.util.List;

import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.Role;
import weinber.szakdolgozat.edzoterem.entity.Ticket;
import weinber.szakdolgozat.edzoterem.entity.User;

public interface RoleServiceIF {

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
	 * Return every Role that is not listed on the given User.
	 * @param user The given User.
	 * @return A List of Roles that is not listed on the given User.
	 */
	List<Role> findMissingRolesForUser(User user);

	
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
