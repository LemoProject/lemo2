package de.lemo.persistence.dataprovider;

import java.util.Set;

public interface ED_Person {
	
	public String getName();
	
	public String getDescriptor();
	
	public Set<String> extAttributes();
	
	public String getExtAttribute(String attr);
		
}
