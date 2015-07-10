package de.lemo.persistence.d4la.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.d4la.entities.Object;
import de.lemo.persistence.d4la.entities.ObjectExt;


@StaticMetamodel(ObjectExt.class)
public class LearningObjectExt_{

	public static volatile SingularAttribute<ObjectExt, Long> id;
	public static volatile SingularAttribute<ObjectExt, Object> learningObject;
	public static volatile SingularAttribute<ObjectExt, String> value;
	public static volatile SingularAttribute<ObjectExt, String> attr;
}

