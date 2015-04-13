package de.lemo.persistence.entities.abstractions;

import de.lemo.persistence.entities.Course;
import de.lemo.persistence.entities.LearningObj;
import de.lemo.persistence.entities.User;


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
