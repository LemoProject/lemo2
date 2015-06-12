package de.lemo.analysis.activitytime;

import java.io.ByteArrayOutputStream;
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.BadRequestException;

import de.lemo.rest.api.WebResource;
import lemo2.dp.*;
import lemo1.*;


@Component
@Provides
@Instantiate
@Path("tools/activitytime")
public class ActivityTimeWebResource implements WebResource{

	private static final Logger logger = LoggerFactory.getLogger(ActivityTimeWebResource.class);
	private DataProvider dataProvider = new DataProviderImpl();
	
	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Produces("application/json")
	public String getResult(){
		String json = "";
		String xml = "";
		
		ResultListHashMapObject resultListHashMap = computeActivities(Arrays.asList(1L),null,1434025148950L,
				1434025149951L,10L,Arrays.asList("test"),Arrays.asList(1L,2L),null);
		logger.info(resultListHashMap.toString());
		try {
			JAXBContext jc = JAXBContext.newInstance(ResultListHashMapObject.class);
			Marshaller m = jc.createMarshaller();
			ByteArrayOutputStream bost = new ByteArrayOutputStream();
			m.marshal( resultListHashMap, bost );
			xml = bost.toString();
	        JSONObject xmlJSONObj = XML.toJSONObject(xml);
	        json = xmlJSONObj.toString(); 
		} catch (Exception e) {
			logger.error("Error converting result list to json", e);
		}
		return json;
	}

	private ED_Context getDemoContext() {
		Set<ED_Context> contexts = dataProvider.getCourses();
		ED_Context context = null;
	    for (Iterator<ED_Context> it = contexts.iterator(); it.hasNext(); ) {
	    	context = it.next();
	    }
		return context;
	}

	public ResultListHashMapObject computeActivities(final List<Long> courses,
			List<Long> users,
			final Long startTime,
			final Long endTime,
			final Long resolution,
			final List<String> resourceTypes,
			List<Long> gender,
			List<Long> learningObjects){

		final Map<Long, ResultListLongObject> result = new HashMap<Long, ResultListLongObject>();
		final Map<Long, HashMap<Integer, Set<Long>>> userPerResStep = new HashMap<Long, HashMap<Integer, Set<Long>>>();

		validateTimestamps(startTime, endTime, resolution);

		// Calculate size of time intervalls
		final double intervall = (endTime - startTime) / (resolution);
		
		initializeVariables(userPerResStep,result,courses,resolution,resourceTypes);
		
		sumActivities(userPerResStep,result,resolution,resourceTypes, startTime, intervall);
		
		copyUserPerResStepIntoResult(userPerResStep,result,courses,resolution);
		
		final ResultListHashMapObject resultObject = createReturnObject(result);
		
		// Alias keys from StudentHelper class are removed.

		return resultObject;
	}
	
	private ResultListHashMapObject createReturnObject(Map<Long, ResultListLongObject> result) {
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

	private void copyUserPerResStepIntoResult(Map<Long, HashMap<Integer, Set<Long>>> userPerResStep, Map<Long, ResultListLongObject> result, List<Long> courses, Long resolution) {
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
	}

	private void sumActivities(Map<Long, HashMap<Integer, Set<Long>>> userPerResStep, 
			Map<Long, ResultListLongObject> result, 
			Long resolution, 
			List<String> resourceTypes, 
			Long startTime, 
			double intervall) {

		ED_Context context = getDemoContext();
		
		for (ED_Activity activity : context.getActivities("test",new Date(),new Date()))
		{
			logger.info("Activity: "+ activity.getObject()+ "at "+activity.getTime()+ "added.");
			boolean isInRT = false;
			if ((resourceTypes != null) && (resourceTypes.size() > 0) && resourceTypes.contains(activity.getObject().getType()))
			{
				isInRT = true;
			}
			if ((resourceTypes == null) || (resourceTypes.size() == 0) || isInRT)
			{
				Integer pos = new Double((activity.getTime() - startTime) / intervall).intValue();
				if (pos > (resolution - 1)) {
					pos = resolution.intValue() - 1;
				}
				result.get(activity.getContext().hashCode()).getElements()
							.set(pos, result.get(activity.getContext().hashCode()).getElements().get(pos) + 1);
				if (userPerResStep.get(activity.getContext().hashCode()).get(pos) == null)
				{
					final Set<Long> s = new HashSet<Long>();
					s.add((long)activity.getPerson().hashCode());
					userPerResStep.get(activity.getContext().hashCode()).put(pos, s);
				} else {
					userPerResStep.get(activity.getContext().hashCode()).get(pos).add((long)activity.getPerson().hashCode());
				}
			}
		}
	}

	private void initializeVariables(
			Map<Long, HashMap<Integer, Set<Long>>> userPerResStep,
			Map<Long, ResultListLongObject> result, 
			List<Long> courses, 
			Long resolution, 
			List<String> resourceTypes ) {
		ED_Context context = getDemoContext();
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
		
	}

	private void validateTimestamps(Long startTime, Long endTime, Long resolution) {
		validateTimestamps(startTime, endTime);
		if (resolution == null) {
			throw new BadRequestException("Missing resolution parameter.");
		}
		if (resolution <= 0) {
			throw new BadRequestException("Invalid resolution: resolution is a negative value.");
		}
	}
	
	protected void validateTimestamps(Long startTime, Long endTime) {
		if (startTime == null) {
			throw new BadRequestException("Missing start time parameter.");
		}
		if (endTime == null) {
			throw new BadRequestException("Missing end time parameter.");
		}
		if (startTime >= endTime) {
			throw new BadRequestException("Invalid start time: start time exceeds end time.");
		}
	}
}
