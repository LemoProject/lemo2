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
	List<LA_Object> _children = null;
	
	private Map<String,String> _extAttributes = null;
	
	public JDBC_Object(Long oid, String name, String type) {
		OBJECT.put(oid, this);
		_name = name;
		_descriptor = Integer.toString(hashCode());
		_type = type;
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
	
	static void initChildren() {
		Set<Long> newIds = new HashSet<Long>();
		for ( Long oid : OBJECT.keySet() ) {
			JDBC_Object object = findById(oid);
			if ( object._children == null ) {
				object._children = new ArrayList<LA_Object>();
				newIds.add(oid);
			}
		}
		if ( ! newIds.isEmpty() ) initChildren(newIds);
	}
	
	private static void initChildren(Set<Long> objectIds) {
		Set<Long> newIds = new HashSet<Long>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id,name,type,parent FROM D4LA_Object WHERE parent IN ");
			sb.append("(");
			boolean first = true;
			for ( Long oid : objectIds ) {
				if ( first ) first = false;
				else sb.append(",");
				sb.append(oid.longValue());
			}
			sb.append(")");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long oid = new Long(rs.getLong(1));
				JDBC_Object child = OBJECT.get(oid);
				if ( child == null ) {
					child = new JDBC_Object(oid, rs.getString(2), rs.getString(3));
					newIds.add(oid);
				}			
				JDBC_Object parent = OBJECT.get(new Long(rs.getLong(3)));
				parent._children.add(child);
				child._parent = parent;
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
		if ( ! newIds.isEmpty() ) {
			initChildren(newIds);
		}
	}
	
	static void initExtAttributes() {
		Set<Long> done = new HashSet<>();
		for ( Long oid : OBJECT.keySet() ) {
			JDBC_Object object = OBJECT.get(oid);
			if ( object._extAttributes == null ) {
				object._extAttributes = new HashMap<String,String>();
			}
			else {
				done.add(oid);
			}
		}
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT attr,value,object FROM D4LA_Object_Ext");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long oid = new Long(rs.getLong(3));
				if ( ! done.contains(oid)) {
					JDBC_Object object = OBJECT.get(oid);
					if ( object != null ) {
						object._extAttributes.put(rs.getString(1), rs.getString(2));
					}
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
