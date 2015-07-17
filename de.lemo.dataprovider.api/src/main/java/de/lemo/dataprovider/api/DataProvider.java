package de.lemo.dataprovider.api;

import java.util.List;

public interface DataProvider {
	
	/**
	 * 
	 * course is a ED_Context with no parents
	 * @return set of all courses, or null, if there is no database connection
	 */
	public List<LA_Context> getCourses();
	
	/**
	 * 
	 * @param String identifying instructor (e.g. userID)
	 * @return set of all courses for instructor, or null
	 */
	public List<LA_Context> getCoursesByInstructor(String userId);
	
	/**
	 * 
	 * @param context descriptor (value returned by method getDescriptor)
	 * @return ED_Context object which has been instantiated by a previous call, or null
	 */
	public LA_Context getContext(String descriptor);
	
	/**
	 * 
	 * @param person descriptor (value returned by method getDescriptor)
	 * @return ED_Person object which has been instantiated by a previous call, or null
	 */
	public LA_Person getPerson(String descriptor);
	
	/**
	 * 
	 * @param object descriptor (value returned by method getDescriptor)
	 * @return ED_Object object which has been instantiated by previous call, or null
	 */
	public LA_Object getObject(String descriptor);

	public boolean testConnection();
	
}