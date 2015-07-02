package de.lemo.persistence.dataprovider;

import java.util.List;
import java.util.Set;

public interface ED_Context {
	
	public String getName();
	
	public String getDescriptor();
	
	public Set<String> extAttributes();
	
	public String getExtAttribute(String attr);
	
	public ED_Context getParent();
	
	public List<ED_Context> getChildren();
	
	public List<ED_Object> getObjects();
	
	public List<ED_Activity> getActivities();
	
	public Set<ED_Person> getStudents();
		
	public Set<ED_Person> getInstructors();
	
}