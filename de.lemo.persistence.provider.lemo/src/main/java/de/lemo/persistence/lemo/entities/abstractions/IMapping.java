package de.lemo.persistence.lemo.entities.abstractions;


public interface IMapping {

	long getId();
	
	void setId(long id);

	boolean equals(IMapping o);
	
}
