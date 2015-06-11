package lemo2.dp;

import java.util.*;

public interface ED_Object {
	
	public String getName();
	
	public ED_Object getParent();
	
	public List<ED_Object> getChildren();
	
	public String getType();
	
}
