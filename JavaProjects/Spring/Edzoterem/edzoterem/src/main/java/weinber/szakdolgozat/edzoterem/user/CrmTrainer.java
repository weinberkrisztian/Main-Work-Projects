package weinber.szakdolgozat.edzoterem.user;

import java.io.File;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.Ticket;
import weinber.szakdolgozat.edzoterem.entity.TrainingClass;
import weinber.szakdolgozat.edzoterem.validation.EmailValidation;
import weinber.szakdolgozat.edzoterem.validation.MatchingPasswordValidation;

public class CrmTrainer {

	@NotNull(message = " kötelező!")
	@Size(min = 1, message = " kötelező!")
	private String firstName;

	@NotNull(message = " kötelező!")
	@Size(min = 1, message = " kötelező!")
	private String lastName;

	@EmailValidation
	@NotNull(message = " kötelező!")
	@Size(min = 1, message = " kötelező!")
	private String email;
	
	@NotNull(message = " kötelező!")
	private String description;
	
	private String imageUrl;
	
	
	private Integer trainingClassId;
	
	private TrainingClass trainingClass;
	

	public CrmTrainer() {

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


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getTrainingClassId() {
		return trainingClassId;
	}

	public void setTrainingClassId(Integer trainingClassId) {
		this.trainingClassId = trainingClassId;
	}


	
}
