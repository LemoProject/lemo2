package de.lemo.persistence.d4la.entities;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This class represents the lemo_learning_activity object table.
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "D4LA_Activity")
public class Activity{

	private long id;
	private Context learningContext;
	private Person person;
	private Object learningObject;
	private Long time;
	private String action;
	private Activity reference;
	private String info;
	
	public boolean equals(final Activity o) {
		if ((o.getId() == this.getId()) && (o instanceof Activity)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}
	
	public int compareTo(final Activity arg0) {
		Activity s;
		try {
			s = arg0;
		} catch (final Exception e)
		{
			return 0;
		}
		if (s != null) {
			if (this.time > s.getTime()) {
				return 1;
			}
			if (this.time < s.getTime()) {
				return -1;
			}
		}
		return 0;
	}
	
	@Id
	public long getId() {
		return id;
	}
	
	
	
	public void setId(long id) {
		this.id = id;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="context")
	public Context getLearningContext() {
		return learningContext;
	}
	
	
	
	public void setLearningContext(Context learningContext) {
		this.learningContext = learningContext;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="person")
	public Person getPerson() {
		return person;
	}
	
	
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="object")
	public Object getLearningObject() {
		return learningObject;
	}
	
	
	
	public void setLearningObject(Object learningObject) {
		this.learningObject = learningObject;
	}
	
	
	@Column(name="time")
	public Long getTime() {
		return time;
	}
	
	
	
	public void setTime(Long time) {
		this.time = time;
	}
	
	
	public void setCourse(final long learningContext, final Map<Long, Context> learningContexts,
			final Map<Long, Context> oldLearningContexts) {

		if (learningContexts.get(learningContext) != null)
		{
			this.learningContext = learningContexts.get(learningContext);
			learningContexts.get(learningContext).addLearningActivity(this);
		}
		if ((this.learningContext == null) && (oldLearningContexts.get(learningContext) != null))
		{
			this.learningContext = oldLearningContexts.get(learningContext);
			oldLearningContexts.get(learningContext).addLearningActivity(this);
		}
	}
	
	public void setPerson(final long person, final Map<Long, Person> persons,
			final Map<Long, Person> oldPersons) {

		if (persons.get(person) != null)
		{
			this.person = persons.get(person);
			persons.get(person).addLearningActivity(this);
		}
		if ((this.person == null) && (oldPersons.get(person) != null))
		{
			this.person = oldPersons.get(person);
			oldPersons.get(person).addLearningActivity(this);
		}
	}
	
	public void setLearningObject(final long learningObject, final Map<Long, Object> learningObjects,
			final Map<Long, Object> oldLearningObjects) {

		if (learningObjects.get(learningObject) != null)
		{
			this.learningObject = learningObjects.get(learningObject);
			learningObjects.get(learningObject).addLearningActivity(this);
		}
		if ((this.learningObject == null) && (oldLearningObjects.get(learningObject) != null))
		{
			this.learningObject = oldLearningObjects.get(learningObject);
			oldLearningObjects.get(learningObject).addLearningActivity(this);
		}
	}

	/**
	 * @return the action
	 */
	@Column(name="action")
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the reference
	 */
	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@JoinColumn(name="reference")
	public Activity getReference() {
		return reference;
	}

	/**
	 * @param reference the reference to set
	 */
	public void setReference(Activity reference) {
		this.reference = reference;
	}

	/**
	 * @return the info
	 */
	@Column(name="info")
	public String getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}
}
