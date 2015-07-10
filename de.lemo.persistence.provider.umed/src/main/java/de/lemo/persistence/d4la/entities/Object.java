package de.lemo.persistence.d4la.entities;

import java.util.HashSet;
import java.util.Map;
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

/** 
 * This class represents the table lemo_learning_object. 
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "D4LA_Object")
public class Object{

	private long id;
	private String name;
	private String type;
	private Object parent;
	
	private Set<ObjectContext> objectContexts = new HashSet<ObjectContext>();		
	private Set<Activity> learningActivities = new HashSet<Activity>();
	private Set<ObjectExt> learningObjectExtensions = new HashSet<ObjectExt>();
	
	public boolean equals(final Object o) {
		if ((o.getId() == this.getId()) && (o instanceof Object)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}


	
	@Id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public void setLearningActivities(final Set<Activity> learningActivities) {
		this.learningActivities = learningActivities;
	}


	@OneToMany(mappedBy="object")
	public Set<Activity> getLearningActivities() {
		return this.learningActivities;
	}

	public void addLearningActivity(final Activity learningActivity) {
		this.learningActivities.add(learningActivity);
	}
	
	/**
	 * standard setter for the attribute course_resource.
	 * 
	 * @param courseLearningObjects
	 *            a set of entries in the course_resource table which relate the resource to the courses
	 */
	public void setObjectContexts(final Set<ObjectContext> objectContexts) {
		this.objectContexts = objectContexts;
	}

	/**
	 * standard getter for the attribute.
	 * 
	 * @return a set of entries in the course_resource table which relate the resource to the courses
	 */
	@OneToMany(mappedBy="object")
	public Set<ObjectContext> getObjectContexts() {
		return this.objectContexts;
	}

	/**
	 * standard add method for the attribute course_resource.
	 * 
	 * @param courseLearningObject
	 *            this entry will be added to the list of course_resource in this resource
	 */
	public void addObjectContext(final ObjectContext objectContext) {
		this.objectContexts.add(objectContext);
	}
	
	public void setParent(final long id, final Map<Long, Object> learningObjs,
			final Map<Long, Object> oldLearningObjs) {

		if (learningObjs.get(id) != null)
		{
			this.parent = learningObjs.get(id);
		}
		if ((this.parent == null) && (oldLearningObjs.get(id) != null))
		{
			this.parent = oldLearningObjs.get(id);
		}
	}
	
	/**
	 * @return the parent
	 */
	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@JoinColumn(name="parent")
	public Object getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Object parent) {
		this.parent = parent;
	}

	/**
	 * @return the LearningObjectExtensions
	 */
	@OneToMany(mappedBy="object")
	public Set<ObjectExt> getLearningObjectExtensions() {
		return learningObjectExtensions;
	}

	/**
	 * @param learningObjectExtensions the LearningObjectExtensions to set
	 */
	public void setLearningObjectExtensions(Set<ObjectExt> learningObjectExtensions) {
		this.learningObjectExtensions = learningObjectExtensions;
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
}
