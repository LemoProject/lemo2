package de.lemo.persistence.d4la.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.lemo.dataprovider.api.LA_Activity;
import de.lemo.dataprovider.api.LA_Context;
import de.lemo.dataprovider.api.LA_Object;
import de.lemo.dataprovider.api.LA_Person;

/** This class represents the table lemo_learning_context. */
@Entity
@Table(name = "D4LA_Context ")
public class Context implements LA_Context{
	
	private long id;
	private String name;
	private Context parent;
	
	private Set<ObjectContext> objectContexts = new HashSet<ObjectContext>();
	private Set<Activity> learningActivities = new HashSet<Activity>();
	private Set<PersonContext> personContexts = new HashSet<PersonContext>();
	
	public boolean equals(final Context o) {
		if ((o.getId() == this.getId()) && (o instanceof Context)) {
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
	 * standard setter for the attribute course_resource
	 * 
	 * @param courseResource
	 *            a set of entries in the course_resource table which shows the resources in this course
	 */
	public void setObjectContexts(final Set<ObjectContext> objectContexts) {
		this.objectContexts = objectContexts;
	}

	/**
	 * standard getter for the attribute course_resource
	 * 
	 * @return a set of entries in the course_resource table which shows the resources in this course
	 */
	@OneToMany(mappedBy="context")
	public Set<ObjectContext> getObjectContexts() {
		return this.objectContexts;
	}

	/**
	 * standard add method for the attribute course_resource
	 * 
	 * @param courseResource
	 *            this entry of the course_resource table will be added to this course
	 */
	public void addObjectContext(final ObjectContext objectContext) {
		this.objectContexts.add(objectContext);
	}
	
		
	public void setLearningActivities(final Set<Activity> learningActivities) {
		this.learningActivities = learningActivities;
	}

	@OneToMany(mappedBy="context")
	public Set<Activity> getLearningActivities() {
		return this.learningActivities;
	}
	
	public void addLearningActivity(final Activity learningActivity) {
		this.learningActivities.add(learningActivity);
	}
	
	/**
	 * @return the courseUsers
	 */
	@OneToMany(mappedBy="context")
	public Set<PersonContext> getPersonContexts() {
		return this.personContexts;
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
	 * @return the parent
	 */
	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@JoinColumn(name="parent")
	public Context getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Context parent) {
		this.parent = parent;
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

	@Override
	public List<LA_Context> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LA_Object> getObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LA_Activity> getActivities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LA_Person> getStudents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LA_Person> getInstructors() {
		// TODO Auto-generated method stub
		return null;
	}
}
