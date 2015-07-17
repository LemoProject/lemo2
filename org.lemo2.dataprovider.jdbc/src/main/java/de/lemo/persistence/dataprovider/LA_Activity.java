package de.lemo.persistence.dataprovider;

import java.util.Set;

public interface LA_Activity {
	
	public long getTime();
	
	public String getAction();
	
	public String getInfo();
	
	public Set<String> extAttributes();
	
	public String getExtAttribute(String attr);
			
	public LA_Activity getReference();
		
	public LA_Object getObject();
	
	public LA_Person getPerson();
	
}
