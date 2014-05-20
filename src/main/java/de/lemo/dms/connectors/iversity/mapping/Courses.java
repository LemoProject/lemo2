/**
 * File ./src/main/java/de/lemo/dms/connectors/iversity/mapping/CourseLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/iversity/mapping/Course_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.iversity.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping class for table Course.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
@Entity
@Table(name = "courses")
public class Courses {

	private long id;
	private String fullname;
	private String subtitle;
	private String title;
	private long startdate;
	private long timecreated;
	private long timemodified;

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Column(name="title")
	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(final String fullname) {
		this.fullname = fullname;
	}

	@Column(name="subtitle")
	public String getSubtitle() {
		return this.subtitle;
	}

	public void setSubtitle(final String subtitle) {
		this.subtitle = subtitle;
	}

	@Column(name="created_at")
	public long getTimecreated() {
		return this.timecreated;
	}

	public void setTimecreated(final long timecreated) {
		this.timecreated = timecreated;
	}

	@Column(name="updated_at")
	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	
	@Column(name="start_date")
	public long getStartdate() {
		return this.startdate;
	}

	public void setStartdate(final long startdate) {
		this.startdate = startdate;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Column(name="title")
	public String getTitle() {
		return this.title;
	}
}
