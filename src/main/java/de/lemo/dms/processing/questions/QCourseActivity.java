/**
 * File ./main/java/de/lemo/dms/processing/questions/QCourseActivity.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseUserMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.ResultListHashMapObject;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

/**
 * Shows to the activities in the courses by objects
 * 
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
@Path("courseactivity")
public class QCourseActivity extends Question {

	/**
	 * Returns a list with the length of 'resolution'. Each entry holds the number of requests in the interval.
	 * 
	 * @param courses
	 *            (Mandatory) Course-identifiers of the courses that should be processed.
	 * @param roles
	 *            (Optional) Role-identifiers
	 * @param startTime
	 *            (Mandatory)
	 * @param endTime
	 *            (Mandatory)
	 * @param resolution
	 *            (Mandatory)
	 * @param resourceTypes
	 *            (Optional)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@POST
	public ResultListHashMapObject compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.ROLE_IDS) final List<Long> roles,
			@FormParam(MetaParam.USER_IDS) final List<Long> users,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam(MetaParam.RESOLUTION) final Long resolution,
			@FormParam(MetaParam.TYPES) final List<String> resourceTypes) {

		final HashMap<Long, ResultListLongObject> result = new HashMap<Long, ResultListLongObject>();
		
		validateTimestamps(startTime, endTime, resolution);

		// Set up db-connection
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		// Calculate size of time intervalls
		final double intervall = (endTime - startTime) / (resolution);

		final HashMap<Long, HashMap<Integer, Set<Long>>> userPerResStep = new HashMap<Long, HashMap<Integer, Set<Long>>>();

		// Create and initialize array for results
		for (int j = 0; j < courses.size(); j++)
		{

			final Long[] resArr = new Long[resolution.intValue()];
			for (int i = 0; i < resArr.length; i++)
			{
				resArr[i] = 0L;
			}
			final List<Long> l = new ArrayList<Long>();
			Collections.addAll(l, resArr);
			result.put(courses.get(j), new ResultListLongObject(l));
		}

		for (final Long c : courses)
		{

			userPerResStep.put(c, new HashMap<Integer, Set<Long>>());

		}

		if ((resourceTypes != null) && (resourceTypes.size() > 0)) {
			for (int i = 0; i < resourceTypes.size(); i++) {
				this.logger.info("Course Activity Request - CA Selection: " + resourceTypes.get(i));
			}
		} else {
			this.logger.info("Course Activity Request - CA Selection: NO Items selected ");
		}

		if ((resourceTypes != null) && (resourceTypes.size() > 0)) {
			for (int i = 0; i < resourceTypes.size(); i++) {
				this.logger.info("Course Activity Request - CA Selection: " + resourceTypes.get(i));
			}
		} else {
			this.logger.info("Course Activity Request - CA Selection: NO Items selected ");
		}

		List<CourseUserMining> ilm = null;

		if ((roles != null) && (roles.size() > 0))
		{
			final Criteria criteria = session.createCriteria(CourseUserMining.class, "log")
					.add(Restrictions.in("log.course.id", courses))
					.add(Restrictions.in("log.role.id", roles));
			if ((users != null) && (users.size() > 0)) {
				criteria.add(Restrictions.in("log.user.id", users));
			}
			ilm = criteria.list();
		}
		final List<Long> userList = new ArrayList<Long>();

		if (ilm != null) {
			for (int i = 0; i < ilm.size(); i++)
			{
				if (ilm.get(i).getUser() != null) {
					userList.add(ilm.get(i).getUser().getId());
				}
			}
		}
		List<ILogMining> logs = null;

		final Criteria criteria2 = session.createCriteria(ILogMining.class, "log");
		criteria2.add(Restrictions.in("log.course.id", courses));

		if (users.size() > 0) {
			criteria2.add(Restrictions.in("log.user.id", users));
		} else if (userList.size() > 0) {
			criteria2.add(Restrictions.in("log.user.id", users));
		}

		criteria2.add(Restrictions.between("log.timestamp", startTime, endTime));

		logs = criteria2.list();

		for (int i = 0; i < logs.size(); i++)
		{
			boolean isInRT = false;
			if ((resourceTypes != null) && (resourceTypes.size() > 0)) {
				for (int j = 0; j < resourceTypes.size(); j++) {
					if (logs.get(i).getClass().toString().toUpperCase().contains(resourceTypes.get(j)))
					{
						isInRT = true;
						break;
					}
				}
			}
			if ((resourceTypes == null) || (resourceTypes.size() == 0) || isInRT)
			{
				Integer pos = new Double((logs.get(i).getTimestamp() - startTime) / intervall).intValue();
				if (pos > (resolution - 1)) {
					pos = resolution.intValue() - 1;
				}
				result.get(logs.get(i).getCourse().getId()).getElements()
						.set(pos, result.get(logs.get(i).getCourse().getId()).getElements().get(pos) + 1);
				if (userPerResStep.get(logs.get(i).getCourse().getId()).get(pos) == null)
				{
					final Set<Long> s = new HashSet<Long>();
					s.add(logs.get(i).getUser().getId());
					userPerResStep.get(logs.get(i).getCourse().getId()).put(pos, s);
				} else {
					userPerResStep.get(logs.get(i).getCourse().getId()).get(pos).add(logs.get(i).getUser().getId());
				}
			}
		}

		for (final Long c : courses)
		{
			for (int i = 0; i < resolution; i++)
			{
				if (userPerResStep.get(c).get(i) == null)
				{
					result.get(c).getElements().add(0L);
				} else {
					result.get(c).getElements().add(Long.valueOf(userPerResStep.get(c).get(i).size()));
				}
			}
		}

		final ResultListHashMapObject resultObject = new ResultListHashMapObject(result);
		if ((resultObject != null) && (resultObject.getElements() != null)) {
			final Set<Long> keySet = resultObject.getElements().keySet();
			final Iterator<Long> it = keySet.iterator();
			while (it.hasNext()) {
				final Long learnObjectTypeName = it.next();
				this.logger.info("Result Course IDs: " + learnObjectTypeName);
			}

		} else {
			this.logger.info("Empty resultset !!!");
		}
		return resultObject;
	}
}
