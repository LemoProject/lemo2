package de.lemo.persistence.entities.abstractions;

import de.lemo.persistence.entities.Course;
import de.lemo.persistence.entities.User;

public interface ILog extends Comparable<ILog>  {

	long getId();

	Long getTimestamp();

	User getUser();

	Course getCourse();
	
	ILearningObject getLearning();

	void setId(long id);
	
	long getPrefix();
	
	String getType();
	
	Long getLearningId();
	
}
