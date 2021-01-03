package weinber.szakdolgozat.edzoterem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weinber.szakdolgozat.edzoterem.dao.TrainerDAOIF;
import weinber.szakdolgozat.edzoterem.entity.Trainer;
import weinber.szakdolgozat.edzoterem.entity.TrainingClass;
import weinber.szakdolgozat.edzoterem.user.CrmTrainer;
import weinber.szakdolgozat.edzoterem.user.CrmUser;

@Service
public class TrainerService implements TrainerServiceIF {

	private TrainerDAOIF trainerDAOIF;
	
	@Autowired
	public TrainerService(TrainerDAOIF trainerDAOIF) {
		this.trainerDAOIF=trainerDAOIF; 
	}
	
	@Override
	@Transactional
	public List<Trainer> findAll(){
		return trainerDAOIF.findAll();
	}

	@Override
	public Trainer findById(int trainerId) {
		return trainerDAOIF.findById(trainerId);
	}

	@Override
	public Trainer findByEmail(String email) {
		return trainerDAOIF.findByEmail(email);
	}

	@Override
	public CrmTrainer convertTrainerToCrmTrainer(Trainer existingTrainer) {
		CrmTrainer crmTrainer=new CrmTrainer();
		
		crmTrainer.setDescription(existingTrainer.getDescription());
		crmTrainer.setEmail(existingTrainer.getEmail());
		crmTrainer.setFirstName(existingTrainer.getFirstName());
		crmTrainer.setLastName(existingTrainer.getLastName());
		if(!existingTrainer.getImageUrl().equals("")) {
		crmTrainer.setImageUrl(existingTrainer.getImageUrl());
		}
		
		if(existingTrainer.getTrainingClass() !=null) {
			crmTrainer.setTrainingClass(existingTrainer.getTrainingClass());
		}
		
		
		return crmTrainer;
	}

	@Override
	public Trainer convertCrmTrainerToTrainer(CrmTrainer crmTrainer) {
		Trainer trainer=new Trainer();
		
		trainer.setDescription(crmTrainer.getDescription());
		trainer.setEmail(crmTrainer.getEmail());
		trainer.setFirstName(crmTrainer.getFirstName());
		trainer.setLastName(crmTrainer.getLastName());
		if(!crmTrainer.getImageUrl().equals("")) {
		trainer.setImageUrl(crmTrainer.getImageUrl());
		}
		if(crmTrainer.getTrainingClass() !=null) {
			trainer.setTrainingClass(crmTrainer.getTrainingClass());
		}
		
		
		return trainer;
	}
	
	@Override
	public Trainer convertCrmTrainerToTrainer(Trainer trainer,CrmTrainer crmTrainer) {
		
		trainer.setDescription(crmTrainer.getDescription());
		trainer.setEmail(crmTrainer.getEmail());
		trainer.setFirstName(crmTrainer.getFirstName());
		trainer.setLastName(crmTrainer.getLastName());
		if(!crmTrainer.getImageUrl().equals("")) {
		trainer.setImageUrl(crmTrainer.getImageUrl());
		}
		
		return trainer;
	}
	
	@Override
	@Transactional
	public Trainer save(CrmTrainer crmTrainer,Trainer trainer) {
// Ezzel megegyező logikára épül a TrainingClass módosítása

		// if we want to modify or create a new Trainer
		if(crmTrainer != null) {
		// null ha új edzőt hozunk létre
		if(trainer == null) {
			trainer=new Trainer();
			// beállítjuk az új adatokat(kívéve TrainingClass)
			trainer=convertCrmTrainerToTrainer(trainer, crmTrainer);
			TrainingClass theTrainingClass=crmTrainer.getTrainingClass();
			// ha trainingClasst is adtunk neki a UI on
			if(theTrainingClass != null) {
				trainer.setTrainingClass(theTrainingClass);
				trainer.getTrainingClass().setTrainer(trainer);
			}
		}else {
			
			trainer=convertCrmTrainerToTrainer(trainer, crmTrainer);
			TrainingClass theTrainingClass=null;
			
			if(trainer.getTrainingClass() != null) {
				theTrainingClass=trainer.getTrainingClass();
			}
			
			// ha a meglévő Edzőnek már volt TrainingClassa de szeretnénk nullozni
			if(crmTrainer.getTrainingClass()== null && theTrainingClass != null) {
				trainer.getTrainingClass().setTrainer(null);
				trainer.setTrainingClass(null);
			}else if(trainer.getTrainingClass() == null && crmTrainer.getTrainingClass()!= null) {
				trainer.setTrainingClass(crmTrainer.getTrainingClass());
				trainer.getTrainingClass().setTrainer(trainer);
			}else if(trainer.getTrainingClass() == null && crmTrainer.getTrainingClass()== null){
			}else{
				trainer.getTrainingClass().setTrainer(null);
				trainer.setTrainingClass(crmTrainer.getTrainingClass());
				trainer.getTrainingClass().setTrainer(trainer);
			}
				
		}
		}
		


		
		Trainer theUpdatedTrainer = trainerDAOIF.save(trainer);
		
		
		
		return theUpdatedTrainer;
	}

	@Override
	public List<Trainer> findAllWithNoTraningClass() {
		List<Trainer> trainerList=trainerDAOIF.findAll();
		List<Trainer> trainerWithNoTrainingClassList=new ArrayList<Trainer>();
		for (int i = 0; i < trainerList.size(); i++) {
			if(trainerList.get(i).getTrainingClass() == null) {
				trainerWithNoTrainingClassList.add(trainerList.get(i));
			}
		}
		
		return trainerWithNoTrainingClassList;
	}

	@Override
	@Transactional
	public void delete(Trainer trainer) {
		trainerDAOIF.delete(trainer);
		
	}
	
	
}
