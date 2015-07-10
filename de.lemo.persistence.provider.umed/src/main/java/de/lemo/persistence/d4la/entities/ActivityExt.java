package de.lemo.persistence.d4la.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "D4LA_Activity_Ext")
public class ActivityExt{

	private long id;
	private Activity learningActivity;
	private String value;
	private String attr;
	

	
	
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
	 * @return the learningActivity
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="activity")
	public Activity getLearningActivity() {
		return learningActivity;
	}
	/**
	 * @param learningActivity the learningActivity to set
	 */
	public void setLearningActivity(Activity learningActivity) {
		this.learningActivity = learningActivity;
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


	public boolean equals(ActivityExt o) {
		if ((o.getId() == this.getId()) && (o instanceof ActivityExt)) {
			return true;
		}
		return false;
	}
	/**
	 * @return the attribute
	 */
	@Column(name="attr")
	public String getAttr() {
		return attr;
	}
	/**
	 * @param attr the attribute to set
	 */
	public void setAttr(String attr) {
		this.attr = attr;
	}
	
	
	
}

