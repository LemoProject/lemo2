package de.lemo.persistence.umed.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.umed.entities.LearningActivity;
import de.lemo.persistence.umed.entities.LearningActivityExt;


@StaticMetamodel(LearningActivityExt.class)
public class LearningActivityExt_{

	public static volatile SingularAttribute<LearningActivityExt, Long> id;
	public static volatile SingularAttribute<LearningActivityExt, LearningActivity> learningActivity;
	public static volatile SingularAttribute<LearningActivityExt, String> value;
	public static volatile SingularAttribute<LearningActivityExt, String> attr;
	
}

