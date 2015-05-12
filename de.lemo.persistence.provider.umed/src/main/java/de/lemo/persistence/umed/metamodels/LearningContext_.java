package de.lemo.persistence.umed.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.umed.entities.LearningContext;

@StaticMetamodel(LearningContext.class)
public class LearningContext_{
	
	public static volatile SingularAttribute<LearningContext, Long> id;
	public static volatile SingularAttribute<LearningContext, String> name;
	public static volatile SingularAttribute<LearningContext, LearningContext> parent;
	
}
