package de.lemo.persistence.umed.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.umed.entities.Person;

@StaticMetamodel(Person.class)
public class Person_{
	

	public static volatile SingularAttribute<Person, Long> id;
	public static volatile SingularAttribute<Person, String> name;
	
}
