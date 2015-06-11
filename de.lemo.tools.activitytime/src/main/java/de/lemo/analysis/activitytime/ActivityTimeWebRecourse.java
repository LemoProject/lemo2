package de.lemo.analysis.activitytime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lemo.rest.api.WebResource;
import lemo2.dp.*;
import lemo1.*;


@Component
@Provides
@Instantiate
@Path("tools/activitytime")
public class ActivityTimeWebRecourse implements WebResource{

	private static final Logger logger = LoggerFactory.getLogger(ActivityTimeWebRecourse.class);
	private DataProvider dataProvider = new DataProviderImpl();
	@GET
	public String getResult(){
		ResultListHashMapObject resultListHashMap = computeActivities(Arrays.asList(1L),null,(Long)System.currentTimeMillis()-100000000L,
				(Long)System.currentTimeMillis(),10L,Arrays.asList("test"),Arrays.asList(1L,2L),null);
		logger.info(resultListHashMap.toString());
		return "Test";
	}

	public ResultListHashMapObject computeActivities(final List<Long> courses,
			List<Long> users,
			final Long startTime,
			final Long endTime,
			final Long resolution,
			final List<String> resourceTypes,
			List<Long> gender,
			List<Long> learningObjects){

		//validateTimestamps(startTime, endTime, resolution);
		final Map<Long, ResultListLongObject> result = new HashMap<Long, ResultListLongObject>();
		//Map<Long, Long> userMap = StudentHelper.getCourseStudentsAliasKeys(courses, gender);
		// Set up db-connection
/*
		if (users.isEmpty()) {
			users = new ArrayList<Long>(userMap.values());
			if (users.isEmpty()) {
				// TODO no users in course, maybe send some http error
				this.logger.debug("No users found for course. Returning empty resultset.");
				return new ResultListHashMapObject();
			}
		}
		else
		{
			List<Long> tmp = new ArrayList<Long>();
			for(int i = 0; i < users.size(); i++)
			{
				tmp.add(userMap.get(users.get(i)));
			}
			users = tmp;
		}
*/
		// Calculate size of time intervalls
		final double intervall = (endTime - startTime) / (resolution);
		
	
//		final Map<Long, Long> idToAlias = StudentHelper.getCourseStudentsRealKeys(courses, gender);
		final Map<Long, HashMap<Integer, Set<Long>>> userPerResStep = new HashMap<Long, HashMap<Integer, Set<Long>>>();

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

		for (final Long course : courses) {
			userPerResStep.put(course, new HashMap<Integer, Set<Long>>());
		}

		for (String resourceType : resourceTypes) {
			this.logger.debug("Course Activity Request - CA Selection: " + resourceType);
		}
		if (resourceTypes.isEmpty()) {
			this.logger.debug("Course Activity Request - CA Selection: NO Items selected ");
		}

/*		final Criteria criteria = session.createCriteria(ILog.class, "log")
				.add(Restrictions.in("log.course.id", courses))
				.add(Restrictions.between("log.timestamp", startTime, endTime))
				.add(Restrictions.in("log.user.id", users));

		if(!learningObjects.isEmpty())
		{
			criteria.add(Restrictions.in("log.learning.id", learningObjects));
		}
		*/

		Set<ED_Context> contexts = dataProvider.getCourses();
		ED_Context context = null;
	    for (Iterator<ED_Context> it = contexts.iterator(); it.hasNext(); ) {
	    	context = it.next();
	    }

//		List<ILog> logs = criteria.list();

		for (ED_Activity log : context.getActivities("test",new Date(),new Date()))
		{
			boolean isInRT = false;
			if ((resourceTypes != null) && (resourceTypes.size() > 0) && resourceTypes.contains(log.getObject().getType()))
			{
				isInRT = true;
			}
			if ((resourceTypes == null) || (resourceTypes.size() == 0) || isInRT)
			{
				Integer pos = new Double((log.getTime() - startTime) / intervall).intValue();
				if (pos > (resolution - 1)) {
					pos = resolution.intValue() - 1;
				}
				result.get(log.getContext().getId()).getElements()
							.set(pos, result.get(log.getContext().getId()).getElements().get(pos) + 1);
				if (userPerResStep.get(log.getContext().getId()).get(pos) == null)
				{
					final Set<Long> s = new HashSet<Long>();
					s.add(log.getPerson().getId());
					userPerResStep.get(log.getContext().getId()).put(pos, s);
				} else {
					userPerResStep.get(log.getContext().getId()).get(pos).add(log.getPerson().getId());
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
			while (it.hasNext()) 
			{
				final Long learnObjectTypeName = it.next();
				this.logger.info("Result Course IDs: " + learnObjectTypeName);
			}

		} else {
			this.logger.info("Returning empty resultset.");
		}
		return resultObject;
	}
}
