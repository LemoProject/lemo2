package de.lemo.analysis.activitytime.dp;

import java.util.Date;

public class ActivityImpl implements ED_Activity {
	
	ED_Context context = null;
	String action = null;
	long time;
	ED_Object object = null;
	ED_Person person = null;
	
	public ActivityImpl() {
		action = "test";
		time = (long)(System.currentTimeMillis()-Math.random()*10);
	}
	
	public ActivityImpl(long time) {
		action = "test";
		this.time = time;
	}
	
	@Override
	public ED_Person getPerson() {
		// TODO Auto-generated method stub
		return person;
	}

	@Override
	public ED_Context getContext() {
		// TODO Auto-generated method stub
		return context;
	}

	@Override
	public ED_Object getObject() {
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
	public ED_Activity getReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addObject(ED_Object object) {
		this.object = object;		
	}

	@Override
	public void addContext(ED_Context context) {
		this.context = context;		
	}

	@Override
	public void addPerson(ED_Person person) {
		this.person = person;		
	}

}
