package de.lemo.analysis.circlegraph;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.ServiceProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.lemo.rest.api.WebResource;
import de.lemo.tools.api.AnalyticsTool;

@Component
@Provides
@Instantiate
@Singleton
@Path("tools/circlegraph")
public class CircleGraphAnalysis implements WebResource, AnalyticsTool {

	private static final Logger logger = LoggerFactory.getLogger(CircleGraphAnalysis.class);

	@ServiceProperty(name = "lemo.tool.id")
	private String id = "circlegraph";

	@ServiceProperty(name = "lemo.tool.name")
	private String name = "Circle Graph";

	@ServiceProperty(name = "lemo.tool.description.short")
	private String descriptionShort = "Zeigt Navigationsschritte der Nutzer zwischen einzelnen Lernobjekten.";

	@ServiceProperty(name = "lemo.tool.description.long")
	private String descriptionLong = "Mit der Analyse „Circle Graph“ können Sie einen Einblick in das Navigationsverhalten der Nutzer erhalten, "
			+ "insbesondere in die Reihenfolge, in der Studierende die Lernobjekte aufrufen.";

	@ServiceProperty(name = "lemo.tool.scripts")
	private final List<String> scripts;
	{
		scripts = new ArrayList<>();
		scripts.add("js/circlegraph.js");
		scripts.add("js/fake_data.js");
	}

	@ServiceProperty(name = "lemo.tool.image.icon.monochrome")
	private String iconMonochrome = "img/icon-monochrome.svg";

	@ServiceProperty(name = "lemo.tool.image.icon.color")
	private String iconColor = "img/icon-color.svg";

	@ServiceProperty(name = "lemo.tool.image.preview")
	private String imagePreview = "img/preview.png";

}
