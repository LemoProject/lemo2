package de.lemo.persistence.d4la.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.d4la.entities.Activity;
import de.lemo.persistence.d4la.entities.ActivityExt;


@StaticMetamodel(ActivityExt.class)
public class ActivityExt_{

	public static volatile SingularAttribute<ActivityExt, Long> id;
	public static volatile SingularAttribute<ActivityExt, Activity> learningActivity;
	public static volatile SingularAttribute<ActivityExt, String> value;
	public static volatile SingularAttribute<ActivityExt, String> attr;
	
}

