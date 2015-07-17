package org.lemo2.analysis.activitytime;

import java.util.ArrayList;
import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.ServiceProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.lemo2.webapplication.api.AnalyticsTool;

@Component
@Provides
@Instantiate
public class ActivityTimeAnalysis implements AnalyticsTool {

	private static final Logger logger = LoggerFactory.getLogger(ActivityTimeAnalysis.class);

	@ServiceProperty(name = "lemo.tool.id")
	private String id = "activitytime";

	@ServiceProperty(name = "lemo.tool.name")
	private String name = "Activity Time";

	@ServiceProperty(name = "lemo.tool.description.short")
	private String descriptionShort = "Zeigt Aktivitäten der Nutzer in Kursen an.";

	@ServiceProperty(name = "lemo.tool.description.long")
	private String descriptionLong = "Mit der Analyse „Aktivität Zeit (Heatmap)“ erhalten Sie einen Überblick über die Intensität der"
			+ " täglichen Materialnutzung  im  Lauf  der  Zeit  anhand  verschieden intensiv  gefärbten  Farbfeldern  und  können "
			+ " diese  mit  der  Intensität  der  Aktivität in anderen Kursen vergleichen.";

	@ServiceProperty(name = "lemo.tool.scripts")
	private final List<String> scripts;
	{
		scripts = new ArrayList<>();
		scripts.add("js/activitytime.js");
		scripts.add("js/getData.js");
		scripts.add("js/nv.d3.js");
	}

	@ServiceProperty(name = "lemo.tool.image.icon.monochrome")
	private String iconMonochrome = "img/icon-monochrome.svg";

	@ServiceProperty(name = "lemo.tool.image.icon.color")
	private String iconColor = "img/icon-color.svg";

	@ServiceProperty(name = "lemo.tool.image.preview")
	private String imagePreview = "img/preview.png";

}
