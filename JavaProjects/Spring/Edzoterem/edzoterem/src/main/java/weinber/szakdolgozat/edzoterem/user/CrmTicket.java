package weinber.szakdolgozat.edzoterem.user;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.Ticket;
import weinber.szakdolgozat.edzoterem.entity.TrainingClass;
import weinber.szakdolgozat.edzoterem.entity.User;
import weinber.szakdolgozat.edzoterem.validation.EmailValidation;
import weinber.szakdolgozat.edzoterem.validation.MatchingPasswordValidation;

public class CrmTicket {

	private Integer id;

	@NotNull(message = " kötelező!")
	@Size(min = 1, message = " kötelező!")
	private String type;
	
	@NotNull(message = " kötelező!")
	private int checkingLimit;
	
	@NotNull(message = " kötelező!")
	private int studentPrice;
	
	@NotNull(message = " kötelező!")
	private int normalPrice;
	

	
	
	private List<User> users;
	

	public CrmTicket() {

	}




	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getCheckingLimit() {
		return checkingLimit;
	}


	public void setCheckingLimit(int checkingLimit) {
		this.checkingLimit = checkingLimit;
	}


	public int getStudentPrice() {
		return studentPrice;
	}


	public void setStudentPrice(int studentPrice) {
		this.studentPrice = studentPrice;
	}


	public int getNormalPrice() {
		return normalPrice;
	}


	public void setNormalPrice(int normalPrice) {
		this.normalPrice = normalPrice;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}


}
