/**
 * File ./src/main/java/de/lemo/dms/db/mapping/Task.java
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
 * File ./main/java/de/lemo/dms/db/mapping/Task.java
 * Date 2014-02-04
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.mapping;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.lemo.dms.db.mapping.abstractions.ILearningObject;
import de.lemo.dms.db.mapping.abstractions.IMapping;
import de.lemo.dms.db.mapping.abstractions.IRatedObject;

/** 
 * This class represents the table task. 
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "lemo_assessment")
public class Assessment implements IMapping, ILearningObject, IRatedObject{

	private long id;
	private String title;
	private AssessmentType type;
	private Assessment parent;
	private String url;
	private double maxGrade;
	
	private Set<CourseAssessment> courseTasks = new HashSet<CourseAssessment>();
	private Set<AssessmentUser> taskUsers = new HashSet<AssessmentUser>();
	private Set<AssessmentLog> taskLogs = new HashSet<AssessmentLog>();
	
	@Override
	public boolean equals(final IMapping o) {
		if (!(o instanceof Assessment)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof Assessment)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int)id;
	}

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Lob
	@Column(name="title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="type_id")
	public AssessmentType getType() {
		return type;
	}

	public void setType(AssessmentType type) {
		this.type = type;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent_id")
	public Assessment getParent() {
		return parent;
	}

	public void setParent(Assessment parent) {
		this.parent = parent;
	}
	
	/**
	 * standard setter for the attribute course_resource.
	 * 
	 * @param courseResources
	 *            a set of entries in the course_resource table which relate the resource to the courses
	 */
	public void setCourseTasks(final Set<CourseAssessment> courseTasks) {
		this.courseTasks = courseTasks;
	}

	/**
	 * standard getter for the attribute.
	 * 
	 * @return a set of entries in the course_resource table which relate the resource to the courses
	 */
	@OneToMany(mappedBy="task")
	public Set<CourseAssessment> getCourseTasks() {
		return this.courseTasks;
	}

	/**
	 * standard add method for the attribute course_resource.
	 * 
	 * @param courseResource
	 *            this entry will be added to the list of course_resource in this resource
	 */
	public void addCourseTask(final CourseAssessment courseTask) {
		this.courseTasks.add(courseTask);
	}
	
	public void setTaskUsers(final Set<AssessmentUser> taskUsers) {
		this.taskUsers = taskUsers;
	}


	@OneToMany(mappedBy="task")
	public Set<AssessmentUser> getTaskUsers() {
		return this.taskUsers;
	}

	public void addTaskUser(final AssessmentUser taskUsers) {
		this.taskUsers.add(taskUsers);
	}

	/**
	 * @return the taskLogs
	 */
	@OneToMany(mappedBy="task")
	public Set<AssessmentLog> getTaskLogs() {
		return taskLogs;
	}

	/**
	 * @param taskLogs the taskLogs to set
	 */
	public void setTaskLogs(Set<AssessmentLog> taskLogs) {
		this.taskLogs = taskLogs;
	}
	
	public void addTaskLog(AssessmentLog taskLog)
	{
		this.taskLogs.add(taskLog);
	}

	/**
	 * @return the maxGrade
	 */
	@Column(name="maxgrade")
	public Double getMaxGrade() {
		return maxGrade;
	}

	/**
	 * @param maxGrade the maxGrade to set
	 */
	public void setMaxGrade(double maxGrade) {
		this.maxGrade = maxGrade;
	}
	
	public void setType(final String name, final Map<String, AssessmentType> taskTypes,
			final Map<String, AssessmentType> oldTaskTypes) {

		if (taskTypes.get(name) != null)
		{
			this.type = taskTypes.get(name);
			taskTypes.get(name).addTask(this);
		}
		if ((this.type == null) && (oldTaskTypes.get(name) != null))
		{
			this.type = oldTaskTypes.get(name);
			oldTaskTypes.get(name).addTask(this);
		}
	}

	@Override
	@Transient
	public String getLOType() {
		return this.getType().getType();
	}
	
	/**
	 * @return the url
	 */
	@Lob
	@Column(name="url")
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
