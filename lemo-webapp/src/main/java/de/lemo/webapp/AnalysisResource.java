package de.lemo.webapp;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.mvc.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateModelException;

@Path("analysis")
public class AnalysisResource {

	private static final Logger logger = LoggerFactory.getLogger(AnalysisResource.class);

	@Context
	private LemoResourceConfig webApplication;

	@GET
	@Template(name = "/analysis-list.html")
	public Object list() throws TemplateModelException {
		return true;
	}

	@GET
	@Path("{analysis-id}")
	@Template(name = "/analysis.html")
	public Object get(@PathParam("analysis-id") String analysisId) {
		Map<String, Object> vars = new HashMap<>();
		vars.put("analysis", webApplication.getAnalyses().get(analysisId));
		return vars;
	}
}
