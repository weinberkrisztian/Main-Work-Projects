package weinber.szakdolgozat.edzoterem.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weinber.szakdolgozat.edzoterem.dao.TrainingClassDAOIF;
import weinber.szakdolgozat.edzoterem.entity.Trainer;
import weinber.szakdolgozat.edzoterem.entity.TrainingClass;
import weinber.szakdolgozat.edzoterem.user.CrmTrainer;
import weinber.szakdolgozat.edzoterem.user.CrmTrainingClass;


@Service
public class TrainingClassService implements TrainingClassServiceIF{

	@Autowired
	private TrainingClassDAOIF trainingClassDao;
	
	@Override
	@Transactional
	public TrainingClass findByName(String name) {
		TrainingClass trainingClass=trainingClassDao.findByName(name);
		return trainingClass;
	}

	@Override
	@Transactional
	public List<TrainingClass> findAll() {
		List<TrainingClass> trainingClassList=trainingClassDao.findAll();
		return trainingClassList;
	}

	@Override
	public TrainingClass findById(long id) {
		TrainingClass trainingClass=trainingClassDao.findById(id);
		return trainingClass;
	}

	@Override
	public List<TrainingClass> findAllWithNoTrainer() {
		List<TrainingClass> trainingClassList=trainingClassDao.findAll();
		List<TrainingClass> trainingClassWithNoTrainerList=new ArrayList<TrainingClass>();
		for (int i = 0; i < trainingClassList.size(); i++) {
			if(trainingClassList.get(i).getTrainer() == null) {
				trainingClassWithNoTrainerList.add(trainingClassList.get(i));
			}
		}
		
		return trainingClassWithNoTrainerList;
	}

	@Override
	public TrainingClass convertCrmTrainingClassToTrainingClass(@Valid CrmTrainingClass crmTrainingClass) {
		TrainingClass trainingClass=new TrainingClass();
		
		trainingClass.setDescription(crmTrainingClass.getDescription());
		trainingClass.setName(crmTrainingClass.getName());
		if(!crmTrainingClass.getImageUrl().equals("")) {
		trainingClass.setImageUrl(crmTrainingClass.getImageUrl());
		}
		if(crmTrainingClass.getTrainer() !=null) {
			trainingClass.setTrainer(crmTrainingClass.getTrainer());
		}
		
		
		return trainingClass;
	}

	@Override
	@Transactional
	public TrainingClass save(CrmTrainingClass crmTrainingClass,TrainingClass trainingClass) {
		
		
		
		
		if(crmTrainingClass != null) {
			if(trainingClass == null) {
				trainingClass=new TrainingClass();
				trainingClass=convertCrmTrainingClassToTrainingClass(trainingClass, crmTrainingClass);
				Trainer theTrainer=crmTrainingClass.getTrainer();
				if(theTrainer != null) {
					trainingClass.setTrainer(theTrainer);
					trainingClass.getTrainer().setTrainingClass(trainingClass);
				}
			}else {
				trainingClass=convertCrmTrainingClassToTrainingClass(trainingClass, crmTrainingClass);
				Trainer theTrainer=null;
					if(trainingClass.getTrainer() != null) {
						theTrainer=trainingClass.getTrainer();
					}
					
					
					if(crmTrainingClass.getTrainer()== null && theTrainer != null) {
						trainingClass.getTrainer().setTrainingClass(null);
						trainingClass.setTrainer(null);
					}else if(trainingClass.getTrainer() == null && crmTrainingClass.getTrainer()!= null) {
						trainingClass.setTrainer(crmTrainingClass.getTrainer());
						trainingClass.getTrainer().setTrainingClass(trainingClass);
					}else if(trainingClass.getTrainer() == null && crmTrainingClass.getTrainer()== null) {
						
					}
					else {
						trainingClass.getTrainer().setTrainingClass(null);
						trainingClass.setTrainer(crmTrainingClass.getTrainer());
						trainingClass.getTrainer().setTrainingClass(trainingClass);
					}
					
			}
		}
		
		TrainingClass theUpdatedTrainingClass = trainingClassDao.save(trainingClass);
		return theUpdatedTrainingClass;
		
	}

	@Override
	public CrmTrainingClass convertTrainingClassToCrmTrainingClass(TrainingClass theTrainingClass) {
		CrmTrainingClass crmTrainingClass=new CrmTrainingClass();
		
		crmTrainingClass.setDescription(theTrainingClass.getDescription());
		crmTrainingClass.setName(theTrainingClass.getName());
		if(!theTrainingClass.getImageUrl().equals("")) {
		crmTrainingClass.setImageUrl(theTrainingClass.getImageUrl());
		}
		if(theTrainingClass.getTrainer() !=null) {
			crmTrainingClass.setTrainer(theTrainingClass.getTrainer());
		}
		
		
		return crmTrainingClass;
	}

	@Override
	public TrainingClass convertCrmTrainingClassToTrainingClass(TrainingClass theUpdatedTrainingClass,
			CrmTrainingClass crmTrainingClass) {
		theUpdatedTrainingClass.setDescription(crmTrainingClass.getDescription());
		theUpdatedTrainingClass.setName(crmTrainingClass.getName());
		if(!crmTrainingClass.getImageUrl().equals("")) {
		theUpdatedTrainingClass.setImageUrl(crmTrainingClass.getImageUrl());
		}
		return theUpdatedTrainingClass;
	}

	@Override
	@Transactional
	public void delete(TrainingClass theTrainingClass) {
		trainingClassDao.delete(theTrainingClass);
	}

}
