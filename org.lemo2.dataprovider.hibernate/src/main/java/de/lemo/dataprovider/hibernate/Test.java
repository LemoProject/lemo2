package org.lemo2.dataprovider.hibernate;

import java.util.List;

import org.lemo2.dataprovider.api.LA_Context;

public class Test {

	public static void main(String[] args) {
		DataProviderHibernate dataProvider = new DataProviderHibernate();
		List<LA_Context> contexts = dataProvider.getCourses();
		System.out.println("Size of Contexts: " + contexts.size());
		for(LA_Context context : contexts ){
			System.out.println(context);			
		}
	}
}
