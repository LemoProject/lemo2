package org.lemo2.persistence.dataprovider.jdbc;

import java.util.*;

import org.lemo2.persistence.dataprovider.*;

public class Test_JDBC_DataProvider {
	
	public static void main(String[] args) {
		DataProvider dp = new JDBC_DataProvider();
		for ( LA_Context context : dp.getCourses() ) {
			printContext((JDBC_Context) context, "");
		}
	}
	
	static private void printContext(JDBC_Context context, String prefix) {
		System.out.print(prefix + "CONTEXT: " + context.getName());
		Set<String> attributes = context.extAttributes();
		if ( attributes != null ) {
			for ( String attr : attributes ) {
				System.out.print(" " + attr + ": " + context.getExtAttribute(attr));
			}
		}
		System.out.println();
		prefix += "    ";
		printFirstInstructor(context.getInstructors(), prefix);
		printFirstStudent(context.getStudents(), prefix);
		printFirstObject(context.getObjects(), prefix);
		printFirstActivity(context.getActivities(), prefix);
		List<LA_Context> children = context.getChildren();
		if ( children == null ) return;
		for ( LA_Context child : children ) {
			printContext((JDBC_Context) child, prefix);
		}
	}
	
	private static void printFirstInstructor(List<LA_Person> instructors, String prefix) {
		for ( LA_Person person : instructors ) {
			System.out.print(prefix + "FIRST INSTRUCTOR (1/" + instructors.size() + "):  name: ");
			System.out.print(person.getName());
			Set<String> attributes = person.extAttributes();
			if ( attributes != null ) {
				for ( String attr : attributes ) {
					System.out.print("  " + attr + ": " + person.getExtAttribute(attr));
				}
			}
			System.out.println();
			return;
		}
	}
	
	private static void printFirstStudent(List<LA_Person> students, String prefix) {
		for ( LA_Person person : students ) {
			System.out.print(prefix + "FIRST STUDENT (1/" + students.size() + "):  name: ");
			System.out.print(person.getName());
			Set<String> attributes = person.extAttributes();
			if ( attributes != null ) {
				for ( String attr : attributes ) {
					System.out.print("  " + attr + ": " + person.getExtAttribute(attr));
				}
			}
			System.out.println();
			return;
		}
	}
	
	private static void printFirstObject(List<LA_Object> objects, String prefix) {
		for ( LA_Object object : objects ) {
			System.out.print(prefix + "FIRST OBJECT (1/" + objects.size() + "):  name: ");
			System.out.print(object.getName() + "  type: " + object.getType());
			Set<String> attributes = object.extAttributes();
			if ( attributes != null ) {
				for ( String attr : attributes ) {
					System.out.print("  " + attr + ": " + object.getExtAttribute(attr));
				}
			}
			System.out.println();
			if ( object.getChildren() == null ) return;
			printFirstObject(object.getChildren(), prefix + "    ");
			return;
		}
	}
		
	private static void printFirstActivity(List<LA_Activity> activities, String prefix) {
		for ( LA_Activity activity : activities ) {
			System.out.print(prefix + "FIRST ACTIVITY (1/" + activities.size() + "):  time: ");
			System.out.print(activity.getTime());
			Set<String> attributes = activity.extAttributes();
			if ( attributes != null ) {
				for ( String attr : activity.extAttributes() ) {
					System.out.print("  " + attr + ": " + activity.getExtAttribute(attr));
				}
			}
			System.out.println();
			return;
		}
	}
	
}