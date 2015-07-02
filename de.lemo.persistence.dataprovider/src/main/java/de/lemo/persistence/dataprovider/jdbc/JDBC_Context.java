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
	private List<LA_Context> _children = new ArrayList<LA_Context>();
	private Set<LA_Person> _students = null;
	private Set<LA_Person> _instructors = null;
	private List<LA_Object> _objects = null;
	private List<LA_Activity> _activities = null;
	
	private Map<String,String> _extAttributes = new HashMap<String,String>();
	
	public JDBC_Context(Long cid, String name) {
		CONTEXT.put(cid, this);
		_cid = cid;
		_name = name;
		_descriptor = Integer.toString(hashCode());
		// find children
		List<Long> childrenIds = new ArrayList<Long>();
		Map<Long,String> idName = new HashMap<Long,String>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT ID,NAME FROM d4la_context WHERE PARENT=");
			sb.append(cid.longValue());
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long child = new Long(rs.getLong(1));
				childrenIds.add(child);
				idName.put(child, rs.getString(2));
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
		for ( Long child : childrenIds ) {
			JDBC_Context context = findById(child);
			if ( context == null ) {
				context = new JDBC_Context(child, idName.get(child));
			}
			context._parent = this;
			_children.add(context);
		}
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
			
	public Set<LA_Person> getStudents() {
		if ( _students == null ) {
			initContents();
		}
		return _students;
	}
			
	public Set<LA_Person> getInstructors() {
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
		_instructors = new HashSet<LA_Person>();
		List<Long> personIds = new ArrayList<>();
		Map<Long,String> idName = new HashMap<Long,String>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT ID,NAME FROM d4la_person WHERE ID IN ");
			sb.append("(SELECT PERSON FROM d4la_person_context WHERE CONTEXT = ");
			sb.append(_cid.longValue());
			sb.append(" AND ROLE = 'instructor')");
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
		_students = new HashSet<LA_Person>();
		List<Long> personIds = new ArrayList<>();
		Map<Long,String> idName = new HashMap<Long,String>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT ID,NAME FROM d4la_person WHERE ID IN ");
			sb.append("(SELECT PERSON FROM d4la_person_context WHERE CONTEXT = ");
			sb.append(_cid.longValue());
			sb.append(" AND ROLE = 'student')");
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
		_objects = new ArrayList<LA_Object>();
		List<Long> objectIds = new ArrayList<Long>();
		Map<Long,String> idName = new HashMap<Long,String>();
		Map<Long,String> idType = new HashMap<Long,String>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT ID,NAME,TYPE FROM d4la_object WHERE ID IN ");
			sb.append("(SELECT OBJECT FROM d4la_object_context WHERE CONTEXT = ");
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
	
	private Set<Long> initActivities() {
		_activities = new ArrayList<LA_Activity>();
		Set<Long> contextIds = new HashSet<Long>();
		contextIds.add(_cid);
		for ( LA_Context c : _children ) {
			JDBC_Context child = (JDBC_Context) c;
			contextIds.addAll(child.initActivities());
		}
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT ID,PERSON,OBJECT,TIME,ACTION,INFO FROM d4la_activity ");
			sb.append("WHERE CONTEXT IN (");
			boolean first = true;
			for ( Long cid : contextIds ) {
				if ( first ) first = false;
				else sb.append(" , ");
				sb.append(cid.longValue());
			}
			sb.append(") ORDER BY TIME ASC");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long aid = new Long(rs.getLong(1));
				JDBC_Activity activity = JDBC_Activity.findById(aid);
				if ( activity == null ) {
					Long pid = new Long(rs.getLong(2));
					Long oid = new Long(rs.getLong(3));
					long time = rs.getLong(4);
					String action = rs.getString(5);
					String info = rs.getString(6);
					activity = new JDBC_Activity(aid, _cid, pid, oid, time, action, info);
				}
				_activities.add(activity);
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
		return contextIds;
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
		JDBC_Object.initExtAttributes();
		context.initActivities();
		JDBC_Activity.initExtAttributes();
		JDBC_Activity.initReferences();
	}
	
	static void initExtAttributes() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ATTR,VALUE,CONTEXT FROM d4la_context_ext");
		try {
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				JDBC_Context context = findById(new Long(rs.getLong(3)));
				if ( context != null ) {
					context._extAttributes.put(rs.getString(1), rs.getString(2));
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
	
