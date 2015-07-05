package de.lemo.persistence.dataprovider.jdbc;

import de.lemo.persistence.dataprovider.*;

import java.sql.ResultSet;
import java.util.*;

public class JDBC_Context implements LA_Context {
	
	/**
	 * all instantiated learning contexts, referenced by database ID
	 */
	static Map<Long,JDBC_Context> CONTEXT = new HashMap<Long,JDBC_Context>();
	
	private Long _cid;
	private String _name;
	private String _descriptor;
	private LA_Context _parent = null;
	private List<LA_Context> _children = null;
	private List<LA_Person> _students = null;
	private List<LA_Person> _instructors = null;
	private List<LA_Object> _objects = null;
	private List<LA_Activity> _activities = null;
	
	private Map<String,String> _extAttributes = null;
	
	public JDBC_Context(Long cid, String name) {
		CONTEXT.put(cid, this);
		_cid = cid;
		_name = name;
		_descriptor = Integer.toString(hashCode());
	}

	public Set<String> extAttributes() {
		return _extAttributes.keySet();
	}
	
	public String getExtAttribute(String attr) {
		return _extAttributes.get(attr);
	}
	
	public String getName() {
		return _name;
	}
	
	public String getDescriptor() {
		return _descriptor;
	}
	
	public LA_Context getParent() {
		return _parent;
	}
	
	public List<LA_Context> getChildren() {
		return _children;
	}
	
	public List<LA_Object> getObjects() {
		if ( _objects == null ) {
			initContents();
		}
		return _objects;
	}
			
	public List<LA_Person> getStudents() {
		if ( _students == null ) {
			initContents();
		}
		return _students;
	}
			
	public List<LA_Person> getInstructors() {
		if ( _instructors == null ) {
			initContents();
		}
		return _instructors;
	}
	
	public List<LA_Activity> getActivities() {
		if ( _activities == null ) {
			initContents();
		}
		return _activities;
	}
	
	private void initInstructors() {
		if ( _instructors != null ) return;
		_instructors = new ArrayList<LA_Person>();
		List<Long> personIds = new ArrayList<>();
		Map<Long,String> idName = new HashMap<Long,String>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id,name FROM D4LA_Person WHERE id IN ");
			sb.append("(SELECT person FROM D4LA_Person_Context WHERE context = ");
			sb.append(_cid.longValue());
			sb.append(" AND role = 'instructor')");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long pid = new Long(rs.getLong(1));
				personIds.add(pid);
				idName.put(pid, rs.getString(2));
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
		for ( Long pid : personIds ) {
			JDBC_Person person = JDBC_Person.findById(pid);
			if ( person == null ) {
				person = new JDBC_Person(pid, idName.get(pid));
			}
			_instructors.add(person);
		}
		for ( LA_Context child : _children ) {
			JDBC_Context c = (JDBC_Context) child;
			c.initInstructors();
			_instructors.addAll(c._instructors);
		}
	}
	
	private void initStudents() {
		if ( _students != null ) return;
		_students = new ArrayList<LA_Person>();
		List<Long> personIds = new ArrayList<>();
		Map<Long,String> idName = new HashMap<Long,String>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id,name FROM D4LA_Person WHERE id IN ");
			sb.append("(SELECT person FROM D4LA_Person_Context WHERE context = ");
			sb.append(_cid.longValue());
			sb.append(" AND role = 'student')");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long pid = new Long(rs.getLong(1));
				personIds.add(pid);
				idName.put(pid, rs.getString(2));
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
		for ( Long pid : personIds ) {
			JDBC_Person person = JDBC_Person.findById(pid);
			if ( person == null ) {
				person = new JDBC_Person(pid, idName.get(pid));
			}
			_students.add(person);
		}
		for ( LA_Context child : _children ) {
			JDBC_Context c = (JDBC_Context) child;
			c.initStudents();
			_students.addAll(c._students);
		}
	}
	
