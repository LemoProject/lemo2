package de.lemo.persistence.dataprovider.jdbc;

import java.util.*;

import de.lemo.persistence.dataprovider.*;

public class Test_JDBC_DataProvider {
	
	public static void main(String[] args) {
		long time1, time2;
		time1 = System.currentTimeMillis();
		DataProvider dp = new JDBC_DataProvider();
		List<LA_Context> courses = dp.getCourses();
		time2 = System.currentTimeMillis();
		System.out.println(">>>> time needed for instantiating all contexts: " + (time2-time1) + " ms");
		printContexts(courses);
	}
	
	static private void printContexts(List<LA_Context> contexts) {
		for ( LA_Context context : contexts ) {
			printContext((JDBC_Context) context, "", true);
		}
	}
	
	static private void printContext(JDBC_Context context, String prefix, boolean timing) {
		long time1, time2;
		System.out.print(prefix + "CONTEXT: " + context.getName());
		for ( String attr : context.extAttributes() ) {
			System.out.print(" " + attr + ": " + context.getExtAttribute(attr));
		}
		System.out.println();
		prefix += "    ";
		if ( timing ) {
			time1 = System.currentTimeMillis();
			context.getInstructors();
			time2 = System.currentTimeMillis();
			System.out.println(prefix + ">>>> time needed for instantiating content: " + (time2-time1) + " ms");
		}
		printInstructors(context, prefix);
		printStudents(context, prefix);
		printObjects(context, prefix);
		printActivities(context, prefix);
		for ( LA_Context child : context.getChildren() ) {
			printContext((JDBC_Context) child, prefix, false);
		}
	}
	
	private static void printInstructors(JDBC_Context context, String prefix) {
		List<LA_Person> instructors = context.getInstructors();
		int n = instructors.size();
		if ( n == 0 ) {
			System.out.println(prefix + "NO INSTRUCTORS");
		}
		else {
			LA_Person instructor = instructors.get(0);
			System.out.print(prefix + "FIRST INSTRUCTOR (1/" + n + "):  name: ");
			System.out.print(instructor.getName());
			for ( String attr : instructor.extAttributes() ) {
				System.out.print("  " + attr + ": " + instructor.getExtAttribute(attr));
			}
			System.out.println();
		}
	}
	
	private static void printStudents(JDBC_Context context, String prefix) {
		List<LA_Person> students = context.getStudents();
		int n = students.size();
		if ( n == 0 ) {
			System.out.println(prefix + "NO STUDENTS");
		}
		else {
			LA_Person student = students.get(0);
			System.out.print(prefix + "FIRST STUDENT (1/" + n + "):  name: ");
			System.out.print(student.getName());
			for ( String attr : student.extAttributes() ) {
				System.out.print("  " + attr + ": " + student.getExtAttribute(attr));
			}
			System.out.println();
		}
	}
	
	private static void printObjects(JDBC_Context context, String prefix) {
		List<LA_Object> objects = context.getObjects();
		int n = objects.size();
		if ( n == 0 ) {
			System.out.println(prefix + "NO OBJECTS");
		}
		else {
			LA_Object object = objects.get(0);
			System.out.print(prefix + "FIRST OBJECT (1/" + n + "):  name: ");
			System.out.print(object.getName() + "  type: " + object.getType());
			for ( String attr : object.extAttributes() ) {
				System.out.print("  " + attr + ": " + object.getExtAttribute(attr));
			}
			System.out.println();
			List<LA_Object> children = object.getChildren();
			while ( ! children.isEmpty() ) {
				prefix += "  ";
				LA_Object child = children.get(0);
				System.out.print(prefix + "FIRST CHILD (1/" + n + "):  name: ");
				System.out.print(child.getName() + "  type: " + child.getType());
				for ( String attr : child.extAttributes() ) {
					System.out.print("  " + attr + ": " + child.getExtAttribute(attr));
				}
				System.out.println();
			}
		}
	}
	
	private static void printActivities(JDBC_Context context, String prefix) {
		List<LA_Activity> activities = context.getActivities();
		int n = activities.size();
		if ( n == 0 ) {
			System.out.println(prefix + "NO ACTIVITIES");
		}
		else {
			LA_Activity activity = activities.get(0);
			System.out.print(prefix + "FIRST ACTIVITY (1/" + n + "):  time: ");
			System.out.print(activity.getTime());
			for ( String attr : activity.extAttributes() ) {
				System.out.print("  " + attr + ": " + activity.getExtAttribute(attr));
			}
			LA_Person person = activity.getPerson();
			if ( person != null ) {
				System.out.print("  person: " + person.getName());
			}
			LA_Object object = activity.getObject();
			if ( object != null ) {
				System.out.print("  object: " + object.getName());
			}
			LA_Context cont = activity.getContext();
			if ( cont != null ) {
				System.out.print("  context: " + cont.getName());
			}
			System.out.println();
		}
	}
	
	static private void printContextsxxx(Collection<LA_Context> contexts, String prefix, boolean timing) {
		if ( JDBC_DataProvider.DEBUG ) System.err.println(contexts.size() + " contexts");
		long time1 = 0, time2 = 0;
		for (LA_Context context : contexts ) {
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
			printContextsxxx(context.getChildren(), prefix + "    ", false);
		}
	}

	static private void printInstructors(Collection<LA_Person> instructors, String prefix) {
		for ( LA_Person instructor : instructors ) {
			System.out.print(prefix + "INSTRUCTOR: " + instructor.getName());
			for ( String attr : instructor.extAttributes() ) {
				System.out.print("  " + attr + ": " + instructor.getExtAttribute(attr));
			}
			System.out.println();
		}
	}
	
	static private void printStudents(Collection<LA_Person> students, String prefix) {
		System.out.println(prefix + "#STUDENTS: " + students.size());
	}
	
	static private void printObjects(List<LA_Object> objects, String prefix) {
		for ( LA_Object object : objects ) {
			System.out.print(prefix + object.getType().toUpperCase() + ": " + object.getName());
			for ( String attr : object.extAttributes() ) {
				System.out.print("  " + attr + ": " + object.getExtAttribute(attr));
			}
			System.out.println();
			printObjects(object.getChildren(), prefix + "  ");
		}		
	}

	static private void printActivities(Collection<LA_Activity> activities, String prefix) {
		System.out.println(prefix + "#ACTIVITIES: " + activities.size());
	}
	
}
