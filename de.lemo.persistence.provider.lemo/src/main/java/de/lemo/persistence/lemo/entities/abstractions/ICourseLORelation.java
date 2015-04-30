/**
 * File ./src/main/java/de/lemo/dms/db/mapping/abstractions/ICourseLORelation.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2015
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
 * File ./main/java/de/lemo/dms/db/mapping/abstractions/ICourseLORelation.java
 * Date 2013-03-14
 * Project Lemo Learning Analytics
 */


package de.lemo.persistence.lemo.entities.abstractions;

import de.lemo.persistence.lemo.entities.Course;

/**
 * Interface for all Course-LearningObject-Association-Classes
 * @author Sebastian Schwarzrock
 */
public interface ICourseLORelation {
	
	long getId();
	
	Course getCourse();
	
	ILearningObject getLearning();	
	
	String getType();
	

}
