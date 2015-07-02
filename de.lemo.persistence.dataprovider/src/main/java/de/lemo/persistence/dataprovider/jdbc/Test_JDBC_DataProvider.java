package de.lemo.persistence.dataprovider.jdbc;

import java.util.*;

import de.lemo.persistence.dataprovider.*;

public class Test_JDBC_DataProvider {
	
	public static void main(String[] args) {
		long time1, time2;
		time1 = System.currentTimeMillis();
		DataProvider dp = new JDBC_DataProvider();
		Set<ED_Context> courses = dp.getCourses();
		time2 = System.currentTimeMillis();
		System.out.println(">>>> time needed for instantiating contexts: " + (time2-time1) + " ms");
		printContexts(courses, "", true);
	}

	static private void printContexts(Collection<ED_Context> contexts, String prefix, boolean timing) {
		long time1 = 0, time2 = 0;
		for (ED_Context context : contexts ) {
			if ( timing ) time1 = System.currentTimeMillis();
			System.out.print(prefix + "CONTEXT: " + context.getName());
			for ( String attr : context.extAttributes() ) {
				System.out.print("  " + attr + ": " + context.getExtAttribute(attr));
			}
			System.out.println();
			printInstructors(context.getInstructors(), prefix + "  ");
			printStudents(context.getStudents(), prefix + "  ");
			printObjects(context.getObjects(), prefix + "  ");
			printActivities(context.getActivities(), prefix + "  ");
			if ( timing ) {
				time2 = System.currentTimeMillis();
				System.out.println(">>>> time needed for instantiating course content: " + (time2-time1) + " ms");
			}
			System.out.println();
			printContexts(context.getChildren(), prefix + "    ", false);
		}
	}

	static private void printInstructors(Collection<ED_Person> instructors, String prefix) {
		for ( ED_Person instructor : instructors ) {
			System.out.print(prefix + "INSTRUCTOR: " + instructor.getName());
			for ( String attr : instructor.extAttributes() ) {
				System.out.print("  " + attr + ": " + instructor.getExtAttribute(attr));
			}
			System.out.println();
		}
	}
	
	static private void printStudents(Collection<ED_Person> students, String prefix) {
		System.out.println(prefix + "#STUDENTS: " + students.size());
	}
	
	static private void printObjects(List<ED_Object> objects, String prefix) {
		for ( ED_Object object : objects ) {
			System.out.print(prefix + object.getType().toUpperCase() + ": " + object.getName());
			for ( String attr : object.extAttributes() ) {
				System.out.print("  " + attr + ": " + object.getExtAttribute(attr));
			}
			System.out.println();
			printObjects(object.getChildren(), prefix + "  ");
		}		
	}

	static private void printActivities(Collection<ED_Activity> activities, String prefix) {
		System.out.println(prefix + "#ACTIVITIES: " + activities.size());
	}
	
}
