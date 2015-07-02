package de.lemo.persistence.dataprovider;

import java.util.Set;
import java.util.List;

public interface ED_Object {
		
	public String getName();
	
	public String getDescriptor();
	
	public String getType();
	
	public Set<String> extAttributes();

	public String getExtAttribute(String attr);
	
	public ED_Object getParent();
	
	public List<ED_Object> getChildren();

}
