/**
 * File ./src/main/java/de/lemo/dms/processing/questions/async/AFrequentPathsBIDE.java
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
 * File ./main/java/de/lemo/dms/processing/questions/QFrequentPathsBIDE.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.questions.async;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ca.pfv.spmf.sequentialpatterns.AlgoBIDEPlus;
import ca.pfv.spmf.sequentialpatterns.SequenceDatabase;
import ca.pfv.spmf.sequentialpatterns.Sequences;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.lemo.dms.core.Clock;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.abstractions.ILogMining;
import de.lemo.dms.processing.AnalysisTask;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.ResultListUserPathGraph;
import de.lemo.dms.processing.resulttype.UserPathLink;
import de.lemo.dms.processing.resulttype.UserPathNode;
import de.lemo.dms.processing.resulttype.UserPathObject;

/**
 * Read ther path data from the database and using the Bide algorithm to generates the frequent paths
 * 
 * @author Sebastian Schwarzrock
 */
public class AFrequentPathsBIDE extends AnalysisTask {

	private Map<String, ILogMining> idToLogM = new HashMap<String, ILogMining>();
	private Map<String, ArrayList<Long>> requests = new HashMap<String, ArrayList<Long>>();
	private Map<String, Integer> idToInternalId = new HashMap<String, Integer>();
	private Map<Integer, String> internalIdToId = new HashMap<Integer, String>();

	private final Logger logger = Logger.getLogger(this.getClass());

	private List<Long> courses;
	private List<Long> users;
	private List<String> types;
	private Long minLength;
	private Long maxLength;
	private Double minSup;
	private boolean sessionWise;
	private Long startTime;
	private Long endTime;
	private List<Long> gender;

	public AFrequentPathsBIDE(
			final String taskId,
			final List<Long> courses,
			final List<Long> users,
			final List<String> types,
			final Long minLength,
			final Long maxLength,
			final Double minSup,
			final boolean sessionWise,
			final Long startTime,
			final Long endTime,
			final List<Long> gender) {
		super(taskId);
		this.courses = courses;
		this.users = users;
		this.types = types;
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.minSup = minSup;
		this.sessionWise = sessionWise;
		this.startTime = startTime;
		this.endTime = endTime;
		this.gender = gender;
	}

