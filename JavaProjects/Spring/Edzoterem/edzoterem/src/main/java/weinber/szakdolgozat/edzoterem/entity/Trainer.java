package weinber.szakdolgozat.edzoterem.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name= "trainer")
public class Trainer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@OneToOne(mappedBy = "trainer" , 
			cascade = CascadeType.ALL)
	private TrainingClass trainingClass;
	
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "image_url")
	private String imageUrl;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "description")
	private String description;
	
	
	public Trainer() {
		
	}

	public Trainer(String email) {
		this.email = email;
	}
	public Trainer( String imageUrl, String firstName, String lastName, String email, String description) {
		this.imageUrl = imageUrl;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public TrainingClass getTrainingClass() {
		return trainingClass;
	}

	public void setTrainingClass(TrainingClass trainingClass) {
		this.trainingClass = trainingClass;
	}


	
	
	
	
}
