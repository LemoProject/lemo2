package de.lemo.persistence.umed.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.umed.entities.Person;
import de.lemo.persistence.umed.entities.PersonExt;


@StaticMetamodel(PersonExt.class)
public class PersonExt_{

	public static volatile SingularAttribute<PersonExt, Long> id;
	public static volatile SingularAttribute<PersonExt, Person> person;
	public static volatile SingularAttribute<PersonExt, String> attr;
	public static volatile SingularAttribute<PersonExt, String> value;

}

