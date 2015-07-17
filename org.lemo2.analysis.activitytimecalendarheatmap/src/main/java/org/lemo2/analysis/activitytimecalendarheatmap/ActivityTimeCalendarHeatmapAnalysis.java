package org.lemo2.analysis.activitytimecalendarheatmap;

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

import org.lemo2.analysis.api.WebResource;
import org.lemo2.webapplication.api.AnalyticsTool;

@Component
@Provides
@Instantiate
@Singleton
@Path("tools/activitytimecalendarheatmap")
public class ActivityTimeCalendarHeatmapAnalysis implements WebResource, AnalyticsTool {

	private static final Logger logger = LoggerFactory.getLogger(ActivityTimeCalendarHeatmapAnalysis.class);

	@ServiceProperty(name = "lemo.tool.id")
	private String id = "activitytimecalendarheatmap";

	@ServiceProperty(name = "lemo.tool.name")
	private String name = "Activity Time Calendar Heatmap";

	@ServiceProperty(name = "lemo.tool.description.short")
	private String descriptionShort = "Shows the number of daily activities in a calendar-view.";

	@ServiceProperty(name = "lemo.tool.description.long")
	private String descriptionLong = "Shows the number of daily activities in a calendar-view.";

	@ServiceProperty(name = "lemo.tool.scripts")
	private final List<String> scripts;
	{
		scripts = new ArrayList<>();
		scripts.add("js/ActivityTimeCalendarHeatmap.js");
		scripts.add("js/ActivityTimeCalendarHeatmapData.js");
		scripts.add("js/cal-heatmap.min.js");		
	}

	@ServiceProperty(name = "lemo.tool.image.icon.monochrome")
	private String iconMonochrome = "img/icon-monochrome.svg";

	@ServiceProperty(name = "lemo.tool.image.icon.color")
	private String iconColor = "img/icon-color.svg";

	@ServiceProperty(name = "lemo.tool.image.preview")
	private String imagePreview = "img/preview.png";

}
