package de.lemo.persistence.dataprovider;

import java.util.Set;

public interface ED_Activity {
	
	public long getTime();
	
	public String getAction();
	
	public String getInfo();
	
	public Set<String> extAttributes();
	
	public String getExtAttribute(String attr);
			
	public ED_Activity getReference();
	
	public ED_Person getPerson();
	
	public ED_Context getContext();
	
	public ED_Object getObject();
	
}
