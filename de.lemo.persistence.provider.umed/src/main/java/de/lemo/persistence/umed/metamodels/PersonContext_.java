package de.lemo.persistence.umed.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.umed.entities.LearningContext;
import de.lemo.persistence.umed.entities.Person;
import de.lemo.persistence.umed.entities.PersonContext;


@StaticMetamodel(PersonContext.class)
public class PersonContext_{
	
	private static volatile SingularAttribute<PersonContext, Long> id;
	private static volatile SingularAttribute<PersonContext, LearningContext> learningContext;
	private static volatile SingularAttribute<PersonContext, Person> person;
	private static volatile SingularAttribute<PersonContext, String> role;
	
}
