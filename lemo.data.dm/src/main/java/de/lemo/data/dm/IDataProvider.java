package de.lemo.data.dm;

import java.util.List;

import de.lemo.data.dm.entities.ED_LearningActivity;
import de.lemo.data.dm.entities.ED_LearningContext;
import de.lemo.data.dm.entities.ED_Path;
import de.lemo.data.dm.entities.ED_Person;

public interface IDataProvider {
	
	/**
	 * Returns a ED_LearningContext object
	 * 
	 * @param id	Id of the LearningContext
	 * @return
	 */
	ED_LearningContext getLearningContext(Long id);
	
	/**
	 * returns a list of ED_LearningContext objects
	 * 
	 * @param ids	List of ids of LearningContexts
	 * @return
	 */
	List<ED_LearningContext> getLearningContexts(List<Long> ids);
	
	/**
	 * Returns all ED_LearningContexts for a Person
	 * 
	 * @param person	Id of the Person
	 * @param role		Role of the Person within the LearningContexts
	 * @return
	 */
	List<ED_LearningContext> getLearningContextsPerson(Long person, String role);
	
	/**
	 * Returns all participating ED_Persons of a LearningContext
	 * 
	 * @param context	Id of the LearningContext
	 * @param role		Role of the Person(s) within the LearningContext
	 * @return
	 */
	List<ED_Person> getPersons(Long context, String role);
	
	/**
	 * Returns a list containing all ED_LearningActivities for a person
	 * 
	 * @param person	Id of the Person
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<ED_LearningActivity> getLearningActivities(Long person, Long startTime, Long endTime);
	
	/**
	 * Returns a list containing all ED_LearningActivities for a LearningContext
	 * 
	 * @param context	Id of the LearningContext
	 * @param action	Specific action of the LearningActivities
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<ED_LearningActivity> getLearningActivities(Long context, String action, Long startTime, Long endTime);
	
	/**
	 * Returns a list containing all ED_LearningActivities
	 * 
	 * @param context	Id of the LearningContext
	 * @param person	Id of the Person
	 * @param objects	List of LearningObject ids
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<ED_LearningActivity> getLearningActivities(Long context, Long person, List<Long> objects, Long startTime, Long endTime);
	
	/**
	 * Returns all ED_Paths within a LearningContext
	 * 
	 * @param context
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<ED_Path> getPaths(Long context, Long startTime, Long endTime);
	
	

}
