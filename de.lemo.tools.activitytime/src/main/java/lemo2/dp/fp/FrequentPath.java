package lemo2.dp.fp;

import lemo2.dp.*;
import java.util.*;

public class FrequentPath {
	
	private List<ED_Object> _path;
	private int _support;
	
	public FrequentPath(List<ED_Object> path, int support) {
		_path = path;
		_support = support;
	}
	
	public List<ED_Object> getPath() {
		return _path;
	}
	
	public int support() {
		return _support;
	}

}
