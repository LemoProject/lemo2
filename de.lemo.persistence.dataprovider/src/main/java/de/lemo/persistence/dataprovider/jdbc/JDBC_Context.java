package de.lemo.persistence.dataprovider.jdbc;

import de.lemo.persistence.dataprovider.*;

import java.sql.ResultSet;
import java.util.*;

public class JDBC_Context implements LA_Context {
	
	/**
	 * all instantiated learning contexts, referenced by database ID
	 */
	static Map<Long,JDBC_Context> CONTEXTS = new HashMap<Long,JDBC_Context>();
	
	private Long _id;
	private boolean _initStudents = false;
	private boolean _initInstructors = false;
	private boolean _initObjects = false;
	private boolean _initActivities = false;
	private String _name = null;
	private String _descriptor;
	private LA_Context _parent = null;
	private List<LA_Context> _children = null;
	private List<LA_Person> _students = null;
	private List<LA_Person> _instructors = null;
	private List<LA_Object> _objects = null;
	private List<LA_Activity> _activities = null;	
	private Map<String,String> _extAttributes = null;
	
	public JDBC_Context(Long id) {
		CONTEXTS.put(id, this);
		_id = id;
		_descriptor = Integer.toString(hashCode());
	}

	public Set<String> extAttributes() {
		if ( _extAttributes == null ) return null;
		return _extAttributes.keySet();
	}
	
	public String getExtAttribute(String attr) {
		if ( _extAttributes == null ) return null;
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
		if ( _initObjects ) return _objects;
		long timing1, timing2;
		if ( JDBC_DataProvider.DEBUG ) {
			timing1 = System.currentTimeMillis();
		}
		_initObjects = true;
		_objects = new ArrayList<LA_Object>();
		Set<Long> ids = new HashSet<Long>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT object FROM D4LA_Object_Context WHERE context = ");
			sb.append(_id.longValue());
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long id = new Long(rs.getLong(1));
				JDBC_Object object = JDBC_Object.findById(id);
				if ( object == null ) object = new JDBC_Object(id);
				_objects.add(object);
				ids.add(id);
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
		JDBC_Object.initialize(ids);
		if ( JDBC_DataProvider.DEBUG ) {
			timing2 = System.currentTimeMillis();
			System.out.println(">> TIMING initialize _objects: " + (timing2-timing1) + " ms");
		}
		return _objects;
	}
			
	public List<LA_Person> getStudents() {
		if ( _initStudents ) return _students;
		long timing1, timing2;
		if ( JDBC_DataProvider.DEBUG ) {
			timing1 = System.currentTimeMillis();
		}
		_initStudents = true;
		_students = new ArrayList<LA_Person>();
		Set<Long> ids = new HashSet<Long>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT person FROM D4LA_Person_Context WHERE context = ");
			sb.append(_id.longValue());
			sb.append(" AND role = 'student'");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long id = new Long(rs.getLong(1));
				JDBC_Person person = JDBC_Person.findById(id);
				if ( person == null ) person = new JDBC_Person(id);
				_students.add(person);
				ids.add(id);
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
		JDBC_Person.initialize(ids);
		if ( JDBC_DataProvider.DEBUG ) {
			timing2 = System.currentTimeMillis();
			System.out.println(">> TIMING initialize _students: " + (timing2-timing1) + " ms");
		}
		return _students;
	}
			
	public List<LA_Person> getInstructors() {
		if ( _initInstructors ) return _instructors;
		long timing1, timing2;
		if ( JDBC_DataProvider.DEBUG ) {
			timing1 = System.currentTimeMillis();
		}
		_initInstructors = true;
		_instructors = new ArrayList<LA_Person>();
		Set<Long> ids = new HashSet<Long>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT person FROM D4LA_Person_Context WHERE context = ");
			sb.append(_id.longValue());
			sb.append(" AND role = 'instructor'");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long id = new Long(rs.getLong(1));
				JDBC_Person person = JDBC_Person.findById(id);
				if ( person == null ) person = new JDBC_Person(id);
				_instructors.add(person);
				ids.add(id);
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
		JDBC_Person.initialize(ids);
		if ( JDBC_DataProvider.DEBUG ) {
			timing2 = System.currentTimeMillis();
			System.out.println(">> TIMING initialize _instructors: " + (timing2-timing1) + " ms");
		}
		return _instructors;
	}
	
