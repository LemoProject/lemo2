package de.lemo.dataprovider.api;

import java.util.Set;
import java.util.List;

public interface LA_Object {
		
	public String getName();
	
	public String getDescriptor();
	
	public String getType();
	
	public Set<String> extAttributes();

	public String getExtAttribute(String attr);
	
	public LA_Object getParent();
	
	public List<LA_Object> getChildren();

}
