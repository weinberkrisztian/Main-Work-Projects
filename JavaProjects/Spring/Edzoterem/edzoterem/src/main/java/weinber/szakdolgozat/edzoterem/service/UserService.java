package weinber.szakdolgozat.edzoterem.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import weinber.szakdolgozat.edzoterem.dao.RoleDAOIF;
import weinber.szakdolgozat.edzoterem.dao.UserDAOIF;
import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.Role;
import weinber.szakdolgozat.edzoterem.entity.Ticket;
import weinber.szakdolgozat.edzoterem.entity.User;
import weinber.szakdolgozat.edzoterem.user.CrmUser;

@Service
public class UserService implements UserServiceIF {
	
	@Autowired
	private UserDAOIF userDAOIF;
	

	@Autowired
	private RoleDAOIF roleDAOIF;
	
	@Autowired
	private RoleServiceIF roleService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private TicketServiceIF ticketService;
	

	@Override
	@Transactional
	public User findByUserName(String userName) {
		User user=userDAOIF.findByUserName(userName);
		
		return user;
	}

	@Override
	@Transactional
	public void save(CrmUser crmUser) {
		
		User user=new User();
		
		// every new user starts with the ticket id 1 and ticker start null
		Ticket ticket = ticketService.findById(1);
		   java.util.Date date=new java.util.Date(); 
		
//		get properties
		user.setUserName(crmUser.getUserName());
		user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
		user.setFirstName(crmUser.getFirstName());
		user.setLastName(crmUser.getLastName());
		user.setEmail(crmUser.getEmail());
		user.setCheckedIn(1);
		user.setWeeklyCheckIn(0);
		user.setRoles(Arrays.asList(getRoleByName(RoleDAOIF.ROLE_USER)));
		user.setTicket(ticket);
		user.setTicketStart(date);
		userDAOIF.save(user);
	}
	

	
	@Override
	@Transactional
	public User update(User user,CrmUser crmUser) {
		
		System.out.println(crmUser.getPassword());
		System.out.println(passwordEncoder.encode(crmUser.getPassword()));
//		get properties
		user.setUserName(crmUser.getUserName());
		// if false, we encode the password because it is new
		if(crmUser.isPasswordEncoded()==false) {
		user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
		}else {
		user.setPassword(crmUser.getPassword());
		}
		user.setFirstName(crmUser.getFirstName());
		user.setLastName(crmUser.getLastName());
		user.setEmail(crmUser.getEmail());
//		user.setRoles(Arrays.asList(getRoleByName(RoleDAOIF.ROLE_USER)));
		user.setPasswordCode(generatePasswordCode());
		user.setGender(crmUser.getGender());
		if(crmUser.getAppointment()!=null) {
			user.setAppointment(crmUser.getAppointment());
		}
			user.setTicket(crmUser.getTicket());
			user.setTicketStart(crmUser.getTicketStart());
		if(crmUser.getBoxNumber() != null) {
			user.setBoxNumber(crmUser.getBoxNumber());
		}
		if(crmUser.getCheckIn() != null)
			user.setCheckIn(crmUser.getCheckIn());
		if(crmUser.getCheckOut() != null)
			user.setCheckOut(crmUser.getCheckOut());
		user.setCheckedIn(crmUser.getCheckedIn());
		user.setWeeklyCheckIn(crmUser.getWeeklyCheckIn());
		userDAOIF.save(user);

		return user;
	}
	
	@Override
	@Transactional
	public User findById(Long userId) {
		User user=userDAOIF.findById(userId);
		return user;
	}
	
	@Override
	@Transactional
	public User findByEmail(String email) {
		User user=userDAOIF.findByEmail(email);
		return user;
	}
	
	@Override
	@Transactional
	public void saveAppointment(String userName, Appointment appointment) {
		userDAOIF.saveAppointment(userName, appointment);
		
	}
	
