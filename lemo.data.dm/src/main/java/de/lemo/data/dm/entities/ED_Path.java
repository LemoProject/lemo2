package de.lemo.data.dm.entities;

import java.util.List;

public class ED_Path {
	
	private Long id;
	private List<Long> learningObjects;
	private long absoluteSupport;
	
	
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
	 * @return the learningObjects
	 */
	public List<Long> getLearningObjects() {
		return learningObjects;
	}
	/**
	 * @param learningObjects the learningObjects to set
	 */
	public void setLearningObjects(List<Long> learningObjects) {
		this.learningObjects = learningObjects;
	}
	/**
	 * @return the absoluteSupport
	 */
	public long getAbsoluteSupport() {
		return absoluteSupport;
	}
	/**
	 * @param absoluteSupport the absoluteSupport to set
	 */
	public void setAbsoluteSupport(long absoluteSupport) {
		this.absoluteSupport = absoluteSupport;
	}

}
