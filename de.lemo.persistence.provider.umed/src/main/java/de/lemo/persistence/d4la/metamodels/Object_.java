package de.lemo.persistence.d4la.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.d4la.entities.Object;

@StaticMetamodel(Object.class)
public class Object_{

	public static volatile SingularAttribute<Object, Long> id;
	public static volatile SingularAttribute<Object, String> name;
	public static volatile SingularAttribute<Object, String> type;
	public static volatile SingularAttribute<Object, Object> parent;
	
	
}