	private void initObjects() {
		if ( _objects != null ) return;
		_objects = new ArrayList<LA_Object>();
		List<Long> objectIds = new ArrayList<Long>();
		Map<Long,String> idName = new HashMap<Long,String>();
		Map<Long,String> idType = new HashMap<Long,String>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id,name,type FROM D4LA_Object WHERE id IN ");
			sb.append("(SELECT object FROM D4LA_Object_Context WHERE CONTEXT = ");
			sb.append(_cid.longValue());
			sb.append(")");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long oid = new Long(rs.getLong(1));
				objectIds.add(oid);
				idName.put(oid, rs.getString(2));
				idType.put(oid, rs.getString(3));
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
		for ( Long oid : objectIds ) {
			JDBC_Object object = JDBC_Object.findById(oid);
			if ( object == null ) {
				object = new JDBC_Object(oid, idName.get(oid), idType.get(oid));
			}
			_objects.add(object);
		}
		for ( LA_Context child : _children ) {
			JDBC_Context c = (JDBC_Context) child;
			c.initObjects();
			_objects.addAll(c._objects);
		}
	}
	
	private List<Long> initActivities(JDBC_Context context) {
		List<Long> familyIds = new ArrayList<Long>();
		familyIds.add(context._cid);
		for ( LA_Context child : context._children ) {
			List<Long> cids = initActivities((JDBC_Context) child);
			for ( Long cid : cids ) {
				if ( ! context._children.contains(cid) ) familyIds.add(cid);
			}
		}
		context._activities = JDBC_Activity.initActivities(familyIds);
		return familyIds;
	}
		
	private void initContents() {
		JDBC_Context context = this;
		while ( context._parent != null ) {
			context = (JDBC_Context) context._parent;
		}
		context.initInstructors();
		context.initStudents();
		JDBC_Person.initExtAttributes();
		context.initObjects();
		JDBC_Object.initChildren();
		JDBC_Object.initExtAttributes();
		initActivities(context);
		JDBC_Activity.initExtAttributes();
		JDBC_Activity.initReferences();
	}
	
	static void initChildren() {
		Set<Long> newIds = new HashSet<Long>();
		for ( Long cid : CONTEXT.keySet() ) {
			JDBC_Context context = findById(cid);
			if ( context._children == null ) {
				context._children = new ArrayList<LA_Context>();
				newIds.add(cid);
			}
		}
		if ( ! newIds.isEmpty() ) initChildren(newIds);
	}
	
	private static void initChildren(Set<Long> contextIds) {
		Set<Long> newIds = new HashSet<Long>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id,name,parent FROM D4LA_Context WHERE parent IN ");
			sb.append("(");
			boolean first = true;
			for ( Long cid : contextIds ) {
				if ( first ) first = false;
				else sb.append(",");
				sb.append(cid.longValue());
			}
			sb.append(")");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long cid = new Long(rs.getLong(1));
				JDBC_Context child = CONTEXT.get(cid);
				if ( child == null ) {
					child = new JDBC_Context(cid, rs.getString(2));
					newIds.add(cid);
				}			
				JDBC_Context parent = CONTEXT.get(new Long(rs.getLong(3)));
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
		for ( Long cid : CONTEXT.keySet() ) {
			JDBC_Context context = CONTEXT.get(cid);
			if ( context._extAttributes == null ) {
				context._extAttributes = new HashMap<String,String>();
			}
			else {
				done.add(cid);
			}
		}
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT attr,value,context FROM D4LA_Context_Ext");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long cid = new Long(rs.getLong(3));
				if ( ! done.contains(cid)) {
					JDBC_Context context = CONTEXT.get(cid);
					if ( context != null ) {
						context._extAttributes.put(rs.getString(1), rs.getString(2));
					}
				}
			}
		} catch ( Exception e ) { e.printStackTrace(); }
	}
	
	static JDBC_Context findById(Long cid) {
		return CONTEXT.get(cid);
	}
	
	static JDBC_Context findByDescriptor(String descriptor) {
		for ( JDBC_Context context : CONTEXT.values() ) {
			if ( descriptor.equals(context.getDescriptor()) ) {
				return context;
			}
		}
		return null;
	}
	
}
	
