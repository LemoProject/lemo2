package de.lemo.persistence.dataprovider;

import java.util.List;
import java.util.Set;

public interface LA_Context {
	
	public String getName();
	
	public String getDescriptor();
	
	public Set<String> extAttributes();
	
	public String getExtAttribute(String attr);
	
	public LA_Context getParent();
	
	public List<LA_Context> getChildren();
	
	public List<LA_Object> getObjects();
	
	public List<LA_Activity> getActivities();
	
	public Set<LA_Person> getStudents();
		
	public Set<LA_Person> getInstructors();
	
}