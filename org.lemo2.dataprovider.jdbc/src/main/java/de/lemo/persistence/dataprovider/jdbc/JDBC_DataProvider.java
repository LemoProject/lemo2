package org.lemo2.persistence.dataprovider.jdbc;

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

import org.lemo2.persistence.dataprovider.*;

@Component
@Instantiate
@Provides
public class JDBC_DataProvider implements DataProvider {
	
	static final boolean DEBUG = true;
	
	static private Statement STATEMENT = null;
	
	static private final String URI = "jdbc:mysql://localhost:3306/d4la_moodle";
	static private final String USER = "root";
	static private final String PASSWORD = "";
	
	private static final Logger logger = LoggerFactory.getLogger(JDBC_DataProvider.class);

	
	public List<LA_Context> getCourses() {
		List<LA_Context> courses = new ArrayList<LA_Context>();
		Set<Long> ids = new HashSet<Long>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id FROM D4LA_Context WHERE parent IS NULL");
			ResultSet rs = executeQuery(new String(sb));
			while ( rs.next() ) {
				Long id = new Long(rs.getLong(1));
				JDBC_Context context = JDBC_Context.findById(id);
				if ( context == null ) {
					context = new JDBC_Context(id);
					ids.add(id);
				}
				courses.add(context);
			}
			rs.close();
		} catch ( Exception e ) { 
			logger.error("Error during first access to courses", e);
		}
		JDBC_Context.initialize(ids);
		return courses;
	}
	
	public List<LA_Context> getCoursesByInstructor(String instructor) {
		List<LA_Context> courses = new ArrayList<LA_Context>();
		Set<Long> ids = new HashSet<Long>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id FROM D4LA_Context WHERE parent IS NULL ");
			sb.append("AND id IN (SELECT context FROM D4LA_Person_Context");
			sb.append("WHERE person IN (SELECT id FROM D4LA_Person_Ext");
			sb.append("WHERE attr = 'login' AND value = '");
			sb.append(instructor);
			sb.append("'))");
			ResultSet rs = executeQuery(new String(sb));
			while ( rs.next() ) {
				Long id = new Long(rs.getLong(1));
				JDBC_Context context = JDBC_Context.findById(id);
				if ( context == null ) {
					context = new JDBC_Context(id);
					ids.add(id);
				}
				courses.add(context);
			}
			rs.close();
		} catch ( Exception e ) { 
			logger.error("Error during first access to courses", e);
		}
		JDBC_Context.initialize(ids);
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
		if ( STATEMENT == null ) {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);
			Connection connection = DriverManager.getConnection(URI, USER, PASSWORD);
			STATEMENT = connection.createStatement();
		}
		ResultSet rs;
		long timing1, timing2;
		if ( DEBUG ) {
			timing1 = System.currentTimeMillis();
		}
		rs = STATEMENT.executeQuery(sql);
		if ( DEBUG ) {
			timing2 = System.currentTimeMillis();
			System.out.println(">> SQL " + sql + ": " + (timing2-timing1) + " ms");
		}
		return rs;
	}
		
}