	@Override
	@Transactional
	public User deleteUserFromAppointment(Appointment appointment,User user) {
		User theUser=userDAOIF.deleteUserFromAppointment(appointment,user);
		return theUser;
	}
	
	@Override
	@Transactional
	public void deleteEveryUserFromAppointment(Appointment appointment) {
		
		while(appointment.getUser().size() != 0) {
			List<User> userList= (List<User>) appointment.getUser();
			userDAOIF.deleteUserFromAppointment(appointment,userList.get(0));
		}
		
	}
	
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDAOIF.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public CrmUser convertUserToCrmUser(User user) {
		CrmUser crmUser=new CrmUser();
		crmUser.setEmail(user.getEmail());
		crmUser.setFirstName(user.getFirstName());
		crmUser.setLastName(user.getLastName());
		crmUser.setPassword(user.getPassword());
		crmUser.setMatchingPassword(user.getPassword());
		crmUser.setUserName(user.getUserName());
		crmUser.setGender(user.getGender());
		if(user.getAppointment()!=null) {
			crmUser.setAppointment(user.getAppointment());
		}
			crmUser.setTicket(user.getTicket());
			crmUser.setTicketStart(user.getTicketStart());
		if(user.getBoxNumber() != null) {
			crmUser.setBoxNumber(user.getBoxNumber());
		}
		if(user.getCheckIn() != null)
			crmUser.setCheckIn(user.getCheckIn());
		if(user.getCheckOut() != null)
			crmUser.setCheckOut(user.getCheckOut());
		crmUser.setCheckedIn(user.getCheckedIn());
		crmUser.setWeeklyCheckIn(user.getWeeklyCheckIn());
		return crmUser;
	}



	@Override
	public User convertCrmUserToUser(CrmUser crmUser) {
		User user=new User();
		user.setEmail(crmUser.getEmail());
		user.setFirstName(crmUser.getFirstName());
		user.setLastName(crmUser.getLastName());
		user.setPassword(crmUser.getPassword());
		user.setUserName(crmUser.getUserName());
		user.setGender(crmUser.getGender());
		// we check if these properties are null, because they are not required to be initialized
		if(crmUser.getAppointment()!=null) {
			user.setAppointment(crmUser.getAppointment());
		}
			user.setTicket(crmUser.getTicket());
			user.setTicketStart(crmUser.getTicketStart());
		if(crmUser.getBoxNumber() != null) {
			user.setBoxNumber(crmUser.getBoxNumber());
		}
		if(crmUser.getCheckIn() != null)
			user.setCheckIn(crmUser.getCheckIn());
		if(crmUser.getCheckOut() != null)
			user.setCheckOut(crmUser.getCheckOut());
			
		user.setCheckedIn(crmUser.getCheckedIn());
		user.setWeeklyCheckIn(crmUser.getWeeklyCheckIn());
		return user;
	}
	
	@Override
	public String generatePasswordCode() {
		Random random = new Random();

		// generate a random integer from 0 to 899, then add 100
		int x = random.nextInt(999) + 1000;
		String theCode=String.valueOf(x);
		return theCode;
	}

	@Override
	public void setTicketForCrmUser(CrmUser crmUser, User originalUser) {
		if(crmUser.getTempTicketId()!= null) {
			Ticket ticket=ticketService.findById(crmUser.getTempTicketId());
			crmUser.setTicket(ticket);
			   java.util.Date date=new java.util.Date(); 
			// we check if the ticket id equals with the id from the original user, it decides if we need to assign a new ticket start value to it, id 1 is the default no ticket
			if(!crmUser.getTempTicketId().equals(1)) {
			if(originalUser.getTicket().equals(crmUser.getTempTicketId())) {
				   crmUser.setTicketStart(originalUser.getTicketStart());  
				   crmUser.setWeeklyCheckIn(originalUser.getWeeklyCheckIn());
			}
			else if(originalUser.getTicket().getId() != crmUser.getTempTicketId()) {

				   crmUser.setTicketStart(date);  
				   crmUser.setWeeklyCheckIn(0);
			}
			}else {
				crmUser.setWeeklyCheckIn(0);
				crmUser.setTicketStart(date);  
			}
		}
		if(crmUser.getTempTicketId() == null) {
			crmUser.setTicket(originalUser.getTicket());
		}

		
	}

