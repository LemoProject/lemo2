package de.lemo.analysis.activitygraph;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.ServiceProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lemo.rest.api.WebResource;
import de.lemo.tools.api.AnalyticsTool;

@Component
@Provides
@Instantiate
@Singleton
@Path("tools/activitygraph")
public class ActivityGraphAnalysis implements WebResource, AnalyticsTool {

	private static final Logger logger = LoggerFactory.getLogger(ActivityGraphAnalysis.class);

	@ServiceProperty(name = "lemo.tool.id")
	private String id = "activitygraph";

	@ServiceProperty(name = "lemo.tool.name")
	private String name = "Activity Graph";

	@ServiceProperty(name = "lemo.tool.description.short")
	private String descriptionShort = "Shows the users' navigational steps between learning objects within the course.";

	@ServiceProperty(name = "lemo.tool.description.long")
	private String descriptionLong = "Shows the users' navigational steps between learning objects within the course.";

	@ServiceProperty(name = "lemo.tool.scripts")
	private final List<String> scripts;
	{
		scripts = new ArrayList<>();
		scripts.add("js/ActivityGraph.js");
		scripts.add("js/ActivityGraphData.js");
		scripts.add("js/jquery.tipsy.js");
	}

	@ServiceProperty(name = "lemo.tool.image.icon.monochrome")
	private String iconMonochrome = "img/icon-monochrome.svg";

	@ServiceProperty(name = "lemo.tool.image.icon.color")
	private String iconColor = "img/icon-color.svg";

	@ServiceProperty(name = "lemo.tool.image.preview")
	private String imagePreview = "img/preview.png";

}
