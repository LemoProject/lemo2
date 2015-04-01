package de.lemo.webapp;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.osgi.service.component.annotations.Component;

import de.lemo.rest.api.WebResource;

@Component(immediate = true)
@Singleton
@Path("/tools")
public class ServiceCatalog implements WebResource {

	// private Map<WebResource, Map<String, String>> analyses = new HashMap<>();

	// @Reference(cardinality = ReferenceCardinality.MULTIPLE)
	// protected void setHttpService(WebResource analysis) {
	// analyses.put(analysis, config);
	// }
	//
	// protected void unsetHttpService(WebResource analysis) {
	// analyses.remove(analysis);
	// }

	// @Reference(aggregate = true, optional = true)
	// void bindAnalysis(Analysis analysis, Map<String, String> config) {
	// analyses.put(analysis, config);
	// }
	//
	// @Unbind(aggregate = true, optional = true)
	// void unbindAnalysis(Analysis analysis, Map<String, String> config) {
	// analyses.remove(analysis);
	// }

	@GET
	@Path("/")
	public String pluginList() {
		String r = "Analyses<br><br>";
		// for (Entry<Analysis, Map<String, String>> entry :
		// analyses.entrySet()) {
		// r += "<b>" + entry.getKey().getClass() + "</b><br/>";
		// for (Entry<String, String> configEntry : entry.getValue().entrySet())
		// {
		// r += "" + entry.getKey() + " = " + entry.getValue() + "<br/>";
		// }
		// r += "<br/>";
		// }

		return r;
	}
}