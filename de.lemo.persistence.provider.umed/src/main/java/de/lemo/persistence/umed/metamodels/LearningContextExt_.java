package de.lemo.persistence.umed.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.umed.entities.LearningContext;
import de.lemo.persistence.umed.entities.LearningContextExt;

@StaticMetamodel(LearningContextExt.class)
public class LearningContextExt_{

	private static volatile SingularAttribute<LearningContextExt, Long> id;
	private static volatile SingularAttribute<LearningContextExt, LearningContext> learningContext;
	private static volatile SingularAttribute<LearningContextExt, String> value;
	private static volatile SingularAttribute<LearningContextExt, String> attr;

}

