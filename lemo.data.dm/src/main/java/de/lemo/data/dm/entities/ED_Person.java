package de.lemo.data.dm.entities;

import java.util.Map;

public class ED_Person {
	
	private Long id;
	private String name;
	private Map<Long, String> contextRoles;
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
	 * @return the contextRoles
	 */
	public Map<Long, String> getContextRoles() {
		return contextRoles;
	}
	/**
	 * @param contextRoles the contextRoles to set
	 */
	public void setContextRoles(Map<Long, String> contextRoles) {
		this.contextRoles = contextRoles;
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
