package de.lemo.persistence.lemo.entities.abstractions;

/**
 * Interface for mapping class to control equality
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
public interface IMappingClass {

	long getId();
	
	void setId(long id);

	boolean equals(IMappingClass o);

}
