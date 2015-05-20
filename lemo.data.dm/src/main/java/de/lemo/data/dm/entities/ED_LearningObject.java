package de.lemo.data.dm.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Contains information about a LearningObject
 * 
 * @author sschwarzrock
 *
 */
public class ED_LearningObject {
	
	private Long id;
	private String name;
	private String type;
	private Long parent;
	private Set<Long> children;
	private Map<String, String> extensions = new HashMap<String, String>();
	
	/**
	 * Adds an extension attribute-value pair to the LearningObject
	 * 
	 * @param attr	The attribute's name
	 * @param value The attribute's value
	 */
	public void addExtension(String attr, String value)
	{
		this.extensions.put(attr, value);
	}
	
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
