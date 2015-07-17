package org.lemo2.dataprovider.mock;

import java.util.ArrayList;

import org.lemo2.dataprovider.api.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContextImpl implements LA_Context {
	
	String name = null;
	List<LA_Activity> activities = null;
	Set<LA_Object> objects = null;
	
	public ContextImpl(String name) {
		this.name = name;
		activities = new ArrayList<LA_Activity>();
		objects = new HashSet<LA_Object>();

		LA_Object object = new ObjectImpl();
		objects.add(object);
		
		LA_Person person = new PersonImpl();
		LA_Person person2 = new PersonImpl();
		
		ActivityImpl activity = new ActivityImpl(1434025149950L);
		activity.addObject(object);
		activity.addContext(this);
		activity.addPerson(person);
		activities.add(activity);
		
		activity = new ActivityImpl(1434025149940L);
		activity.addObject(object);
		activity.addContext(this);
		activity.addPerson(person);
		activities.add(activity);
		
		activity = new ActivityImpl(1434025149930L);
		activity.addObject(object);
		activity.addContext(this);
		activity.addPerson(person2);
		activities.add(activity);
		
		activity = new ActivityImpl(1434025149030L);
		activity.addObject(object);
		activity.addContext(this);
		activity.addPerson(person2);
		activities.add(activity);
	
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LA_Context getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LA_Context> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<LA_Person> getStudents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LA_Person> getInstructors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> extAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getExtAttribute(String attr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LA_Object> getObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LA_Activity> getActivities() {
		// TODO Auto-generated method stub
		return activities;
	}

}
