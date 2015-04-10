package de.lemo.webapp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.glassfish.jersey.server.mvc.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lemo.rest.api.WebResource;
import freemarker.template.TemplateModelException;

@Path(WebappResourceConfig.HOME_PAGE)
public class HomePage implements WebResource {

	private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

	@GET
	@Template(name = "/home.html")
	public Object homePage() throws TemplateModelException {
		return true;
	}

}
