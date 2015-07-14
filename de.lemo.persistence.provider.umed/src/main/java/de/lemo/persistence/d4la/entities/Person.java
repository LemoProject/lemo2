package de.lemo.persistence.d4la.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.lemo.dataprovider.api.LA_Person;


/** This class represents the table user. */
@Entity
@Table(name = "D4LA_Person")
public class Person implements LA_Person{
	


	private long id;
	private String name;
	
	private Set<Activity> learningActivities= new HashSet<Activity>();
	private Set<PersonContext> personContexts = new HashSet<PersonContext>();
	
	
	/**
	 * @return the eventLogs
	 */
	@OneToMany(mappedBy="person")
	public Set<Activity> getLearningActivities() {
		return learningActivities;
	}

	/**
	 * @param learningActivities the learningActivities to set
	 */
	public void setLearningActivities(Set<Activity> learningActivities) {
		this.learningActivities = learningActivities;
	}
	
	public void addLearningActivity(Activity learningActivity)
	{
		this.learningActivities.add(learningActivity);
	}


	public boolean equals(final Person o) {
		if ((o.getId() == this.getId()) && (o instanceof Person)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}
	
	/**
	 * @return the id
	 */
	@Id
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the courseUsers
	 */
	@OneToMany(mappedBy="person")
	public Set<PersonContext> getPersonContexts() {
		return personContexts;
	}

	/**
	 * @param courseUsers the courseUsers to set
	 */
	public void setPersonContexts(Set<PersonContext> personContexts) {
		this.personContexts = personContexts;
	}
	
	public void addPersonContext(PersonContext personContext)
	{
		this.personContexts.add(personContext);
	}

	/**
	 * @return the name
	 */
	@Column(name="name")
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> extAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getExtAttribute(String attr) {
		// TODO Auto-generated method stub
		return null;
	}

}
