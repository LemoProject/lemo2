package org.lemo2.dataprovider.mock;

import java.util.Set;

import org.lemo2.dataprovider.api.*;

public class ActivityImpl implements LA_Activity {
	
	LA_Context context = null;
	String action = null;
	long time;
	LA_Object object = null;
	LA_Person person = null;
	
	public ActivityImpl() {
		action = "test";
		time = (long)(System.currentTimeMillis()-Math.random()*10);
	}
	
	public ActivityImpl(long time) {
		action = "test";
		this.time = time;
	}
	
	@Override
	public LA_Person getPerson() {
		// TODO Auto-generated method stub
		return person;
	}

	@Override
	public LA_Object getObject() {
		// TODO Auto-generated method stub
		return object;
	}

	@Override
	public long getTime() {
		// TODO Auto-generated method stub
		return time;
	}

	@Override
	public String getAction() {
		return action;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LA_Activity getReference() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addObject(LA_Object object) {
		this.object = object;		
	}

	public void addContext(LA_Context context) {
		this.context = context;		
	}

	public void addPerson(LA_Person person) {
		this.person = person;		
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

}
