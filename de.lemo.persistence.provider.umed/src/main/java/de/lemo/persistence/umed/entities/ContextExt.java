package de.lemo.persistence.umed.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "lemo_context_ext")
public class ContextExt{

	private long id;
	private LearningContext learningContext;
	private String value;
	private String attribute;
	

	
	
	/**
	 * @return the value
	 */
	@Column(name="value")
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the learningContext
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="learningcontext_id")
	public LearningContext getLearningContext() {
		return learningContext;
	}
	/**
	 * @param learningContext the learningContext to set
	 */
	public void setLearningContext(LearningContext learningContext) {
		this.learningContext = learningContext;
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
	
	
	public boolean equals(ContextExt o) {
		if ((o.getId() == this.getId()) && (o instanceof ContextExt)) {
			return true;
		}
		return false;
	}
	/**
	 * @return the attribute
	 */
	@Column(name="attribute")
	public String getAttribute() {
		return attribute;
	}
	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	
	
}

