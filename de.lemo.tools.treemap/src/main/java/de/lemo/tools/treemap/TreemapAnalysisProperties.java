package de.lemo.tools.treemap;

import org.apache.felix.ipojo.annotations.ServiceProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lemo.tools.api.AnalyticsTool;

public class TreemapAnalysisProperties implements AnalyticsTool {
	
	private static final Logger logger = LoggerFactory.getLogger(TreemapAnalysisProperties.class);
	
	@ServiceProperty(name = "lemo.tool.id")
	private String id = "treemap";

	@ServiceProperty(name = "lemo.tool.name")
	private String name = "Tree Map";
	
	
	@ServiceProperty(name = "lemo.tool.description.short")
	private String descriptionShort = "Ladida.";

	@ServiceProperty(name = "lemo.tool.description.long")
	private String descriptionLong = "LLLLAAAADDDDIIIIDDDDAAAA.";
	
	@ServiceProperty(name = "lemo.tool.image.icon.monochrome")
	private String iconMonochrome = "img/icon-monochrome.svg";

	@ServiceProperty(name = "lemo.tool.image.icon.color")
	private String iconColor = "img/icon-color.svg";

	@ServiceProperty(name = "lemo.tool.image.preview")
	private String imagePreview = "img/preview.png";

}
