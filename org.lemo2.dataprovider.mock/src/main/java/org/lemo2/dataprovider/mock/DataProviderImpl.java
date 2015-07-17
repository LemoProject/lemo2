package org.lemo2.dataprovider.mock;

import java.util.ArrayList;
import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import org.lemo2.dataprovider.api.*;


@Component
@Provides
@Instantiate
public class DataProviderImpl implements DataProvider {
	
	List<LA_Context> courses;
	
	public DataProviderImpl() {
		this.courses = new ArrayList<LA_Context>();
		LA_Context context = new ContextImpl("Toll");	
		this.courses.add(context);
	}

	@Override
	public List<LA_Context> getCourses() {
		// TODO Auto-generated method stub
		return courses;
	}

	@Override
	public LA_Person getPerson(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LA_Context> getCoursesByInstructor(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LA_Context getContext(String descriptor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LA_Object getObject(String descriptor) {
		// TODO Auto-generated method stub
		return null;
	}

}
