package weinber.szakdolgozat.edzoterem.service;

import java.util.List;

import javax.validation.Valid;

import weinber.szakdolgozat.edzoterem.entity.Trainer;
import weinber.szakdolgozat.edzoterem.user.CrmTrainer;

public interface TrainerServiceIF {

	/**
	 * Lists all Trainer.
	 * @return The List of all Trainer.
	 */
	public List<Trainer> findAll();
	
	/**
	 * Finds a Trainer with the given id.
	 * @param The id of the Trainer.
	 * @return The Trainer.
	 */
	Trainer findById(int id);
	
	/**
	 * Finds a Trainer with the given email.
	 * @param The email of the Trainer.
	 * @return The Trainer.
	 */
	public Trainer findByEmail(String email);

	/**
	 * Converts a Trainer into an CrmTrainer
	 * @param existingTrainer The actual Trainer.
	 * @return A CrmTrainer converted from an actual Trainer.
	 */
	public CrmTrainer convertTrainerToCrmTrainer(Trainer existingTrainer);
	
	/**
	 * Saves the Trainer, if it already exist than it updates it with the new properties from the UI.
	 * It examines how to set the actual TrainingClass that depends on the Trainer.
	 * @param CrmTrainer The crmTrainer from the UI which holds the new data.
	 * @param The Trainer to be updated.
	 * @return The updated Trainer.
	 */
    public Trainer save(CrmTrainer crmTrainer,Trainer trainer);
    
    /**
     * Lists every Trainer with no TrainingClass.
     * @return A list of Trainers with no TrainingClass.
     */
    public List<Trainer> findAllWithNoTraningClass();

	/**
	 * Converts a CrmTrainer to an actual Trainer.
	 * @param crmTrainer The crmTrainer.
	 * @return A Trainer converted from a crmTrainer.
	 */
	Trainer convertCrmTrainerToTrainer(CrmTrainer crmTrainer);

	/**
	 * Sets an actual Trainers properties with a crmTrainer.
	 * Except the TrainingClass porperty, it is handled in the save method.
	 * @param crmTrainer The crmTrainer.
	 * @param The existing Trainer.
	 * @return A Trainer converted from a crmTrainer.
	 */
	Trainer convertCrmTrainerToTrainer(Trainer trainer, CrmTrainer crmTrainer);

    /**
     * Deleting the Trainer.
     * @param trainer The Trainer to be deleted.
     */
    public void delete(Trainer trainer);
	
}
