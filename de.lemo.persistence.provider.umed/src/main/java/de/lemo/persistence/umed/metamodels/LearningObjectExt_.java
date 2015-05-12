package de.lemo.persistence.umed.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.umed.entities.LearningObject;
import de.lemo.persistence.umed.entities.LearningObjectExt;


@StaticMetamodel(LearningObjectExt.class)
public class LearningObjectExt_{

	public static volatile SingularAttribute<LearningObjectExt, Long> id;
	public static volatile SingularAttribute<LearningObjectExt, LearningObject> learningObject;
	public static volatile SingularAttribute<LearningObjectExt, String> value;
	public static volatile SingularAttribute<LearningObjectExt, String> attr;
}

