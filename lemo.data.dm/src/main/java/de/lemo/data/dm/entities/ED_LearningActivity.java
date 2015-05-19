package de.lemo.data.dm.entities;

import java.util.Map;

public class ED_LearningActivity {
	
	private Long id;
	private Long person;
	private Long learningContext;
	private Long LearningObject;
	private Long time;
	private String action;
	private long reference;
	private String info;
	private Map<String, String> extensions;
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the person
	 */
	public Long getPerson() {
		return person;
	}
	/**
	 * @param person the person to set
	 */
	public void setPerson(Long person) {
		this.person = person;
	}
	/**
	 * @return the learningContext
	 */
	public Long getLearningContext() {
		return learningContext;
	}
	/**
	 * @param learningContext the learningContext to set
	 */
	public void setLearningContext(Long learningContext) {
		this.learningContext = learningContext;
	}
	/**
	 * @return the learningObject
	 */
	public Long getLearningObject() {
		return LearningObject;
	}
	/**
	 * @param learningObject the learningObject to set
	 */
	public void setLearningObject(Long learningObject) {
		LearningObject = learningObject;
	}
	/**
	 * @return the time
	 */
	public Long getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(Long time) {
		this.time = time;
	}
	/**
	 * @return the action
	 */
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
	public long getReference() {
		return reference;
	}
	/**
	 * @param reference the reference to set
	 */
	public void setReference(long reference) {
		this.reference = reference;
	}
	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * @return the extensions
	 */
	public Map<String, String> getExtensions() {
		return extensions;
	}
	/**
	 * @param extensions the extensions to set
	 */
	public void setExtensions(Map<String, String> extensions) {
		this.extensions = extensions;
	}
	
	
	

}
