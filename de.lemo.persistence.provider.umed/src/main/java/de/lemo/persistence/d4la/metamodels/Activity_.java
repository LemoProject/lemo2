package de.lemo.persistence.d4la.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.d4la.entities.Activity;
import de.lemo.persistence.d4la.entities.Context;
import de.lemo.persistence.d4la.entities.Object;
import de.lemo.persistence.d4la.entities.Person;

@StaticMetamodel(Activity.class)
public class Activity_{

	public static volatile SingularAttribute<Activity, Long> id;
	public static volatile SingularAttribute<Activity, Context> learningContext;
	public static volatile SingularAttribute<Activity, Person> person;
	public static volatile SingularAttribute<Activity, Object> learningObject;
	public static volatile SingularAttribute<Activity, Long> time;
	public static volatile SingularAttribute<Activity, String> action;
	public static volatile SingularAttribute<Activity, Activity> reference;
	public static volatile SingularAttribute<Activity, String> info;
}
