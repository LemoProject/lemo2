package de.lemo.webapp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.mvc.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lemo.rest.api.WebResource;
import freemarker.template.TemplateModelException;

@Path(WebappResourceConfig.ANALYTICS_PAGE)
public class AnalyticsPage implements WebResource {

	private static final Logger logger = LoggerFactory.getLogger(AnalyticsPage.class);

	@Context
	private WebappResourceConfig webApplication;

	@GET
	@Template(name = "/analysis-list.html")
	public Object list() throws TemplateModelException {
		return true;
	}

	// @GET
	// @Path("{analysis-id}")
	// @Template(name = "/analysis.html")
	// public Object get(@PathParam("analysis-id") String analysisId) {
	// Analysis analysis = webApplication.getAnalyses().get(analysisId);
	// if (analysis == null) {
	// throw new WebApplicationException(Status.NOT_FOUND);
	// }
	// Map<String, Object> vars = new HashMap<>();
	// vars.put("analysis", webApplication.getAnalyses().get(analysisId));
	// return vars;
	// }
}
