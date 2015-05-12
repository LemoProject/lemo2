package de.lemo.persistence.umed.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.umed.entities.LearningActivity;
import de.lemo.persistence.umed.entities.LearningActivityExt;


@StaticMetamodel(LearningActivityExt.class)
public class LearningActivityExt_{

	private static volatile SingularAttribute<LearningActivityExt, Long> id;
	private static volatile SingularAttribute<LearningActivityExt, LearningActivity> learningActivity;
	private static volatile SingularAttribute<LearningActivityExt, String> value;
	private static volatile SingularAttribute<LearningActivityExt, String> attr;
	
}

