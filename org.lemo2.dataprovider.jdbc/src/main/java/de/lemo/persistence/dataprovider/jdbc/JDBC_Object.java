package org.lemo2.persistence.dataprovider.jdbc;

import org.lemo2.persistence.dataprovider.*;

import java.sql.ResultSet;
import java.util.*;

public class JDBC_Object implements LA_Object {
	
	/**
	 * all instantiated learning objects, referenced by database ID
	 */
	static Map<Long,JDBC_Object> OBJECTS = new HashMap<Long,JDBC_Object>();
	
	private String _name = null;
	private String _descriptor;
	private String _type = null;
	private LA_Object _parent = null;
	private List<LA_Object> _children = null;
	private Map<String,String> _extAttributes = null;
	
	public JDBC_Object(Long id) {
		OBJECTS.put(id, this);
		_descriptor = Integer.toString(hashCode());
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
		if ( _extAttributes == null ) return null;
		return _extAttributes.keySet();
	}
	
	public String getExtAttribute(String attr) {
		if ( _extAttributes == null ) return null;
		return _extAttributes.get(attr);
	}
	
	public LA_Object getParent() {
		return _parent;
	}
	
	public List<LA_Object> getChildren() {
		return _children;
	}

	static void initialize(Set<Long> ids) {
		Set<Long> allIds = new HashSet<Long>();
		while ( ! ids.isEmpty() ) {
			allIds.addAll(ids);
			try {
				StringBuffer sb = new StringBuffer();
				sb.append("SELECT id,parent FROM D4LA_Object WHERE parent IN (");
				boolean first = true;
				for ( Long id : ids ) {
					if ( first ) first = false;
					else sb.append(",");
					sb.append(id.longValue());
				}
				sb.append(")");
				ids = new HashSet<Long>();
				ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
				while ( rs.next() ) {
					Long id = new Long(rs.getLong(2));
					JDBC_Object parent = OBJECTS.get(id);
					id = new Long(rs.getLong(1));
					JDBC_Object child = OBJECTS.get(id);
					if ( child == null ) {
						child = new JDBC_Object(id);
						ids.add(id);
					}
					if ( parent._children == null ) parent._children = new ArrayList<LA_Object>();
					parent._children.add(child);
					child._parent = parent;
				}
				rs.close();
			} catch ( Exception e ) { e.printStackTrace(); }
		}
		initNames(allIds);
		initTypes(allIds);
		initExtAttributes(allIds);
	}
		
	private static void initNames(Set<Long> ids) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id,name FROM D4LA_Object WHERE name IS NOT NULL");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long id = new Long(rs.getLong(1));
				if ( ids.contains(id)) {
					JDBC_Object object = OBJECTS.get(id);
					object._name = rs.getString(2);
				}
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
	}
	
	private static void initTypes(Set<Long> ids) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id,type FROM D4LA_Object WHERE type IS NOT NULL");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long id = new Long(rs.getLong(1));
				if ( ids.contains(id)) {
					JDBC_Object object = OBJECTS.get(id);
					object._type = rs.getString(2);
				}
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
	}
	
	private static void initExtAttributes(Set<Long> ids) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT attr,value,object FROM D4LA_Object_Ext");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long id = new Long(rs.getLong(3));
				if ( ids.contains(id)) {
					JDBC_Object object = OBJECTS.get(id);
					if ( object._extAttributes == null ) object._extAttributes = new HashMap<String,String>();
					object._extAttributes.put(rs.getString(1), rs.getString(2));
				}
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
	}
	

	static JDBC_Object findById(Long id) {
		return OBJECTS.get(id);
	}
	
	static JDBC_Object findByDescriptor(String descriptor) {
		for ( JDBC_Object object : OBJECTS.values() ) {
			if ( descriptor.equals(object.getDescriptor()) ) {
				return object;
			}
		}
		return null;
	}

}
