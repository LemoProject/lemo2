package de.lemo.webapp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.mvc.Template;

@Path("/analysis")
public class AnalysisResource {

	@Context
	LemoResourceConfig webApplication;

	@GET
	@Template(name = "/analysis-list.mustache")
	public Object list() {
		return webApplication.getAnalyses();
	}

	@GET
	@Path("{analysis-id}")
	@Template(name = "/analysis.mustache")
	public Object get(@PathParam("analysis-id") String analysisId) {
		return webApplication.getAnalyses().get(analysisId);
	}
}
