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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "appointment")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "day")
	@Temporal(TemporalType.DATE )
	private Date day;

	@Column(name = "time_from_id")
	private int timeFromId;
	
    @Column(name = "time_from")
    @Temporal(TemporalType.TIME )
    private Date timeFrom;
    
    @Column(name = "time_to")
    @Temporal(TemporalType.TIME )
    private Date timeTo;
    
	@Column(name = "day_txt")
	private String dayTxt;
	
	@Column(name = "max_number")
	private int maxPerson;
	
	@Column(name = "current_number")
	private int currentPerson;
	
	@Column(name = "order_number")
	private int orderNumber;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "user_appointment", 
	joinColumns = @JoinColumn(name = "appointment_id"), 
	inverseJoinColumns = {
	@JoinColumn(name = "user_id")})
	private Collection<User> user;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "training_class_id" )
	private TrainingClass trainingClass;
	
	public Appointment() {
		
	}

	
	
	public Appointment(int id, Date day, int timeFromId, Date timeFrom, Date timeTo, String dayTxt, int maxPerson,
			int currentPerson, int orderNumber, Collection<User> user, TrainingClass trainingClass) {
		super();
		this.id = id;
		this.day = day;
		this.timeFromId = timeFromId;
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
		this.dayTxt = dayTxt;
		this.maxPerson = maxPerson;
		this.currentPerson = currentPerson;
		this.orderNumber = orderNumber;
		this.user = user;
		this.trainingClass = trainingClass;
	}



	public void removeUser(User theUser) {
		if(theUser!=null)
		user.remove(theUser);
	}


	public void addUser(User theUser) {
		
		user.add(theUser);
		
	}


	public int getCurrentPerson() {
		return currentPerson;
	}

	public void setCurrentPerson(int currentPerson) {
		this.currentPerson = currentPerson;
	}

	public int getMaxPerson() {
		return maxPerson;
	}


	public void setMaxPerson(int maxPerson) {
		this.maxPerson = maxPerson;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}




	public Date getDay() {
		return day;
	}



	public void setDay(Date day) {
		this.day = day;
	}



	public Date getTimeFrom() {
		return timeFrom;
	}



	public void setTimeFrom(Date timeFrom) {
		this.timeFrom = timeFrom;
	}



	public Date getTimeTo() {
		return timeTo;
	}



	public void setTimeTo(Date timeTo) {
		this.timeTo = timeTo;
	}



	public Collection<User> getUser() {
		return user;
	}


	public void setUser(Collection<User> user) {
		this.user = user;
	}


	public TrainingClass getTrainingClass() {
		return trainingClass;
	}


	public void setTrainingClass(TrainingClass trainingClass) {
		this.trainingClass = trainingClass;
	}



	public String getDayTxt() {
		return dayTxt;
	}



	public void setDayTxt(String dayTxt) {
		this.dayTxt = dayTxt;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}



	public int getTimeFromId() {
		return timeFromId;
	}



	public void setTimeFromId(int timeFromId) {
		this.timeFromId = timeFromId;
	}


	
	
}
