package de.lemo.data.dm;

import java.util.List;

import de.lemo.data.dm.entities.ED_LearningActivity;
import de.lemo.data.dm.entities.ED_LearningContext;
import de.lemo.data.dm.entities.ED_Path;
import de.lemo.data.dm.entities.ED_Person;

public interface IDataProvider {
	
	ED_LearningContext getLearningContext(Long id);
	
	List<ED_LearningContext> getLearningContexts(List<Long> ids);
	
	List<ED_LearningContext> getLearningContextsPerson(Long person);
	
	List<ED_Person> getPersons(Long context, String role);
	
	List<ED_LearningActivity> getLearningActivities(Long person);
	
	List<ED_LearningActivity> getLearningActivities(Long context, String action);
	
	List<ED_LearningActivity> getLearningActivities(Long context, Long person, List<Long> objects);
	
	List<ED_Path> getPaths(Long context);
	
	

}