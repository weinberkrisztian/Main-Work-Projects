package weinber.szakdolgozat.edzoterem.user;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import weinber.szakdolgozat.edzoterem.entity.Appointment;
import weinber.szakdolgozat.edzoterem.entity.Ticket;
import weinber.szakdolgozat.edzoterem.entity.Trainer;
import weinber.szakdolgozat.edzoterem.entity.TrainingClass;
import weinber.szakdolgozat.edzoterem.validation.EmailValidation;
import weinber.szakdolgozat.edzoterem.validation.MatchingPasswordValidation;

public class CrmTrainingClass {

	@NotNull(message = " kötelező!")
	@Size(min = 1, message = " kötelező!")
	private String name;

	@NotNull(message = " kötelező!")
	private String description;
	
	private String imageUrl;
	
	private Integer trainerId;
	
	private Trainer trainer;
	

	public CrmTrainingClass() {

	}
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTrainerId() {
		return trainerId;
	}
	public void setTrainerId(Integer trainerId) {
		this.trainerId = trainerId;
	}
	public Trainer getTrainer() {
		return trainer;
	}
	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	
}
