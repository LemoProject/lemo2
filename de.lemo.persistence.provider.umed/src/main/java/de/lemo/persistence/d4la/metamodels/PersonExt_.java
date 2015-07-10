package de.lemo.persistence.d4la.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.d4la.entities.Person;
import de.lemo.persistence.d4la.entities.PersonExt;


@StaticMetamodel(PersonExt.class)
public class PersonExt_{

	public static volatile SingularAttribute<PersonExt, Long> id;
	public static volatile SingularAttribute<PersonExt, Person> person;
	public static volatile SingularAttribute<PersonExt, String> attr;
	public static volatile SingularAttribute<PersonExt, String> value;

}

