/**
 * File ./src/main/java/de/lemo/dms/processing/resulttype/ResultListResourceRequestInfo.java
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
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListResourceRequestInfo.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents a list for ResourceRequestInfo objects which is use to transfer data from
 * the dms to the app-server
 * @author Sebastian Schwarzrock
 *
 */
@XmlRootElement
public class ResultListResourceRequestInfo {

	private List<ResourceRequestInfo> rri;

	public ResultListResourceRequestInfo() {
		this.rri = new ArrayList<ResourceRequestInfo>();
	}

	public ResultListResourceRequestInfo(final List<ResourceRequestInfo> resourceRequestInfos) {
		this.rri = resourceRequestInfos;
	}

	@XmlElement
	public List<ResourceRequestInfo> getResourceRequestInfos() {
		return this.rri;
	}

	public void setRoles(final List<ResourceRequestInfo> resourceRequestInfos) {
		this.rri = resourceRequestInfos;
	}

	public void add(final ResourceRequestInfo rri)
	{
		this.rri.add(rri);
	}

	public void addAll(final Collection<ResourceRequestInfo> rri)
	{
		this.rri.addAll(rri);
	}
}
