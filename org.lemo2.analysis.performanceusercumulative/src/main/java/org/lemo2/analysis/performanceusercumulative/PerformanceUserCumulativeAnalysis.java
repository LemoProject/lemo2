package org.lemo2.analysis.performanceusercumulative;

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
@Path("tools/performanceusercumulative")
public class PerformanceUserCumulativeAnalysis implements WebResource, AnalyticsTool {

	private static final Logger logger = LoggerFactory.getLogger(PerformanceUserCumulativeAnalysis.class);

	@ServiceProperty(name = "lemo.tool.id")
	private String id = "performanceusercumulative";

	@ServiceProperty(name = "lemo.tool.name")
	private String name = "Performance User Cumulative";

	@ServiceProperty(name = "lemo.tool.description.short")
	private String descriptionShort = "Shows the range of grades for each student.";

	@ServiceProperty(name = "lemo.tool.description.long")
	private String descriptionLong = "Shows the range of grades for each student.";

	@ServiceProperty(name = "lemo.tool.scripts")
	private final List<String> scripts;
	{
		scripts = new ArrayList<>();
		scripts.add("js/BoxPlot_Lib.js");		
		scripts.add("js/BoxPlot.js");
		scripts.add("js/PerformanceUserCumulativeData.js");
//		scripts.add("js/d3.v2.min.js");
	}

	@ServiceProperty(name = "lemo.tool.image.icon.monochrome")
	private String iconMonochrome = "img/icon-monochrome.svg";

	@ServiceProperty(name = "lemo.tool.image.icon.color")
	private String iconColor = "img/icon-color.svg";

	@ServiceProperty(name = "lemo.tool.image.preview")
	private String imagePreview = "img/preview.png";

}
