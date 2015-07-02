package de.lemo.persistence.dataprovider;

import java.util.*;

public interface ED_Paths {
	
	public List<List<ED_Activity>> getAprioriPaths();
	
	public int[] getAprioriSupports();
	
	public List<List<ED_Activity>> getPrefixspanPaths();
	
	public int[] getPrefixspanSupports();
	
}
