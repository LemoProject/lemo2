package org.lemo2.persistence.dataprovider;

import java.util.*;

public interface LA_Paths {
	
	public List<List<LA_Activity>> getAprioriPaths();
	
	public int[] getAprioriSupports();
	
	public List<List<LA_Activity>> getPrefixspanPaths();
	
	public int[] getPrefixspanSupports();
	
}
