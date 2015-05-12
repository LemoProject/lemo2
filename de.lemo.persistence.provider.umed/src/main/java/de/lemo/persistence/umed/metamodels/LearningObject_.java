package de.lemo.persistence.umed.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.umed.entities.LearningObject;

@StaticMetamodel(LearningObject.class)
public class LearningObject_{

	public static volatile SingularAttribute<LearningObject, Long> id;
	public static volatile SingularAttribute<LearningObject, String> name;
	public static volatile SingularAttribute<LearningObject, String> type;
	public static volatile SingularAttribute<LearningObject, LearningObject> parent;
	
	
}
