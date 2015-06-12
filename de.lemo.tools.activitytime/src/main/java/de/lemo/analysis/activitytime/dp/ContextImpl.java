package de.lemo.analysis.activitytime.dp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContextImpl implements ED_Context {
	
	long id = 0;
	String name = null;
	List<ED_Activity> activities = null;
	Set<ED_Object> objects = null;
	
	public ContextImpl(long id, String name) {
		this.id=id;
		this.name = name;
		activities = new ArrayList<ED_Activity>();
		objects = new HashSet<ED_Object>();

		ED_Object object = new ObjectImpl();
		objects.add(object);
		
		ED_Person person = new PersonImpl();
		
		ED_Activity activity = new ActivityImpl(1434025149950L);
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
		activity.addPerson(person);
		activities.add(activity);
	
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ED_Context getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ED_Context> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ED_Object> getObjects(String type) {
		// TODO Auto-generated method stub
		return objects;
	}

	@Override
	public List<ED_Activity> getActivities(String action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ED_Activity> getActivities(String action, Date begin, Date end) {
		// TODO Auto-generated method stub
		return activities;
	}

	@Override
	public Set<ED_Person> getPersons(String role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ED_Person> getStudents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ED_Person> getInstructors() {
		// TODO Auto-generated method stub
		return null;
	}

}
