package de.lemo.persistence.umed.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.umed.entities.LearningObject;
import de.lemo.persistence.umed.entities.LearningObjectExt;


@StaticMetamodel(LearningObjectExt.class)
public class LearningObjectExt_{

	private static volatile SingularAttribute<LearningObjectExt, Long> id;
	private static volatile SingularAttribute<LearningObjectExt, LearningObject> learningObject;
	private static volatile SingularAttribute<LearningObjectExt, String> value;
	private static volatile SingularAttribute<LearningObjectExt, String> attr;
}

