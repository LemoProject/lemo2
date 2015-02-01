package de.lemo.webapp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.glassfish.jersey.server.mvc.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateModelException;

@Path("/")
public class Home {

	private static final Logger logger = LoggerFactory.getLogger(Home.class);


	@GET
	@Template(name = "/home.html")
	public Object list() throws TemplateModelException {

		return true;
	}

}
