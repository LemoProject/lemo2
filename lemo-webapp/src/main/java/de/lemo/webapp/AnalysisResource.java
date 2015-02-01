package de.lemo.webapp;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.mvc.Template;

import freemarker.template.TemplateModelException;

@Path("analysis")
public class AnalysisResource {

	@Context
	LemoResourceConfig webApplication;

	@GET
	@Template(name = "/analysis-list.html")
	public Object list() throws TemplateModelException {  
		Map<String, Object> r = new HashMap<>();
		r.put("title", "Foo"); 
		r.put("analyses", webApplication.getAnalyses());
		return r;
	}

	@GET
	@Path("{analysis-id}")
	@Template(name = "/analysis.mustache")
	public Object get(@PathParam("analysis-id") String analysisId) {
		return webApplication.getAnalyses().get(analysisId);
	}
}
