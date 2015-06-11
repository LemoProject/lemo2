package lemo2.dp;

import java.util.HashSet;
import java.util.Set;

public class DataProviderImpl implements DataProvider {
	
	Set<ED_Context> courses;
	
	public DataProviderImpl() {
		this.courses = new HashSet<ED_Context>();
		ED_Context context = new ContextImpl(1, "Toll");	
		this.courses.add(context);
	}

	@Override
	public Set<ED_Context> getCourses() {
		// TODO Auto-generated method stub
		return courses;
	}

	@Override
	public Set<ED_Context> getCourses(ED_Person person) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ED_Person getPerson(String login) {
		// TODO Auto-generated method stub
		return null;
	}

}
