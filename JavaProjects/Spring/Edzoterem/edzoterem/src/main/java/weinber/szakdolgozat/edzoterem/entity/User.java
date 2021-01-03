package weinber.szakdolgozat.edzoterem.entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "username")
	private String userName;
	
	@Column(name = "password")
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;
	
	@Column(name = "password_code")
	private String passwordCode;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name = "users_roles", 
	joinColumns = @JoinColumn(name = "user_id"), 
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name = "user_appointment", 
	joinColumns = @JoinColumn(name = "user_id"), 
	inverseJoinColumns = {
	@JoinColumn(name = "appointment_id")})
	private Collection<Appointment> appointment;
	
	@Column(name = "ticket_start")
	@Temporal(TemporalType.DATE )
	private Date ticketStart;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "ticket_id" )
	private Ticket ticket;
	
	// 0- checked in 1- checked out
	@Column(name = "checked_in")
	private int checkedIn;
	
	@Column(name = "check_in")
    @Temporal(TemporalType.TIME )
	private Date checkIn;
	
	@Column(name = "check_out")
    @Temporal(TemporalType.TIME )
	private Date checkOut;
	
	@Column(name = "weekly_check_in")
	private int weeklyCheckIn;
	
	// 0- male 1-female
	@Column(name = "gender")
	private int gender;
	
	@Column(name = "box_number")
	private String boxNumber;
	
	public User() {
	}

	public User(String userName, String password, String firstName, String lastName, String email) {
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public User(String userName, String password, String firstName, String lastName, String email,
			Collection<Role> roles) {
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.roles = roles;
	}
	
	
	public void addRole(Role role) {
		roles.add(role);
	}
	
	public void removwRole(Role role) {
		if(roles.contains(role)) {
			roles.remove(role);
		}
	}
	
	public void addAppointment(Appointment theAppointment) {
		appointment.add(theAppointment);
	}
	
	public void deleteAppointment(Appointment theAppointment) {
		if(appointment.size()!=0) {
			appointment.remove(theAppointment);
		}
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public String getPasswordCode() {
		return passwordCode;
	}

	public void setPasswordCode(String passwordCode) {
		this.passwordCode = passwordCode;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	
	public Collection<Appointment> getAppointment() {
		return appointment;
	}

	public void setAppointment(Collection<Appointment> appointment) {
		this.appointment = appointment;
	}

	public Date getTicketStart() {
		return ticketStart;
	}

	public void setTicketStart(Date ticketStart) {
		this.ticketStart = ticketStart;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public int getCheckedIn() {
		return checkedIn;
	}

	public void setCheckedIn(int checkedIn) {
		this.checkedIn = checkedIn;
	}

	public int getWeeklyCheckIn() {
		return weeklyCheckIn;
	}

	public void setWeeklyCheckIn(int weeklyCheckIn) {
		this.weeklyCheckIn = weeklyCheckIn;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	
	

}
