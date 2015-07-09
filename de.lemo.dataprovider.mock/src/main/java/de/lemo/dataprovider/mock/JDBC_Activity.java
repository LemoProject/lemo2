package de.lemo.dataprovider.mock;

import de.lemo.dataprovider.api.*;

import java.sql.ResultSet;
import java.util.*;

public class JDBC_Activity implements LA_Activity {
	
	/**
	 * all instantiated learning activities, referenced by database ID
	 */
	static Map<Long,JDBC_Activity> ACTIVITIES = new HashMap<Long,JDBC_Activity>();
		
	private long _time;
	private String _action = null;
	private String _info = null;
	private LA_Activity _reference = null;	
	private Map<String,String> _extAttributes = null;
	private LA_Person _person = null;
	private LA_Object _object = null;
	
	public JDBC_Activity(Long id, long time, LA_Person person, LA_Object object) {
		ACTIVITIES.put(id, this);
		_time = time;
		_person = person;
		_object = object;
	}

	public Set<String> extAttributes() {
		if ( _extAttributes == null ) return null;
		return _extAttributes.keySet();
	}
	
	public String getExtAttribute(String attr) {
		if ( _extAttributes == null ) return null;
		return _extAttributes.get(attr);
	}
	
	public long getTime() {
		return _time;
	}
	
	public String getAction() {
		return _action;
	}
	
	public String getInfo() {
		return _info;
	}
	
	public LA_Activity getReference() {
		return _reference;
	}
	
	public LA_Person getPerson() {
		return _person;
	}
	
	public LA_Object getObject() {
		return _object;
	}
	
	static void initialize(Set<Long> ids) {
		initActions(ids);
		initInfos(ids);
		initReferences(ids);
		initExtAttributes(ids);
	}
	
	private static void initActions(Set<Long> ids) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id,action FROM D4LA_Activity WHERE action IS NOT NULL");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long id = new Long(rs.getLong(1));
				if ( ids.contains(id)) {
					JDBC_Activity activity = ACTIVITIES.get(id);
					activity._action = rs.getString(2);
				}
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
	}
	
	private static void initInfos(Set<Long> ids) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id,info FROM D4LA_Activity WHERE info IS NOT NULL");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long id = new Long(rs.getLong(1));
				if ( ids.contains(id)) {
					JDBC_Activity activity = ACTIVITIES.get(id);
					activity._info = rs.getString(2);
				}
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
	}
	
	private static void initReferences(Set<Long> ids) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id,reference FROM D4LA_Activity WHERE reference IS NOT NULL");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long id = new Long(rs.getLong(1));
				if ( ids.contains(id)) {
					JDBC_Activity activity = ACTIVITIES.get(id);
					id = new Long(rs.getLong(2));
					activity._reference = ACTIVITIES.get(id);
				}
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
	}
	
	static void initExtAttributes(Set<Long> ids) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT attr,value,activity FROM D4LA_Activity_Ext");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long id = new Long(rs.getLong(3));
				if ( ids.contains(id)) {
					JDBC_Activity activity = ACTIVITIES.get(id);
					if ( activity._extAttributes == null ) activity._extAttributes = new HashMap<String,String>();
					activity._extAttributes.put(rs.getString(1), rs.getString(2));
				}
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
	}
	
	static JDBC_Activity findById(Long id) {
		return ACTIVITIES.get(id);
	}
	
}
