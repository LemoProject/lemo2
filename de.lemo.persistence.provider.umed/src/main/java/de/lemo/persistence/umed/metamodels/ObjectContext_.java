package de.lemo.persistence.umed.metamodels;


import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.umed.entities.LearningObject;
import de.lemo.persistence.umed.entities.LearningContext;
import de.lemo.persistence.umed.entities.ObjectContext;

@StaticMetamodel(ObjectContext.class)
public class ObjectContext_{
	
	public static volatile SingularAttribute<ObjectContext, Long> id;
	public static volatile SingularAttribute<ObjectContext, LearningContext> learningContext;
	public static volatile SingularAttribute<ObjectContext, LearningObject> learningObject;
}
