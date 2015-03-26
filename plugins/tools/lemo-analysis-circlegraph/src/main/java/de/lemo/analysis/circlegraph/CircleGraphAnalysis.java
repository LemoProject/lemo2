package de.lemo.analysis.circlegraph;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.jvnet.hk2.annotations.Service;

@Component
@Service
@Singleton
@Path("analysis/circle-graph")
public class CircleGraphAnalysis  {

	@Property(name = "lemo.tool.name")
	private String name = "Circle Graph";

	// @Property(name = "lemo.tool.script")
	// @Uri("analysis/circle-graph/script")
	// private String script() {
	// return "rewr";
	// }

	Application app;

	@Context
	public UriInfo uriInfo;

	@GET
	public String test() {
		return "ok!";
	}

	@GET
	@Produces("text/html")
	public String t(@QueryParam("n") String name) {
		return "Fooo " + name + "-" + this + " - " + app;
	}

	@GET
	@Path("js.png")
	@Produces("image/png")
	public Response test2() {
		String name = "/img/preview.png";
		InputStream is = getClass().getResourceAsStream(name);
		if (is == null) {
			throw new WebApplicationException(404);
		}

		String mt = URLConnection.guessContentTypeFromName(name);// new MimetypesFileTypeMap().getContentType(name);
		System.out.println("XXXXXXXX " + mt);
		return Response.ok(new InputStreamReader(is), mt).build();
	}

	public final static String PATH = "circle-graph";

//	private final Map<String, String> properties;
//	{
		// TODO i18n
//		Map<String, String> properties = new HashMap<String, String>();
//		properties.put(Analysis.DESCRIPTION_SHORT, "Zeigt Navigationsschritte der Nutzer zwischen einzelnen Lernobjekten.");
//		properties.put(Analysis.DESCRIPTION_LONG, "Mit der Analyse „Circle Graph“ können Sie einen Einblick in das Navigationsverhalten der Nutzer erhalten, "
//				+ "insbesondere in die Reihenfolge, in der Studierende die Lernobjekte aufrufen.");
//
//		// properties.put(Analysis.ICON_COLOR, "img/icon-color.svg");
//		// properties.put(Analysis.ICON_MONOCHROME, "img/icon-monochrome.svg");
//		properties.put(Analysis.IMAGE_PREVIEW, "img/preview.png");
//		this.properties = Collections.unmodifiableMap(properties);
//	}

	private final List<String> scripts;
	{
		List<String> scripts = new ArrayList<String>();
		scripts.add("js/circlegraph.js");
		scripts.add("js/fake_data.js");
		this.scripts = Collections.unmodifiableList(scripts);
	}

	// @Override
	// public String getPath() {
	// try {
	// return "uri: " + uriInfo.getAbsolutePath();
	// } catch (Exception e) {
	// return "url fail";
	// }
	// }
	//
	// @Override
	// public String getName() {
	// return "Circle Graph";
	// }
	//
	// @Override
	// public Map<String, String> getProperties() {
	// return properties;
	// }
	//
	// @Override
	// public List<String> getScriptPaths() {
	// return scripts;
	// }

}
