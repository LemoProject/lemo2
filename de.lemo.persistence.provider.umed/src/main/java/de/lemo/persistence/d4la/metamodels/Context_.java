package de.lemo.persistence.d4la.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.d4la.entities.Context;

@StaticMetamodel(Context.class)
public class Context_{
	
	public static volatile SingularAttribute<Context, Long> id;
	public static volatile SingularAttribute<Context, String> name;
	public static volatile SingularAttribute<Context, Context> parent;
	
}
