/**
 * File ./src/main/java/de/lemo/dms/test/Test.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/test/Test.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.test;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.processing.questions.QPerformanceHistogram;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

/**
 * sollte gelöscht werden
 * @author Sebastian Schwarzrock
 *
 */
public class Test {


	public static void gen()
	{
		final ContentGenerator conGen = new ContentGenerator();
		ServerConfiguration.getInstance().loadConfig("/lemo");
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		dbHandler.saveCollectionToDB(session, conGen.generateMiningDB(5, 2, 2, 251, 1325372400L, 500, 1));
	}
	
	public static void testAssignment()
	{
		ServerConfiguration.getInstance().loadConfig("/lemo");
		QPerformanceHistogram qph = new QPerformanceHistogram();
		
		List<Long> courses = new ArrayList<Long>();
		List<Long> users = new ArrayList<Long>();
		List<Long> quizzes = new ArrayList<Long>();
		List<Long> gender = new ArrayList<Long>();
		courses.add(11961L);
		Long resolution = 100L;
		Long startTime = 0L;
		Long endTime = 1478305674L;
		
		
		
		ResultListLongObject rl = qph.compute(courses, users, quizzes, resolution, startTime, endTime, gender);
		System.out.println();
	}

	public static void write()
	{
		final TestDataCreatorChemgapedia ch = new TestDataCreatorChemgapedia();

		ch.getDataFromDB();
		ch.writeDataSource("c://users//s.schwarzrock//desktop//chemgaLog.log",
				"c://users//s.schwarzrock//desktop//VluGen");
	}

	public static void writeMoodle()
	{
		final TestDataCreatorMoodle mod = new TestDataCreatorMoodle();
		mod.getDataFromDB();
		mod.writeSourceDB();
	}

	/**
	 * main method for test case
	 * TODO move to unit test
	 * @param args
	 */
	public static void main(final String[] args)
	{
		
		Test.testAssignment();
	}
}
