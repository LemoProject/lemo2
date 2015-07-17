package org.lemo2.persistence.dataprovider;

import java.util.Set;

public interface LA_Person {
	
	public String getName();
	
	public String getDescriptor();
	
	public Set<String> extAttributes();
	
	public String getExtAttribute(String attr);
		
}
