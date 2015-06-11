package lemo2.dp.fp;

import java.util.*;

import lemo2.dp.*;

public class FrequentPaths {

	private List<FrequentPath> _apriori;
	private List<FrequentPath> _viger;
	private int _minSupportApriori;
	private int _minSupportViger;
	private ED_Context _context;
	
	public FrequentPaths(ED_Context context) {
		_context = context;
	}
	
	public List<FrequentPath> apriori() {
		return _apriori;
	}
	
	public List<FrequentPath> viger() {
		return _viger;
	}
	
	public int minSupportApriori() {
		return _minSupportApriori;
	}
	
	public int minSupportViger() {
		return _minSupportViger;
	}
	
}
