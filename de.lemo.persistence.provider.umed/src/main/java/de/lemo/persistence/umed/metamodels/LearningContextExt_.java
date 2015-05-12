package de.lemo.persistence.umed.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.umed.entities.LearningContext;
import de.lemo.persistence.umed.entities.LearningContextExt;

@StaticMetamodel(LearningContextExt.class)
public class LearningContextExt_{

	public static volatile SingularAttribute<LearningContextExt, Long> id;
	public static volatile SingularAttribute<LearningContextExt, LearningContext> learningContext;
	public static volatile SingularAttribute<LearningContextExt, String> value;
	public static volatile SingularAttribute<LearningContextExt, String> attr;

}

