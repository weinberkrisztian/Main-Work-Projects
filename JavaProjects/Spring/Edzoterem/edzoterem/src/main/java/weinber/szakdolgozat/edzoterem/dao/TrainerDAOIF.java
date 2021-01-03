package weinber.szakdolgozat.edzoterem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import weinber.szakdolgozat.edzoterem.entity.Trainer;
import weinber.szakdolgozat.edzoterem.entity.TrainingClass;
import weinber.szakdolgozat.edzoterem.entity.User;
import weinber.szakdolgozat.edzoterem.user.CrmTrainer;

public interface TrainerDAOIF{

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
	 * Saves the Trainer, if it already exist than it updates it with the new properties.
	 * @param The Trainer to be updated.
	 * @return The updated Trainer.
	 */
    public Trainer save(Trainer trainer);
    
    
    /**
     * Deleting the Trainer.
     * @param trainer The Trainer to be deleted.
     */
    public void delete(Trainer trainer);

}
