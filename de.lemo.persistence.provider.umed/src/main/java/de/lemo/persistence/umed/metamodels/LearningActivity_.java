package de.lemo.persistence.umed.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.umed.entities.LearningActivity;
import de.lemo.persistence.umed.entities.LearningContext;
import de.lemo.persistence.umed.entities.LearningObject;
import de.lemo.persistence.umed.entities.Person;

@StaticMetamodel(LearningActivity.class)
public class LearningActivity_{

	public static volatile SingularAttribute<LearningActivity, Long> id;
	public static volatile SingularAttribute<LearningActivity, LearningContext> learningContext;
	public static volatile SingularAttribute<LearningActivity, Person> person;
	public static volatile SingularAttribute<LearningActivity, LearningObject> learningObject;
	public static volatile SingularAttribute<LearningActivity, Long> time;
	public static volatile SingularAttribute<LearningActivity, String> action;
	public static volatile SingularAttribute<LearningActivity, LearningActivity> reference;
	public static volatile SingularAttribute<LearningActivity, String> info;
}
