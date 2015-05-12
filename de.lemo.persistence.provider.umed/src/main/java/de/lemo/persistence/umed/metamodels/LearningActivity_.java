package de.lemo.persistence.umed.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.umed.entities.LearningActivity;
import de.lemo.persistence.umed.entities.LearningContext;
import de.lemo.persistence.umed.entities.LearningObject;
import de.lemo.persistence.umed.entities.Person;

@StaticMetamodel(LearningActivity.class)
public class LearningActivity_{

	private static volatile SingularAttribute<LearningActivity, Long> id;
	private static volatile SingularAttribute<LearningActivity, LearningContext> learningContext;
	private static volatile SingularAttribute<LearningActivity, Person> person;
	private static volatile SingularAttribute<LearningActivity, LearningObject> learningObject;
	private static volatile SingularAttribute<LearningActivity, Long> time;
	private static volatile SingularAttribute<LearningActivity, String> action;
	private static volatile SingularAttribute<LearningActivity, LearningActivity> reference;
	private static volatile SingularAttribute<LearningActivity, String> info;
}