	public/* ResultListUserPathGraph */Object compute() {

		final List<UserPathNode> nodes = Lists.newArrayList();
		final List<UserPathLink> links = Lists.newArrayList();
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();

		final Session session = dbHandler.getMiningSession();

		if (logger.isDebugEnabled()) {
			StringBuffer buffer = new StringBuffer();
			if (!courses.isEmpty()) {
				buffer.append("Parameter list: Courses: ").append(courses.get(0));
				for (int i = 1; i < courses.size(); i++) {
					buffer.append(", ").append(courses.get(i));
				}
				logger.debug(buffer.toString());
			}
			if (!users.isEmpty())
			{
				logger.debug("Parameter list: Users: " + users.get(0));
				buffer = new StringBuffer();
				for (int i = 1; i < users.size(); i++) {
					buffer.append(", ").append(users.get(i));
				}
				logger.debug(buffer.toString());
			}
			if (!types.isEmpty())
			{
				buffer = new StringBuffer();
				buffer.append("Parameter list: Types: : ").append(types.get(0));
				for (int i = 1; i < types.size(); i++) {
					buffer.append(", ").append(types.get(i));
				}
				logger.debug(buffer.toString());
			}
			if ((minLength != null) && (maxLength != null) && (minLength < maxLength))
			{
				logger.debug("Parameter list: Minimum path length: : " + minLength);
				logger.debug("Parameter list: Maximum path length: : " + maxLength);
			}
			logger.debug("Parameter list: Minimum Support: : " + minSup);
			logger.debug("Parameter list: Session Wise: : " + sessionWise);
			logger.debug("Parameter list: Start time: : " + startTime);
			logger.debug("Parameter list: End time: : " + endTime);
		}

		try {

			final SequenceDatabase sequenceDatabase = new SequenceDatabase();

			if (!sessionWise) {
				sequenceDatabase.loadLinkedList(generateLinkedList(courses, users, types, minLength,
						maxLength, startTime, endTime, session, gender));
			} else {
				sequenceDatabase.loadLinkedList(generateLinkedListSessionBound(courses, users,
						types, minLength, maxLength, startTime, endTime, session, gender));
			}

			final AlgoBIDEPlus algo = new AlgoBIDEPlus(minSup);

			// execute the algorithm
			final Clock c = new Clock();
			final Sequences res = algo.runAlgorithm(sequenceDatabase);
			logger.debug("Time for BIDE-calculation: " + c.get());

			final LinkedHashMap<String, UserPathObject> pathObjects = Maps.newLinkedHashMap();
			Long pathId = 0L;
			for (int i = res.getLevelCount() - 1; i >= 0; i--)
			{
				for (int j = 0; j < res.getLevel(i).size(); j++)
				{
					String predecessor = null;
					final Long absSup = Long.valueOf(res.getLevel(i).get(j).getAbsoluteSupport());
					pathId++;
					logger.debug("New " + i + "-Sequence. Support : "
							+ res.getLevel(i).get(j).getAbsoluteSupport());
					for (int k = 0; k < res.getLevel(i).get(j).size(); k++)
					{

						final String obId = internalIdToId.get(res.getLevel(i).get(j).get(k)
								.getItems().get(0).getId());

						final ILogMining ilo = idToLogM.get(obId);

						final String type = ilo.getClass().getSimpleName();

						final String posId = String.valueOf(pathObjects.size());

						if (predecessor != null)
						{
							pathObjects.put(
									posId,
									new UserPathObject(posId, ilo.getTitle(), absSup, type,
											Double.valueOf(ilo.getDuration()), ilo.getPrefix(), pathId,
											Long.valueOf(requests.get(obId).size()), Long
													.valueOf(new HashSet<Long>(
															requests.get(obId)).size())));

							// Increment or create predecessor edge
							pathObjects.get(predecessor).addEdgeOrIncrement(posId);
						}
						else
						{
							pathObjects.put(
									posId,
									new UserPathObject(posId, ilo.getTitle(), absSup,
											type, Double.valueOf(ilo.getDuration()), ilo.getPrefix(), pathId, Long
													.valueOf(requests.get(obId).size()), Long
													.valueOf(new HashSet<Long>(
															requests.get(obId)).size())));
						}
						predecessor = posId;
					}

				}
			}

			for (final UserPathObject pathEntry : pathObjects.values()) {

				final UserPathObject path = pathEntry;
				path.setWeight(path.getWeight());
				path.setPathId(pathEntry.getPathId());
				nodes.add(new UserPathNode(path, true));
				final String sourcePos = path.getId();

				for (final Entry<String, Integer> linkEntry : pathEntry.getEdges().entrySet()) {
					final UserPathLink link = new UserPathLink();
					link.setSource(sourcePos);
					link.setPathId(path.getPathId());
					link.setTarget(linkEntry.getKey());
					link.setValue(String.valueOf(linkEntry.getValue()));
					links.add(link);
				}
			}

		} catch (final Exception e)
		{
			logger.error(e.getMessage());
		} finally
		{
			requests.clear();
			idToLogM.clear();
			internalIdToId.clear();
			idToInternalId.clear();
		}
		session.close();
		return new ResultListUserPathGraph(nodes, links);
	}

