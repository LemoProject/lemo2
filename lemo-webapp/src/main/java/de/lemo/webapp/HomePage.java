package de.lemo.webapp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.mvc.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateModelException;

@Path("/")
public class HomePage {

	private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

	@GET
	@Template(name = "/home.html")
	public Object homePage() throws TemplateModelException {
		return true;
	}

}
