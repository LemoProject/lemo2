package de.lemo.persistence.d4la.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "D4LA_Object_Ext")
public class ObjectExt{

	private long id;
	private Object learningObject;
	private String value;
	private String attr;
	

	
	
	/**
	 * @return the value
	 */
	@Lob
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
	 * @return the learningId
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="object")
	public Object getLearningObject() {
		return learningObject;
	}
	/**
	 * @param learningId the learningId to set
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
	
	public boolean equals(ObjectExt o) {
		if ((o.getId() == this.getId()) && (o instanceof ObjectExt)) {
			return true;
		}
		return false;
	}
	/**
	 * @return the attr
	 */
	@Column(name="attr")
	public String getAttr() {
		return attr;
	}
	/**
	 * @param attr the attr to set
	 */
	public void setAttr(String attr) {
		this.attr = attr;
	}
	
	
	
}

