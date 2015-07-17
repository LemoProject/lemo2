package org.lemo2.analysis.performance;

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

import org.lemo2.rest.api.WebResource;
import org.lemo2.tools.api.AnalyticsTool;

@Component
@Provides
@Instantiate
@Singleton
@Path("tools/performance")
public class PerformanceAnalysis implements WebResource, AnalyticsTool {

	private static final Logger logger = LoggerFactory.getLogger(PerformanceAnalysis.class);

	@ServiceProperty(name = "lemo.tool.id")
	private String id = "performance";

	@ServiceProperty(name = "lemo.tool.name")
	private String name = "Performance";

	@ServiceProperty(name = "lemo.tool.description.short")
	private String descriptionShort = "Shows the distribution of grades among graded learning objects.";

	@ServiceProperty(name = "lemo.tool.description.long")
	private String descriptionLong = "Shows the distribution of grades among graded learning objects.";

	@ServiceProperty(name = "lemo.tool.scripts")
	private final List<String> scripts;
	{
		scripts = new ArrayList<>();
		scripts.add("js/Performance.js");
		scripts.add("js/PerformanceData.js");
		scripts.add("js/nv.d3.js");
	}

	@ServiceProperty(name = "lemo.tool.image.icon.monochrome")
	private String iconMonochrome = "img/icon-monochrome.svg";

	@ServiceProperty(name = "lemo.tool.image.icon.color")
	private String iconColor = "img/icon-color.svg";

	@ServiceProperty(name = "lemo.tool.image.preview")
	private String imagePreview = "img/preview.png";

}
