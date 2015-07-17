package de.lemo.persistence.d4la.metamodels;


import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.d4la.entities.Context;
import de.lemo.persistence.d4la.entities.Object;
import de.lemo.persistence.d4la.entities.ObjectContext;

@StaticMetamodel(ObjectContext.class)
public class ObjectContext_{
	
	public static volatile SingularAttribute<ObjectContext, Long> id;
	public static volatile SingularAttribute<ObjectContext, Context> learningContext;
	public static volatile SingularAttribute<ObjectContext, Object> learningObject;
}