	/**
	 * Generates the necessary list of input-strings, containing the sequences (user paths) for the BIDE+ algorithm
	 * 
	 * @param courses
	 *            Course-Ids
	 * @param users
	 *            User-Ids
	 * @param starttime
	 *            Start time
	 * @param endtime
	 *            End time
	 * @param gender 
	 * @return The path to the generated file
	 */
	@SuppressWarnings("unchecked")
	private LinkedList<String> generateLinkedListSessionBound(final List<Long> courses, List<Long> users,
			final List<String> types, final Long minLength, final Long maxLength, final Long starttime,
			final Long endtime, Session session, List<Long> gender)
	{
		final LinkedList<String> result = new LinkedList<String>();
		try {

			if (users == null || users.size() == 0)
			{
				users = new ArrayList<Long>(StudentHelper.getCourseStudentsAliasKeys(courses, gender).values());
			}
			else
			{
				Map<Long, Long> userMap = StudentHelper.getCourseStudentsAliasKeys(courses, gender);
				List<Long> tmp = new ArrayList<Long>();
				for (int i = 0; i < users.size(); i++)
				{
					tmp.add(userMap.get(users.get(i)));
				}
				users = tmp;
			}

			final Criteria criteria = session.createCriteria(ILogMining.class, "log");
			if (courses.size() > 0) {
				criteria.add(Restrictions.in("log.course.id", courses));
			}
			if (users.size() > 0) {
				criteria.add(Restrictions.in("log.user.id", users));
			}
			criteria.add(Restrictions.between("log.timestamp", starttime, endtime));
			final ArrayList<ILogMining> list = (ArrayList<ILogMining>) criteria.list();

			logger.debug("Read " + list.size() + " logs.");
			final ArrayList<ArrayList<ILogMining>> uhis = new ArrayList<ArrayList<ILogMining>>();

			final HashMap<Long, ArrayList<ILogMining>> logMap = new HashMap<Long, ArrayList<ILogMining>>();
			int max = 0;
			for (int i = 0; i < list.size(); i++)
			{
				if ((list.get(i).getUser() != null) && (list.get(i).getLearnObjId() != null)) {
					if (logMap.get(list.get(i).getUser().getId()) == null)
					{
						final ArrayList<ILogMining> a = new ArrayList<ILogMining>();
						a.add(list.get(i));
						logMap.put(list.get(i).getUser().getId(), a);
						if (logMap.get(list.get(i).getUser().getId()).size() > max) {
							max = logMap.get(list.get(i).getUser().getId()).size();
						}
					}
					else
					{

						logMap.get(list.get(i).getUser().getId()).add(list.get(i));
						Collections.sort(logMap.get(list.get(i).getUser().getId()));
						if (logMap.get(list.get(i).getUser().getId()).size() > max) {
							max = logMap.get(list.get(i).getUser().getId()).size();
						}
						if (list.get(i).getDuration() == -1)
						{
							uhis.add(new ArrayList<ILogMining>(logMap.get(list.get(i).getUser().getId())));
							logMap.remove(list.get(i).getUser().getId());
						}
					}
				}
			}

			uhis.addAll(logMap.values());

			final Integer[] lengths = new Integer[(max / 10) + 1];
			for (int i = 0; i < lengths.length; i++) {
				lengths[i] = 0;
			}

			for (int i = 0; i < uhis.size(); i++)
			{
				lengths[uhis.get(i).size() / 10]++;
			}

			for (int i = 0; i < lengths.length; i++) {
				if (lengths[i] != 0)
				{
					logger.debug("Paths of length " + i + "0 - " + (i + 1) + "0: " + lengths[i]);
				}
			}

			logger.debug("Generated " + uhis.size() + " user histories. Max length @ " + max);

			int z = 0;

			// Write data to output file
			for (final Iterator<ArrayList<ILogMining>> iter = uhis.iterator(); iter.hasNext();)
			{
				final ArrayList<ILogMining> l = iter.next();
				if (l.size() > 5)
				{
					String line = "";
					for (int i = 0; i < l.size(); i++)
					{
						if (idToLogM.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) == null) {
							idToLogM.put(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId(),
									l.get(i));
						}

						if (requests.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) == null)
						{
							final ArrayList<Long> us = new ArrayList<Long>();
							us.add(l.get(i).getUser().getId());
							requests.put(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId(), us);
						} else {
							requests.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()).add(
									l.get(i).getUser().getId());
						}

						line += l.get(i).getPrefix() + "" + l.get(i).getLearnObjId() + " -1 ";
					}
					line += "-2";
					result.add(line);
					z++;
				}

			}
			logger.debug("Wrote " + z + " user histories.");

		} catch (final Exception e)
		{
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * Generates the necessary list of input-strings, containing the sequences (user paths) for the BIDE+ algorithm
	 * 
	 * @param courses
	 *            Course-Ids
	 * @param users
	 *            User-Ids
	 * @param starttime
	 *            Start time
	 * @param endtime
	 *            End time
	 * @param gender2 
	 * @return The path to the generated file
	 */
	@SuppressWarnings("unchecked")
	private LinkedList<String> generateLinkedList(final List<Long> courses, List<Long> users,
			final List<String> types,
			final Long minLength, final Long maxLength, final Long starttime, final Long endtime, Session session, List<Long> gender2)
	{
		final LinkedList<String> result = new LinkedList<String>();
		final boolean hasBorders = (minLength != null) && (maxLength != null) && (maxLength > 0)
				&& (minLength < maxLength);
		final boolean hasTypes = (types != null) && (types.size() > 0);

		Criteria criteria;
		if (users == null || users.size() == 0)
		{
			users = new ArrayList<Long>(StudentHelper.getCourseStudentsAliasKeys(courses, gender).values());
		}
		else
		{
			Map<Long, Long> userMap = StudentHelper.getCourseStudentsAliasKeys(courses, gender);
			List<Long> tmp = new ArrayList<Long>();
			for (int i = 0; i < users.size(); i++)
			{
				tmp.add(userMap.get(users.get(i)));
			}
			users = tmp;
		}

		criteria = session.createCriteria(ILogMining.class, "log");
		if (courses.size() > 0) {
			criteria.add(Restrictions.in("log.course.id", courses));
		}
		if (users.size() > 0) {
			criteria.add(Restrictions.in("log.user.id", users));
		}
		criteria.add(Restrictions.between("log.timestamp", starttime, endtime));
		final ArrayList<ILogMining> list = (ArrayList<ILogMining>) criteria.list();

		logger.debug("Read " + list.size() + " logs.");

		int max = 0;

		final HashMap<Long, ArrayList<ILogMining>> logMap = new HashMap<Long, ArrayList<ILogMining>>();

		for (int i = 0; i < list.size(); i++)
		{
			if ((list.get(i).getUser() != null) && (list.get(i).getLearnObjId() != null)) {
				// If there isn't a user history for this user-id create a new one
				if (logMap.get(list.get(i).getUser().getId()) == null)
				{
					// User histories are saved in an ArrayList of ILogMining-objects
					final ArrayList<ILogMining> a = new ArrayList<ILogMining>();
					// Add current ILogMining-object to user-history
					a.add(list.get(i));
					// Add user history to the user history map
					logMap.put(list.get(i).getUser().getId(), a);
				}
				else
				{
					// Add current ILogMining-object to user-history
					logMap.get(list.get(i).getUser().getId()).add(list.get(i));
					// Sort the user's history (by time stamp)
					Collections.sort(logMap.get(list.get(i).getUser().getId()));
				}
			}
		}

		// Just changing the container for the user histories
		final ArrayList<ArrayList<ILogMining>> uhis = new ArrayList<ArrayList<ILogMining>>();
		int id = 1;
		for (final ArrayList<ILogMining> uLog : logMap.values())
		{

			final ArrayList<ILogMining> tmp = new ArrayList<ILogMining>();
			boolean containsType = false;
			for (final ILogMining iLog : uLog)
			{
				if (idToInternalId.get(iLog.getPrefix() + " " + iLog.getLearnObjId()) == null)
				{
					internalIdToId.put(id, iLog.getPrefix() + " " + iLog.getLearnObjId());
					idToInternalId.put(iLog.getPrefix() + " " + iLog.getLearnObjId(), id);
					id++;
				}
				if (hasTypes) {
					for (final String type : types)
					{
						if (iLog.getClass().getSimpleName().toLowerCase().contains(type.toLowerCase()))
						{
							containsType = true;
							tmp.add(iLog);
							break;
						}

					}
				}
				if (!hasTypes) {
					tmp.add(iLog);
				}
			}
			if ((!hasBorders || ((tmp.size() >= minLength) && (tmp.size() <= maxLength)))
					&& (!hasTypes || containsType))
			{
				uhis.add(tmp);
				if (tmp.size() > max) {
					max = tmp.size();
				}
			}
		}

		// This part is only for statistics - group histories of similar length together and display there
		// respective lengths
		final int dif = 10;
		final Integer[] lengths = new Integer[(max / dif) + 1];
		for (int i = 0; i < lengths.length; i++) {
			lengths[i] = 0;
		}

		for (int i = 0; i < uhis.size(); i++) {
			lengths[uhis.get(i).size() / dif]++;
		}

		for (int i = 0; i < lengths.length; i++) {
			if (lengths[i] != 0)
			{
				logger.debug("Paths of length " + i + "0 - " + (i + 1) + "0: " + lengths[i]);
			}
		}

		logger.debug("Generated " + uhis.size() + " user histories. Max length @ " + max);

		int z = 0;
		// Convert all user histories or "paths" into the format, that is requested by the BIDE-algorithm-class
		for (final ArrayList<ILogMining> l : uhis)
		{
			String line = "";
			for (int i = 0; i < l.size(); i++)
			{
				if (idToLogM.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) == null) {
					idToLogM
							.put(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId(), l.get(i));
				}

				// Update request numbers
				if (requests.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) == null)
				{
					final ArrayList<Long> us = new ArrayList<Long>();
					us.add(l.get(i).getUser().getId());
					requests.put(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId(), us);
				} else {
					requests.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()).add(
							l.get(i).getUser().getId());
				}
				// The id of the object gets the prefix, indicating it's class. This is important for distinction
				// between objects of different ILogMining-classes but same ids
				line += idToInternalId
						.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) + " -1 ";
			}
			line += "-2";
			logger.debug(line);
			result.add(line);
			z++;
		}
		logger.debug("Wrote " + z + " logs.");
		return result;
	}

}
