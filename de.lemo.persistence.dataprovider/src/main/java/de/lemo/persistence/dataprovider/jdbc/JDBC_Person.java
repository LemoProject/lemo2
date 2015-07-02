package de.lemo.persistence.dataprovider.jdbc;

import de.lemo.persistence.dataprovider.*;

import java.util.*;
import java.sql.ResultSet;

public class JDBC_Person implements ED_Person {
	
	/**
	 * all instantiated persons, referenced by database ID
	 */
	static Map<Long,JDBC_Person> PERSON = new HashMap<Long,JDBC_Person>();
	
	private String _name;
	private String _descriptor;
	private Map<String,String> _extAttributes = new HashMap<String,String>();
	
	public JDBC_Person(Long pid, String name) {
		PERSON.put(pid, this);
		_name = name;
		_descriptor = Integer.toString(hashCode());
	}

	public String getName() {
		return _name;
	}
	
	public String getDescriptor() {
		return _descriptor;
	}
	
	public Set<String> extAttributes() {
		return _extAttributes.keySet();
	}
	
	public String getExtAttribute(String attr) {
		return _extAttributes.get(attr);
	}
	
	static void initExtAttributes() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ATTR,VALUE,PERSON FROM d4la_person_ext");
		try {
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				JDBC_Person person = findById(new Long(rs.getLong(3)));
				if ( person != null ) {
					person._extAttributes.put(rs.getString(1), rs.getString(2));
				}
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
	}
	
	static JDBC_Person findById(Long pid) {
		return PERSON.get(pid);
	}
	
	static JDBC_Person findByDescriptor(String descriptor) {
		for ( JDBC_Person person : PERSON.values() ) {
			if ( descriptor.equals(person.getDescriptor()) ) {
				return person;
			}
		}
		return null;
	}
	
	
}
