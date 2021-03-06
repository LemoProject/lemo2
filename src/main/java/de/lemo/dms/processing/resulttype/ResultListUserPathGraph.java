/**
 * File ./src/main/java/de/lemo/dms/processing/resulttype/ResultListUserPathGraph.java
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
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListUserPathGraph.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents a list for PathGraph objects which is use to transfer data from
 * the dms to the app-server
 * @author Sebastian Schwarzrock
 *
 */
@XmlRootElement
public class ResultListUserPathGraph {

	private List<UserPathLink> links;
	private List<UserPathNode> nodes;

	@XmlElement
	public final String type = this.getClass().getSimpleName();

	public ResultListUserPathGraph() {
	}

	public ResultListUserPathGraph(final List<UserPathNode> nodes, final List<UserPathLink> links)
	{
		this.nodes = nodes;
		this.links = links;
	}

	@XmlElement
	public List<UserPathLink> getLinks()
	{
		return this.links;
	}

	public void setLinks(final List<UserPathLink> links)
	{
		this.links = links;
	}

	@XmlElement
	public List<UserPathNode> getNodes() {
		return this.nodes;
	}

	public void setNodes(final List<UserPathNode> nodes) {
		this.nodes = nodes;
	}
}
