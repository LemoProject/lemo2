package de.lemo.data.dm.entities;

import java.util.Map;
import java.util.Set;

public class ED_LearningContext {
	
	private Long id;
	private String name;
	private Long parent;
	private Long firstAccess;
	private Long lastAccess;
	private Long userCount;
	private Set<Long> children;
	
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the parent
	 */
	public Long getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Long parent) {
		this.parent = parent;
	}
	/**
	 * @return the firstAccess
	 */
	public Long getFirstAccess() {
		return firstAccess;
	}
	/**
	 * @param firstAccess the firstAccess to set
	 */
	public void setFirstAccess(Long firstAccess) {
		this.firstAccess = firstAccess;
	}
	/**
	 * @return the lastAccess
	 */
	public Long getLastAccess() {
		return lastAccess;
	}
	/**
	 * @param lastAccess the lastAccess to set
	 */
	public void setLastAccess(Long lastAccess) {
		this.lastAccess = lastAccess;
	}
	/**
	 * @return the userCount
	 */
	public Long getUserCount() {
		return userCount;
	}
	/**
	 * @param userCount the userCount to set
	 */
	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}
	/**
	 * @return the children
	 */
	public Set<Long> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(Set<Long> children) {
		this.children = children;
	}
	/**
	 * @return the learningObjects
	 */
	public Set<Long> getLearningObjects() {
		return learningObjects;
	}
	/**
	 * @param learningObjects the learningObjects to set
	 */
	public void setLearningObjects(Set<Long> learningObjects) {
		this.learningObjects = learningObjects;
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
	private Set<Long> learningObjects;
	private Map<String, String> extensions;

}
