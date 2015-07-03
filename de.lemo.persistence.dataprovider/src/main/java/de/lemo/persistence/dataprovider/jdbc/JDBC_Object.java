package de.lemo.persistence.dataprovider.jdbc;

import de.lemo.persistence.dataprovider.*;

import java.sql.ResultSet;
import java.util.*;

public class JDBC_Object implements LA_Object {
	
	/**
	 * all instantiated learning objects, referenced by database ID
	 */
	static Map<Long,JDBC_Object> OBJECT = new HashMap<Long,JDBC_Object>();
	
	private String _name;
	private String _descriptor;
	private String _type;
	LA_Object _parent = null;
	List<LA_Object> _children = new ArrayList<LA_Object>();
	
	private Map<String,String> _extAttributes = new HashMap<String,String>();
	
	public JDBC_Object(Long oid, String name, String type) {
		OBJECT.put(oid, this);
		_name = name;
		_descriptor = Integer.toString(hashCode());
		_type = type;
		// find children
		List<Long> childrenIds = new ArrayList<Long>();
		Map<Long,String> idName = new HashMap<Long,String>();
		Map<Long,String> idType = new HashMap<Long,String>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT ID,NAME,TYPE FROM d4la_object WHERE PARENT=");
			sb.append(oid.longValue());
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long child = new Long(rs.getLong(1));
				idName.put(child, rs.getString(2));
				idType.put(child, rs.getString(3));
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
		for ( Long child : childrenIds ) {
			JDBC_Object object = JDBC_Object.findById(child);
			if ( object == null ) {
				object = new JDBC_Object(child, idName.get(child), idType.get(child));
			}
			object._parent = this;
			_children.add(object);
		}
	}

	public String getName() {
		return _name;
	}
	
	public String getDescriptor() {
		return _descriptor;
	}
	
	public String getType() {
		return _type;
	}
	
	public Set<String> extAttributes() {
		return _extAttributes.keySet();
	}
	
	public String getExtAttribute(String attr) {
		return _extAttributes.get(attr);
	}
	
	public LA_Object getParent() {
		return _parent;
	}
	
	public List<LA_Object> getChildren() {
		return _children;
	}
	
	static void initExtAttributes() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ATTR,VALUE,OBJECT FROM d4la_object_ext");
		try {
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				JDBC_Object object = findById(new Long(rs.getLong(3)));
				if ( object != null ) {
					object._extAttributes.put(rs.getString(1), rs.getString(2));
				}
			}
		} catch ( Exception e ) { e.printStackTrace(); }
	}
	
	static JDBC_Object findById(Long pid) {
		return OBJECT.get(pid);
	}
	
	static JDBC_Object findByDescriptor(String descriptor) {
		for ( JDBC_Object object : OBJECT.values() ) {
			if ( descriptor.equals(object.getDescriptor()) ) {
				return object;
			}
		}
		return null;
	}

}
