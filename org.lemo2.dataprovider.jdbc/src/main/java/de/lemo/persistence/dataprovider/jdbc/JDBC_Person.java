package org.lemo2.persistence.dataprovider.jdbc;

import org.lemo2.persistence.dataprovider.*;

import java.util.*;
import java.sql.ResultSet;

public class JDBC_Person implements LA_Person {
	
	/**
	 * all instantiated persons, referenced by database ID
	 */
	static Map<Long,JDBC_Person> PERSONS = new HashMap<Long,JDBC_Person>();
	
	private String _name;
	private String _descriptor;
	private Map<String,String> _extAttributes = null;
	
	public JDBC_Person(Long id) {
		PERSONS.put(id, this);
		_descriptor = Integer.toString(hashCode());
	}

	public String getName() {
		return _name;
	}
	
	public String getDescriptor() {
		return _descriptor;
	}
	
	public Set<String> extAttributes() {
		if ( _extAttributes == null ) return null;
		return _extAttributes.keySet();
	}
	
	public String getExtAttribute(String attr) {
		if ( _extAttributes == null ) return null;
		return _extAttributes.get(attr);
	}
	
	static void initialize(Set<Long> ids) {
		initNames(ids);
		initExtAttributes(ids);
	}
	
	private static void initNames(Set<Long> ids) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id,name FROM D4LA_Person WHERE name IS NOT NULL");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long id = new Long(rs.getLong(1));
				if ( ids.contains(id)) {
					JDBC_Person person = PERSONS.get(id);
					person._name = rs.getString(2);
				}
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
	}
	
	static void initExtAttributes(Set<Long> ids) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT attr,value,person FROM D4LA_Person_Ext");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long id = new Long(rs.getLong(3));
				if ( ids.contains(id)) {
					JDBC_Person person = PERSONS.get(id);
					if ( person._extAttributes == null ) person._extAttributes = new HashMap<String,String>();
					person._extAttributes.put(rs.getString(1), rs.getString(2));
				}
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
	}
	
	static JDBC_Person findById(Long id) {
		return PERSONS.get(id);
	}
	
	static JDBC_Person findByDescriptor(String descriptor) {
		for ( JDBC_Person person : PERSONS.values() ) {
			if ( descriptor.equals(person.getDescriptor()) ) {
				return person;
			}
		}
		return null;
	}
	
	
}
