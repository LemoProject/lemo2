package de.lemo.persistence.lemo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Simple key value configuration store.
 * 
 * @author Leonard Kappe
 */
@Entity
@Table(name = "lemo_config")
public class Config {

	private String name;
	private String value;

	@Id
	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
