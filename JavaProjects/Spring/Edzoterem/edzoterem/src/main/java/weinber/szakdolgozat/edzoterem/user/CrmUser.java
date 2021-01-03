package weinber.szakdolgozat.edzoterem.user;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.Role;
import weinber.szakdolgozat.edzoterem.entity.Ticket;
import weinber.szakdolgozat.edzoterem.validation.EmailValidation;
import weinber.szakdolgozat.edzoterem.validation.MatchingPasswordValidation;

@MatchingPasswordValidation.List({
	@MatchingPasswordValidation(first = "password", second = "matchingPassword", message = "A jelszavaknak meg kell egyezniük!")
})
public class CrmUser {
	
	
	

	@NotNull(message = "A felhasználónév megadása kötelező!")
	@Size(min = 5, message = "A felhasználónévnek legalább 5 karakter hosszúnak kell lennie!")
	private String userName;

	@NotNull(message = "A jelszó megadása kötelező!")
	@Size(min = 5, message = "A jelszónak legalább 5 karakter hosszúnak kell lennie!")
	private String password;
	
	@NotNull(message = "A jelszó megadása kötelező!")
	@Size(min = 5, message = "A jelszónak legalább 5 karakter hosszúnak kell lennie!")
	private String matchingPassword;

	@Size(min = 1, message = "A keresztnév megadása kötelező!")
	private String firstName;

	@Size(min = 1, message = "A vezetéknév megadása kötelező!")
	private String lastName;

	@EmailValidation
	@NotNull(message = "Az email megadása kötelező!")
	private String email;
	
	@NotNull(message = "A nem megadása kötelező!")
	private int gender;
	
	private boolean passwordEncoded=true;

	private Collection<Appointment> appointment;
	
	private Date ticketStart;
	
	private Ticket ticket;
	
	private Integer tempTicketId;
	
	private int checkedIn;
	
	private Date checkIn;
	
	private Date checkOut;
	
	private int weeklyCheckIn;
	
	private String boxNumber;
	
	private Collection<Role> role;

	public CrmUser() {

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

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
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
	
	public boolean isPasswordEncoded() {
		return passwordEncoded;
	}


	public void setPasswordEncoded(boolean passwordEncoded) {
		this.passwordEncoded = passwordEncoded;
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
		if(ticketStart == null) {
			this.ticketStart=null;
		}else {
		this.ticketStart = ticketStart;
		}
	}

	public Ticket getTicket() {
		return ticket;
	}


	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}


	public Integer getTempTicketId() {
		return tempTicketId;
	}


	public void setTempTicketId(Integer tempTicketId) {
		this.tempTicketId = tempTicketId;
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


	public Collection<Role> getRole() {
		return role;
	}


	public void setRole(Collection<Role> role) {
		this.role = role;
	}

	
	
}
