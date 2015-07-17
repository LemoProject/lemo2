package org.lemo2.webapp;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.mvc.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateModelException;

@Path(WebappConfig.ANALYTICS_PAGE)
public class AnalyticsToolPage {

	private static final Logger logger = LoggerFactory.getLogger(AnalyticsToolPage.class);

	@Inject
	private WebappConfig webApp;

	@GET
	@Template(name = "/tools.html")
	public Object toolsList() throws TemplateModelException {
		return true;
	}

	@GET
	@Path("{tool-id}")
	@Template(name = "/tool.html")
	public Object get(@PathParam("tool-id") String toolId) {
		Map<String, Object> tool = webApp.getToolProperties(toolId);
		if (tool == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		Map<String, Object> templateVariables = new HashMap<>();
		templateVariables.put("tool", tool);
		return templateVariables;
	}
}
