package de.lemo.persistence.umed.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.umed.entities.Person;

@StaticMetamodel(Person.class)
public class Person_{
	


	private static volatile SingularAttribute<Person, Long> id;
	private static volatile SingularAttribute<Person, String> name;
	
}
