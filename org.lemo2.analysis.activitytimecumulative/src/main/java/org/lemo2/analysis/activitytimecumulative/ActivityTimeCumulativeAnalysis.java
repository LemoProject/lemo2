package org.lemo2.analysis.activitytimecumulative;

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
@Path("tools/activitytimecumulative")
public class ActivityTimeCumulativeAnalysis implements WebResource, AnalyticsTool {

	private static final Logger logger = LoggerFactory.getLogger(ActivityTimeCumulativeAnalysis.class);

	@ServiceProperty(name = "lemo.tool.id")
	private String id = "activitytimecumulative";

	@ServiceProperty(name = "lemo.tool.name")
	private String name = "Activitiy Time Cumulative";

	@ServiceProperty(name = "lemo.tool.description.short")
	private String descriptionShort = "Shows the number of activities on specific weekdays / times.";

	@ServiceProperty(name = "lemo.tool.description.long")
	private String descriptionLong = "Shows the number of activities on specific weekdays / times.";

	@ServiceProperty(name = "lemo.tool.scripts")
	private final List<String> scripts;
	{
		scripts = new ArrayList<>();
		scripts.add("js/ActivityTimeCumulative.js");
		scripts.add("js/ActivityTimeCumulativeData.js");
		scripts.add("js/BoxPlot_Lib.js");
	}

	@ServiceProperty(name = "lemo.tool.image.icon.monochrome")
	private String iconMonochrome = "img/icon-monochrome.svg";

	@ServiceProperty(name = "lemo.tool.image.icon.color")
	private String iconColor = "img/icon-color.svg";

	@ServiceProperty(name = "lemo.tool.image.preview")
	private String imagePreview = "img/preview.png";

}
