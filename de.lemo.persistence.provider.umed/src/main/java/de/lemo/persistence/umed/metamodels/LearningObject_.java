package de.lemo.persistence.umed.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.umed.entities.LearningObject;

@StaticMetamodel(LearningObject.class)
public class LearningObject_{

	private static volatile SingularAttribute<LearningObject, Long> id;
	private static volatile SingularAttribute<LearningObject, String> name;
	private static volatile SingularAttribute<LearningObject, String> type;
	private static volatile SingularAttribute<LearningObject, LearningObject> parent;
	
	
}
