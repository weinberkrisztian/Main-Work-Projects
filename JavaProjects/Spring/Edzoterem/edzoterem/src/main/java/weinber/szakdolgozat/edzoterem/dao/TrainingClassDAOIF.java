package weinber.szakdolgozat.edzoterem.dao;

import java.util.List;

import weinber.szakdolgozat.edzoterem.entity.TrainingClass;
import weinber.szakdolgozat.edzoterem.user.CrmTrainingClass;

public interface TrainingClassDAOIF {

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
	 * Saves the TrainingClass, if it already exist than it updates it with the new properties.
	 * @param The TrainingClass to be updated.
	 * @return The updated TrainingClass.
	 */
	public TrainingClass save(TrainingClass trainingClass);

    /**
     * Deleting the TrainingClass.
     * @param theTrainingClass The TrainingClass to be deleted.
     */
	public void delete(TrainingClass theTrainingClass);

}
