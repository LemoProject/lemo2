package de.lemo.persistence.lemo.entities.abstractions;

import de.lemo.persistence.lemo.entities.Course;
import de.lemo.persistence.lemo.entities.LearningObj;
import de.lemo.persistence.lemo.entities.User;


/**
 * Interface for the association between the user and a rated object
 * @author Sebastian Schwarzrock
 */
public interface ILearningUserAssociation {

	Course getCourse();
	
	User getUser();
	
	Double getFinalGrade();
	
	LearningObj getLearning();

}