	public List<LA_Activity> getActivities() {
		if ( _initActivities ) return _activities;
		long timing1, timing2;
		if ( JDBC_DataProvider.DEBUG ) {
			timing1 = System.currentTimeMillis();
		}
		_initActivities = true;
		_activities = new ArrayList<LA_Activity>();
		Set<Long> ids = new HashSet<Long>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id,time,person,object FROM D4LA_Activity WHERE context = ");
			sb.append(_id.longValue());
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long id = new Long(rs.getLong(1));
				JDBC_Activity activity = JDBC_Activity.findById(id);
				if ( activity == null ) {
					activity = new JDBC_Activity(id, rs.getLong(2),
							JDBC_Person.findById(new Long(rs.getLong(3))),
							JDBC_Object.findById(new Long(rs.getLong(4))));
				}
				_activities.add(activity);
				ids.add(id);
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
		JDBC_Activity.initialize(ids);
		if ( JDBC_DataProvider.DEBUG ) {
			timing2 = System.currentTimeMillis();
			System.out.println(">> TIMING initialize _activities: " + (timing2-timing1) + " ms");
		}
		return _activities;
	}
	
	static void initialize(Set<Long> ids) {
		long timing1, timing2;
		if ( JDBC_DataProvider.DEBUG ) {
			timing1 = System.currentTimeMillis();
		}
		Set<Long> allIds = new HashSet<Long>();
		while ( ! ids.isEmpty() ) {
			allIds.addAll(ids);
			try {
				StringBuffer sb = new StringBuffer();
				sb.append("SELECT id,parent FROM D4LA_Context WHERE parent IN (");
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
					JDBC_Context parent = CONTEXTS.get(id);
					id = new Long(rs.getLong(1));
					JDBC_Context child = CONTEXTS.get(id);
					if ( child == null ) {
						child = new JDBC_Context(id);
						ids.add(id);
					}
					if ( parent._children == null ) parent._children = new ArrayList<LA_Context>();
					parent._children.add(child);
					child._parent = parent;
				}
				rs.close();
			} catch ( Exception e ) { e.printStackTrace(); }
		}
		initNames(allIds);
		initExtAttributes(allIds);
		if ( JDBC_DataProvider.DEBUG ) {
			timing2 = System.currentTimeMillis();
			System.out.println(">> TIMING initialize contexts: " + (timing2-timing1) + " ms");
		}
	}
		
	private static void initNames(Set<Long> ids) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id,name FROM D4LA_Context WHERE name IS NOT NULL");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long id = new Long(rs.getLong(1));
				if ( ids.contains(id)) {
					JDBC_Context context = CONTEXTS.get(id);
					context._name = rs.getString(2);
				}
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
	}
	
	static void initExtAttributes(Set<Long> ids) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT attr,value,context FROM D4LA_Context_Ext");
			ResultSet rs = JDBC_DataProvider.executeQuery(new String(sb));
			while ( rs.next() ) {
				Long id = new Long(rs.getLong(3));
				if ( ids.contains(id)) {
					JDBC_Context context = CONTEXTS.get(id);
					if ( context._extAttributes == null ) context._extAttributes = new HashMap<String,String>();
					context._extAttributes.put(rs.getString(1), rs.getString(2));
				}
			}
			rs.close();
		} catch ( Exception e ) { e.printStackTrace(); }
	}
	
	static JDBC_Context findById(Long id) {
		return CONTEXTS.get(id);
	}
	
	static JDBC_Context findByDescriptor(String descriptor) {
		for ( JDBC_Context context : CONTEXTS.values() ) {
			if ( descriptor.equals(context.getDescriptor()) ) {
				return context;
			}
		}
		return null;
	}
	
}
	
