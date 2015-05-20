package de.lemo.tools.treemap;

import java.util.ArrayList;
import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.ServiceProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lemo.tools.api.AnalyticsTool;

@Component
@Provides
@Instantiate
public class TreemapAnalysis implements AnalyticsTool{

	private static final Logger logger = LoggerFactory.getLogger(TreemapAnalysis.class);

	@ServiceProperty(name = "lemo.tool.id")
	private String id = "treemap";

	@ServiceProperty(name = "lemo.tool.name")
	private String name = "Tree Map";
	
	
	@ServiceProperty(name = "lemo.tool.description.short")
	private String descriptionShort = "Compares the number of activities on learning objects within the course.";

	@ServiceProperty(name = "lemo.tool.description.long")
	private String descriptionLong = "Compares the number of activities on learning objects within the course.";
	

	@ServiceProperty(name = "lemo.tool.image.icon.monochrome")
	private String iconMonochrome = "img/icon-monochrome.svg";

	@ServiceProperty(name = "lemo.tool.image.icon.color")
	private String iconColor = "img/icon-color.svg";

	@ServiceProperty(name = "lemo.tool.image.preview")
	private String imagePreview = "img/preview.png";
	
	@ServiceProperty(name = "lemo.tool.scripts")
	private final List<String> scripts;
	{
		scripts = new ArrayList<>();
		scripts.add("js/ActivityLearningObjectTM.js");
		scripts.add("js/fake_data.js");
	}
	
}
