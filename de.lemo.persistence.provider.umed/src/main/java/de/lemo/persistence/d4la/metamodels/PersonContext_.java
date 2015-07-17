package de.lemo.persistence.d4la.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.d4la.entities.Context;
import de.lemo.persistence.d4la.entities.Person;
import de.lemo.persistence.d4la.entities.PersonContext;


@StaticMetamodel(PersonContext.class)
public class PersonContext_{
	
	public static volatile SingularAttribute<PersonContext, Long> id;
	public static volatile SingularAttribute<PersonContext, Context> learningContext;
	public static volatile SingularAttribute<PersonContext, Person> person;
	public static volatile SingularAttribute<PersonContext, String> role;
	
}
