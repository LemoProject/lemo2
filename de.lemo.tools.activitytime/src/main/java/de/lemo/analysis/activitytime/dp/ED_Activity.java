package de.lemo.analysis.activitytime.dp;

public interface ED_Activity {
	
	public ED_Person getPerson();
	
	public ED_Context getContext();
	
	public ED_Object getObject();
	
	public long getTime();
	
	public String getAction();
	
	public String getInfo();
	
	public ED_Activity getReference();

	public void addObject(ED_Object object);

	public void addContext(ED_Context context);

	public void addPerson(ED_Person person);
	
}
