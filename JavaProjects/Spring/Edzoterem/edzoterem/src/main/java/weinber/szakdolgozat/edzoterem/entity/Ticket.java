package weinber.szakdolgozat.edzoterem.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ticket")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "checking_limit")
	private int checkingLimit;
	
	@Column(name = "student_price")
	private int studentPrice;
	
	@Column(name = "normal_price")
	private int normalPrice;
	
	
	@OneToMany(mappedBy = "ticket",
			cascade = { CascadeType.PERSIST,CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	private List<User> users;
	

	
	public Ticket() {
		
	}
	
	public void removeUser(User user) {
		if(users.contains(user)) {
			users.remove(user);
		}
	}
	
	
	public void addUser(User user) {
		if(user != null) {
			users.add(user);
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
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
	
	
	

}
