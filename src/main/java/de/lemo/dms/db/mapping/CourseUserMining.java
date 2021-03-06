/**
 * File ./src/main/java/de/lemo/dms/db/mapping/CourseUserMining.java
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
 * File ./main/java/de/lemo/dms/db/mapping/CourseUserMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.mapping;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.lemo.dms.db.mapping.abstractions.IMappingClass;

/**
 * This class represents the relationship between courses and user
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "course_user")
public class CourseUserMining implements IMappingClass {

	private long id;
	private CourseMining course;
	private UserMining user;
	private RoleMining role;
	private long enrolStart;
	private long enrolEnd;
	private Long platform;

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof CourseUserMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof CourseUserMining)) {
			return true;
		}
		return false;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the association between course and user
	 */
	@Override
	@Id
	public long getId() {
		return this.id;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the association between course and user
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard setter for the attribute course
	 * 
	 * @param course
	 *            the course in which the user is enroled
	 */
	public void setCourse(final CourseMining course) {
		this.course = course;
	}

	/**
	 * standard getter for the attribute course
	 * 
	 * @return the course in which the user is enroled
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id")
	public CourseMining getCourse() {
		return this.course;
	}

	/**
	 * parameterized setter for the attribute course
	 * 
	 * @param course
	 *            the id of the course in which the user is enroled
	 * @param courseMining
	 *            a list of new added courses, which is searched for the course with the id submitted in the course
	 *            parameter
	 * @param oldCourseMining
	 *            a list of course in the miningdatabase, which is searched for the course with the id submitted in the
	 *            course parameter
	 */
	public void setCourse(final long course, final Map<Long, CourseMining> courseMining,
			final Map<Long, CourseMining> oldCourseMining) {
		if (courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addCourseUser(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourseUser(this);
		}
	}

	/**
	 * standard getter for the attribute enrolstart
	 * 
	 * @return the timestamp when the user was enroled in the course
	 */
	@Column(name="enrolstart")
	public long getEnrolstart() {
		return this.enrolStart;
	}

	/**
	 * standard setter for the attribute enrolstart
	 * 
	 * @param enrolstart
	 *            the timestamp when the user was enroled in the course
	 */
	public void setEnrolstart(final long enrolstart) {
		this.enrolStart = enrolstart;
	}

	/**
	 * standard getter for the attribute enrolend
	 * 
	 * @return the timestamp when the user is not enroled any more
	 */
	@Column(name="enrolend")
	public long getEnrolend() {
		return this.enrolEnd;
	}

	/**
	 * standard setter for the attribute enrolend
	 * 
	 * @param enrolend
	 *            the timestamp when the user is not enroled any more
	 */
	public void setEnrolend(final long enrolend) {
		this.enrolEnd = enrolend;
	}

	/**
	 * standard getter for the attribute user
	 * 
	 * @return the user who is enroled in the course
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public UserMining getUser() {
		return this.user;
	}

	/**
	 * standard setter for the attribute user
	 * 
	 * @param user
	 *            the user who is enroled in the course
	 */
	public void setUser(final UserMining user) {
		this.user = user;
	}

	/**
	 * parameterized setter for the attribute user
	 * 
	 * @param user
	 *            the id of the user who is enroled in the course
	 * @param userMining
	 *            a list of new added users, which is searched for the user with the id submitted in the user parameter
	 * @param oldUserMining
	 *            a list of users in the miningdatabase, which is searched for the user with the id submitted in the
	 *            user parameter
	 */
	public void setUser(final long user, final Map<Long, UserMining> userMining,
			final Map<Long, UserMining> oldUserMining) {
		if (userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addCourseUser(this);
		}
		if ((this.user == null) && (oldUserMining.get(user) != null))
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addCourseUser(this);
		}
	}

	/**
	 * standard setter for the attribute role
	 * 
	 * @param role
	 *            the role which the user has in the course
	 */
	public void setRole(final RoleMining role) {
		this.role = role;
	}

	/**
	 * standard getter for the attribute role
	 * 
	 * @return the role which the user has in the course
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="role_id")
	public RoleMining getRole() {
		return this.role;
	}

	/**
	 * parameterized setter for the attribute role
	 * 
	 * @param role
	 *            the id of the role which the user has in the course
	 * @param roleMining
	 *            a list of new added roles, which is searched for the role with the id submitted in the role parameter
	 * @param oldRoleMining
	 *            a list of roles in the miningdatabase, which is searched for the role with the id submitted in the
	 *            role parameter
	 */
	public void setRole(final long role, final Map<Long, RoleMining> roleMining,
			final Map<Long, RoleMining> oldRoleMining) {
		if (roleMining.get(role) != null)
		{
			this.role = roleMining.get(role);
			roleMining.get(role).addCourseUser(this);
		}
		if ((this.role == null) && (oldRoleMining.get(role) != null))
		{
			this.role = oldRoleMining.get(role);
			oldRoleMining.get(role).addCourseUser(this);
		}
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
