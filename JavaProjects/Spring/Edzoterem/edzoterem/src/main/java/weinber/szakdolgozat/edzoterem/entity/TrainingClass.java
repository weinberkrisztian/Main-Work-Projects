package weinber.szakdolgozat.edzoterem.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "training_class")
public class TrainingClass {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "trainer_id" )
	private Trainer trainer;
	
	@OneToMany(mappedBy = "trainingClass" , 
			cascade = { CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,})
	private List<Appointment> appointment;
	
	@Column(name = "name")
	private String name;
	
	@Column(name="image_url")
	private String imageUrl;
	
	@Column(name = "description")
	private String description;

	
	
	
	public TrainingClass() {
		
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Trainer getTrainer() {
		return trainer;
	}


	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}


	public List<Appointment> getApppointment() {
		return appointment;
	}


	public void setApppointment(List<Appointment> appointment) {
		this.appointment = appointment;
	}


	public void setId(long id) {
		this.id = id;
	}
	
	
}
