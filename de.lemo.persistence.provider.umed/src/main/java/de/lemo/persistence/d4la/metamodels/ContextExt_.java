package de.lemo.persistence.d4la.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.d4la.entities.Context;
import de.lemo.persistence.d4la.entities.ContextExt;

@StaticMetamodel(ContextExt.class)
public class ContextExt_{

	public static volatile SingularAttribute<ContextExt, Long> id;
	public static volatile SingularAttribute<ContextExt, Context> learningContext;
	public static volatile SingularAttribute<ContextExt, String> value;
	public static volatile SingularAttribute<ContextExt, String> attr;

}

