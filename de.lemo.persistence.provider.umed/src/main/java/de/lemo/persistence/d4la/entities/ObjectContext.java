package de.lemo.persistence.d4la.entities;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/** This class represents the relationship between contexts and learningObjects. */
@Entity
@Table(name = "D4LA_Object_Context")
public class ObjectContext{
	
	private long id;
	private Context learningContext;
	private Object learningObject;
	
	
	public boolean equals(final ObjectContext o) {
		if ((o.getId() == this.getId()) && (o instanceof ObjectContext)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}
	
	/**
	 * @return the course
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="context")
	public Context getLearningContext() {
		return learningContext;
	}


	/**
	 * @param course the course to set
	 */
	public void setLearningContext(Context learningContext) {
		this.learningContext = learningContext;
	}
	
	public void setLearningContext(final long learningContext, final Map<Long, Context> learningContexts,
			final Map<Long, Context> oldLearningContexts) {
		if (learningContexts.get(learningContext) != null)
		{
			this.learningContext = learningContexts.get(learningContext);
			learningContexts.get(learningContext).addObjectContext(this);
		}
		if ((this.learningContext == null) && (oldLearningContexts.get(learningContext) != null))
		{
			this.learningContext = oldLearningContexts.get(learningContext);
			oldLearningContexts.get(learningContext).addObjectContext(this);
		}
	}
	
	public void setLearningObject(final long learningObject, final Map<Long, Object> learningObjects,
			final Map<Long, Object> oldLearningObjects) {
		if (learningObjects.get(learningObject) != null)
		{
			this.learningObject = learningObjects.get(learningObject);
			learningObjects.get(learningObject).addObjectContext(this);
		}
		if ((this.learningObject == null) && (oldLearningObjects.get(learningObject) != null))
		{
			this.learningObject = oldLearningObjects.get(learningObject);
			oldLearningObjects.get(learningObject).addObjectContext(this);
		}
	}


	/**
	 * @return the learningObject
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="object")
	public Object getLearningObject() {
		return this.learningObject;
	}


	/**
	 * @param learningObject the learningObject to set
	 */
	public void setLearningObject(Object learningObject) {
		this.learningObject = learningObject;
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
}
