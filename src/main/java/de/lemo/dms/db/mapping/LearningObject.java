/**
 * File ./src/main/java/de/lemo/dms/db/mapping/Resource.java
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
 * File ./main/java/de/lemo/dms/db/mapping/Resource.java
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

/** 
 * This class represents the table resource. 
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "lemo_learning_object")
public class LearningObject implements IMapping, ILearningObject{

	private long id;
	private String title;
	private LearningObjectType type;
	
	private Set<CourseLearningObject> courseLearningObjects = new HashSet<CourseLearningObject>();	
	private Set<ViewLog> viewLogs = new HashSet<ViewLog>();
	
	public boolean equals(final IMapping o) {
		if (!(o instanceof CollaborativeObject)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof CollaborativeObject)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}


	
	@Id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Lob
	@Column(name="title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="type_id")
	public LearningObjectType getType() {
		return type;
	}
	public void setType(LearningObjectType type) {
		this.type = type;
	}
	
	public void setViewLogs(final Set<ViewLog> viewLogs) {
		this.viewLogs = viewLogs;
	}


	@OneToMany(mappedBy="learningObject")
	public Set<ViewLog> getViewLogs() {
		return this.viewLogs;
	}

	public void addViewLog(final ViewLog viewLog) {
		this.viewLogs.add(viewLog);
	}
	
	/**
	 * standard setter for the attribute course_resource.
	 * 
	 * @param courseLearningObjects
	 *            a set of entries in the course_resource table which relate the resource to the courses
	 */
	public void setCourseLearningObjects(final Set<CourseLearningObject> courseLearningObjects) {
		this.courseLearningObjects = courseLearningObjects;
	}

	/**
	 * standard getter for the attribute.
	 * 
	 * @return a set of entries in the course_resource table which relate the resource to the courses
	 */
	@OneToMany(mappedBy="learningObject")
	public Set<CourseLearningObject> getCourseLearningObjects() {
		return this.courseLearningObjects;
	}

	/**
	 * standard add method for the attribute course_resource.
	 * 
	 * @param courseLearningObject
	 *            this entry will be added to the list of course_resource in this resource
	 */
	public void addCourseLearningObject(final CourseLearningObject courseLearningObject) {
		this.courseLearningObjects.add(courseLearningObject);
	}


	public void setType(final String title, final Map<String, LearningObjectType> learningObjectTypes,
			final Map<String, LearningObjectType> oldLearningObjectTypes) {

		if (learningObjectTypes.get(title) != null)
		{
			this.type = learningObjectTypes.get(title);
			learningObjectTypes.get(title).addLearningObject(this);
		}
		if ((this.type == null) && (oldLearningObjectTypes.get(title) != null))
		{
			this.type = oldLearningObjectTypes.get(title);
			oldLearningObjectTypes.get(title).addLearningObject(this);
		}
	}

	@Override
	@Transient
	public String getLOType() {
		return this.getType().getType();
	}
}