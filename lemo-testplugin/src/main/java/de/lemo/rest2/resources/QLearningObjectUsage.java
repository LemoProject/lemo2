/**
 * File ./src/main/java/de/lemo/dms/processing/questions/QLearningObjectUsage.java
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
 * File ./main/java/de/lemo/dms/processing/questions/QLearningObjectUsage.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.rest2.resources;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Use of learning objects
 * 
 * @author Sebastian Schwarzrock
 */
@Path("learningobjectusage")
public class QLearningObjectUsage {

	/**
	 * Returns a list of resources and their respective statistics of usage.
	 * 
	 * @see ELearningObjectType
	 * @param courses
	 *            List of course-identifiers
	 * @param users
	 *            List of user-identifiers
	 * @param types
	 *            List of learn object types (see ELearnObjType)
	 * @param startTime
	 *            LongInteger time stamp
	 * @param endTime
	 *            LongInteger time stamp
	 * @return
	 */
 
	@POST
	public ResultListResourceRequestInfo compute(@FormParam(MetaParam.COURSE_IDS) final List<Long> courses, @FormParam(MetaParam.USER_IDS) List<Long> users,
			@FormParam(MetaParam.TYPES) final List<String> types, @FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime, @FormParam(MetaParam.GENDER) final List<Long> gender,
			@FormParam(MetaParam.LEARNING_OBJ_IDS) final List<Long> learningObjects) {

		final ResultListResourceRequestInfo result = new ResultListResourceRequestInfo();

		ResultListResourceRequestInfo r = new ResultListResourceRequestInfo();

		for (int i = 0; i < 100; i++) {
			ResourceRequestInfo resourceRequestInfo = new ResourceRequestInfo();
			resourceRequestInfo.setId((long) i);
			resourceRequestInfo.setRequests((long) (1000 * Math.random()));
			resourceRequestInfo.setResolutionSlot(1l);
			resourceRequestInfo.setResourcetype("type");
			resourceRequestInfo.setTitle("Resource-" + i);
			resourceRequestInfo.setUsers((long) (100 * Math.random()));
		}

		return result;
	}
}
