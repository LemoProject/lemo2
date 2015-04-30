package de.lemo.analysis.activitylearningobject;

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
public class ActivityLearningObjectAnalysis implements AnalyticsTool {

	private static final Logger logger = LoggerFactory.getLogger(ActivityLearningObjectAnalysis.class);

	@ServiceProperty(name = "lemo.tool.id")
	private String id = "activitylearningobject";

	@ServiceProperty(name = "lemo.tool.name")
	private String name = "Activity Learning Object";

	@ServiceProperty(name = "lemo.tool.description.short")
	private String descriptionShort = "???";

	@ServiceProperty(name = "lemo.tool.description.long")
	private String descriptionLong = "???";

	@ServiceProperty(name = "lemo.tool.scripts")
	private final List<String> scripts;
	{
		scripts = new ArrayList<>();
		scripts.add("js/ActivityLearningObject.js");
		scripts.add("js/fake_data.js");
		scripts.add("js/nv.d3.js");
	}

	@ServiceProperty(name = "lemo.tool.image.icon.monochrome")
	private String iconMonochrome = "img/icon-monochrome.svg";

	@ServiceProperty(name = "lemo.tool.image.icon.color")
	private String iconColor = "img/icon-color.svg";

	@ServiceProperty(name = "lemo.tool.image.preview")
	private String imagePreview = "img/preview.png";

}