	@Override
	public List<User> findAll() {
		List<User> userList=userDAOIF.findAll();
		return userList;
	}

	@Override
	public List<User> findBySearch(String name) {
		List<User> userList=userDAOIF.findBySearch(name);
		return userList;
	}

	@Override
	public void setCheckInForCrmUser(CrmUser crmUser, User originalUser) {
		crmUser.setWeeklyCheckIn(originalUser.getWeeklyCheckIn());
		crmUser.setCheckedIn(originalUser.getCheckedIn());
		crmUser.setCheckIn(originalUser.getCheckIn());
		crmUser.setCheckOut(originalUser.getCheckOut());
	}

	@Override
	@Transactional
	public User update(User user) {
		userDAOIF.save(user);
		return user;
	}

	@Override
	public void checkInOrOut(User user, Appointment appointment) {
		int checkedIn=user.getCheckedIn();
		if(checkedIn==1) {
			setUserBoxNumber(user.getId());
			user.setCheckedIn(0);
			
			java.util.Date date=new java.util.Date(); 
			user.setCheckIn(date);
			
		}else if(checkedIn==0) {
			user.setBoxNumber("");
			user.setCheckedIn(1);
			java.util.Date date=new java.util.Date(); 
			user.setCheckOut(date);
			
			if(user.getTicket().getId() != 1 && user.getWeeklyCheckIn() != user.getTicket().getCheckingLimit()) {
			user.setWeeklyCheckIn(user.getWeeklyCheckIn()+1);
			insertLog(user, appointment,false);
			}else {
			insertLog(user, appointment,true);
			}
		}
	}


	@Override
	public User getAuthenticatedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User user=findByUserName(username);
		return user;
	}


	@Override
	public String getAuthenticatedUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		return username;
	}

	@Override
	public Role getRoleByName(String roleName) {
		Role theRole = roleDAOIF.findRoleByName(roleName);
		return theRole;
	}

	@Override
	public User setUserBoxNumber(long userId) {

		User user=findById(userId);
		int gender=user.getGender();
		String boxNumber=null;
		user.setBoxNumber(boxNumber);
		Boolean exists=null;
		do {
			boxNumber=generateBoxNumber();
			user.setBoxNumber(boxNumber);
			exists=userDAOIF.checkExistingBoxNumber(user);
			
		}while(exists);
		
		
		
		
		return user;
	}

	@Override
	public String generateBoxNumber() {
		Random r = new Random();
		int low = 1;
		int high = 100;
		int result = r.nextInt(high-low) + low;
		
		return String.valueOf(result);
	}

	@Override
	public void insertLog(User user, Appointment appointment, boolean dailyTicket) {
		userDAOIF.insertLog(user, appointment,dailyTicket);
		
	}

	@Override
	public List<User> findAllUserNotInSpecificAppointment(int appointmentId) {
		List<User> userList=userDAOIF.findAllUserNotInSpecificAppointment(appointmentId);
		return userList;
	}

	@Override
	@Transactional
	public void deleteUserFromEveryAppointment(User user) {
		while(user.getAppointment().size() != 0) {
		List<Appointment> appointmentList=(List<Appointment>) user.getAppointment();
		deleteUserFromAppointment(appointmentList.get(0), user);
		}
		
	}

	@Override
	@Transactional
	public void removeEveryRoleFromUser(User user) {
		while(user.getRoles().size() != 0) {
		List<Role> roleList=(List<Role>) user.getRoles();
		roleService.removeRoleFromUser(roleList.get(0), user);
		}
		
	}

	@Override
	@Transactional
	public void deleteUser(User user) {
		userDAOIF.deleteUser(user);
		
	}
	
	




	
}
