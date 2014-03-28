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

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.processing.questions.QCourseActivity;
import de.lemo.dms.processing.questions.QFrequentPathsBIDE;
import de.lemo.dms.processing.questions.QFrequentPathsViger;
import de.lemo.dms.processing.questions.QPerformanceBoxPlot;
import de.lemo.dms.processing.questions.QPerformanceHistogram;
import de.lemo.dms.processing.questions.QPerformanceUserTestBoxPlot;

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
		dbHandler.saveCollectionToDB(session, conGen.generateMiningDB(5, 250, 1325372400L, 5000));
	}
/*	
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
	
	public static void test()
	{
		QPerformanceHistogram qph = new QPerformanceHistogram();
		QPerformanceUserTestBoxPlot qpubp = new QPerformanceUserTestBoxPlot();
		QCourseActivity qca = new QCourseActivity();
		QPerformanceBoxPlot qpbp = new QPerformanceBoxPlot();
		QFrequentPathsViger qfpv = new QFrequentPathsViger();
		QFrequentPathsBIDE qfpb = new QFrequentPathsBIDE();
		List<Long> courses = new ArrayList<Long>();
		courses.add(221L);
		List<Long> users = new ArrayList<Long>();
		Long startTime = 1325375975L;
		Long endTime = 1356906989L;
		Long resolution = 100L;
		List<String> types = new ArrayList<String>();
		List<Long> gender = new ArrayList<Long>();
		List<Long> quizzes = new ArrayList<Long>();
		quizzes.add(11224L);
		quizzes.add(11225L);
		quizzes.add(11221L);
		quizzes.add(11222L);
		quizzes.add(11223L);
		Long u = 1L;

		try {
			qfpb.compute(1L, courses, users, types, 0L, 1000L, 0.9, false, startTime, endTime, gender);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}

	public static void main(final String[] args)
	{
		ServerConfiguration.getInstance().loadConfig("/lemo");
		Test.test();
	}

}
