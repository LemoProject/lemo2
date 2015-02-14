package de.lemo.webapp;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.mvc.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lemo.plugin.api.Analysis;
import freemarker.template.TemplateModelException;

@Path(LemoResourceConfig.ANALYTICS_PAGE)
public class AnalyticsPage {

	private static final Logger logger = LoggerFactory.getLogger(AnalyticsPage.class);

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
		Analysis analysis = webApplication.getAnalyses().get(analysisId);
		if (analysis == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		Map<String, Object> vars = new HashMap<>();
		vars.put("analysis", webApplication.getAnalyses().get(analysisId));
		return vars;
	}
}
