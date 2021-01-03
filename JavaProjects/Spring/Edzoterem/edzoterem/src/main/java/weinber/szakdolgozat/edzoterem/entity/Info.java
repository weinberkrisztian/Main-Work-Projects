package weinber.szakdolgozat.edzoterem.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "info")
public class Info {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "user_id")
	private long userId;
	
	@Column(name = "check_in")
    @Temporal(TemporalType.TIME )
	private Date checkIn;
	
	@Column(name = "check_out")
    @Temporal(TemporalType.TIME )
	private Date checkOut;
	
	@Column(name = "app_date")
    @Temporal(TemporalType.DATE )
	private Date appDate;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "training_type")
	private String trainingType;
	
	@Column(name = "trainer_name")
	private String trainerName;
	
	@Column(name = "ticket_type")
	private String ticketType;

	@Column(name = "ticket_start")
	@Temporal(TemporalType.DATE )
	private Date ticketStart;
	
	@Column(name = "daily_ticket")
	private String dailyTicket;
	
	public Info() {
		
	}

	public Info(long userId, Date checkIn, Date checkOut, Date appDate, String userName, String trainingType,
			String trainerName, String ticketType, Date ticketStart, String dailyTicket) {
		this.userId = userId;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.appDate = appDate;
		this.userName = userName;
		this.trainingType = trainingType;
		this.trainerName = trainerName;
		this.ticketType = ticketType;
		
		if(ticketStart != null)
		this.ticketStart = ticketStart;
		
		this.dailyTicket=dailyTicket;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	
	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public Date getTicketStart() {
		return ticketStart;
	}

	public void setTicketStart(Date ticketStart) {
		this.ticketStart = ticketStart;
	}

	public String getDailyTicket() {
		return dailyTicket;
	}

	public void setDailyTicket(String dailyTicket) {
		this.dailyTicket = dailyTicket;
	}
	
	
	

}
