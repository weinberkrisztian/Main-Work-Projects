package weinber.szakdolgozat.edzoterem.service;

import java.util.List;

import javax.validation.Valid;

import weinber.szakdolgozat.edzoterem.entity.Trainer;
import weinber.szakdolgozat.edzoterem.entity.TrainingClass;
import weinber.szakdolgozat.edzoterem.user.CrmTrainingClass;

public interface TrainingClassServiceIF {
	
	/**
	 * Finds a TrainingClass with the given name.
	 * @param The name of the TrainingClass.
	 * @return The TrainingClass.
	 */
	public TrainingClass findByName(String name);
	
	/**
	 * Finds a TrainingClass with the given id.
	 * @param The id of the TrainingClass.
	 * @return The TrainingClass.
	 */
	public TrainingClass findById(long id);
	
	/**
	 * Lists all TrainingClass.
	 * @return The List of all TrainingClass.
	 */
	public List<TrainingClass> findAll();
	
    /**
     * Lists every TrainingClass with no Trainer.
     * @return A list of TrainingClasses with no Trainer.
     */
    public List<TrainingClass> findAllWithNoTrainer();
    
	/**
	 * Converts a CrmTrainingClass to an actual TrainingClass
	 * @param CrmTrainingClass The crmTrainingClass.
	 * @return A TrainingClass converted from a crmTrainingClass.
	 */
	public TrainingClass convertCrmTrainingClassToTrainingClass(@Valid CrmTrainingClass crmTrainingClass);
	
	/**
	 * Saves the TrainingClass, if it already exist than it updates it with the new properties from the UI.
	 * @param CrmTrainingClass The crmTrainingClass from the UI which holds the new data.
	 * @param The TrainingClass to be updated.
	 * @return The updated TrainingClass.
	 */
	public TrainingClass save(CrmTrainingClass crmTrainingClass,TrainingClass trainingClass);

	/**
	 * Converts a TrainingClass into an CrmTrainingClass
	 * @param theTrainingClass The actual TrainingClass.
	 * @return A CrmTrainingClass converted from an actual TrainingClass.
	 */
	public CrmTrainingClass convertTrainingClassToCrmTrainingClass(TrainingClass theTrainingClass);

	/**
	 * Sets an actual TrainingClass properties with a crmTrainingClass.
	 * Except the Trainer porperty, it is handled in the save method.
	 * @param crmTrainingClass The crmTrainingClass.
	 * @param theUpdatedTrainingClass The existing TrainingClass.
	 * @return A TrainingClass converted from a crmTrainingClass.
	 */
	public TrainingClass convertCrmTrainingClassToTrainingClass(TrainingClass theUpdatedTrainingClass,
			CrmTrainingClass crmTrainingClass);

    /**
     * Deleting the TrainingClass.
     * @param theTrainingClass The TrainingClass to be deleted.
     */
	public void delete(TrainingClass theTrainingClass);
}
