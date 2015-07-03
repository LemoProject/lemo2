package de.lemo.persistence.dataprovider.jdbc;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Driver;

import de.lemo.persistence.dataprovider.*;

@Component
@Instantiate
@Provides
public class JDBC_DataProvider implements DataProvider {

	static private boolean INITIALIZED = false;
	static private Connection CONNECTION = null;
	static private Statement STATEMENT = null;
	
	static private final String DRIVER = "com.mysql.jdbc.Driver";
	static private final String URI = "jdbc:mysql://localhost:3306/d4la_moodle_ws14";
	static private final String USER = "root";
	static private final String PASSWORD = "";
	
	private static final Logger logger = LoggerFactory.getLogger(JDBC_DataProvider.class);

	
	public Set<LA_Context> getCourses() {
		Set<LA_Context> courses = new HashSet<LA_Context>();
		List<Long> courseIds = new ArrayList<Long>();
		Map<Long,String> idName = new HashMap<Long,String>();
		StringBuffer sb;
		ResultSet rs;
		try {
			sb = new StringBuffer();
			sb.append("SELECT id,name FROM D4LA_Context WHERE parent IS NULL");
			rs = executeQuery(new String(sb));
			while ( rs.next() ) {
				Long cid = new Long(rs.getLong(1));
				courseIds.add(cid);
				idName.put(cid, rs.getString(2));
			}			
		} catch ( Exception e ) { 
			logger.error("Error during first access to courses", e);
		}
		for ( Long cid : courseIds ) {
			courses.add(new JDBC_Context(cid, idName.get(cid)));
		}
//		closeConnection();
		return courses;
	}
	
	public Set<LA_Context> getCoursesByInstructor(String instructor) {
		Set<LA_Context> courses = new HashSet<LA_Context>();
		List<Long> courseIds = new ArrayList<Long>();
		Map<Long,String> idName = new HashMap<Long,String>();
		StringBuffer sb;
		ResultSet rs;
		try {
			sb = new StringBuffer();
			sb.append("SELECT id,name FROM D4LA_Context WHERE parent IS NULL ");
			sb.append("AND id IN (SELECT context FROM D4LA_Person_Context");
			sb.append("WHERE person IN (SELECT id FROM D4LA_Person_Ext");
			sb.append("WHERE attr = 'login' AND value = '");
			sb.append(instructor);
			sb.append("'))");
			rs = executeQuery(new String(sb));
			while ( rs.next() ) {
				Long cid = new Long(rs.getLong(1));
				courseIds.add(cid);
				idName.put(cid, rs.getString(2));
			}
			rs.close();
		} catch ( Exception e ) { 
			logger.error("Error while querrying courses by instructor",e);
		}
		for ( Long cid : courseIds ) {
			courses.add(new JDBC_Context(cid, idName.get(cid)));
		}
//		closeConnection();
		return courses;
	}
	
	public LA_Context getContext(String descriptor) {
		return JDBC_Context.findByDescriptor(descriptor);
	}
	
	public LA_Person getPerson(String descriptor) {
		return JDBC_Person.findByDescriptor(descriptor);
	}
	
	public LA_Object getObject(String descriptor) {
		return JDBC_Object.findByDescriptor(descriptor);
	}
	
	static ResultSet executeQuery(String sql) throws Exception {
		if ( CONNECTION == null ) {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);
			CONNECTION = DriverManager.getConnection(URI, USER, PASSWORD);
			STATEMENT = CONNECTION.createStatement();
		}
		return STATEMENT.executeQuery(sql);
	}
	
	static void closeConnection() {
		if ( CONNECTION != null) {
			try {
				CONNECTION.close();
				CONNECTION = null;
			} catch ( Exception e ) { 
				logger.error("Error closing jdbc connection", e);
			}
		}
	}
	
}
