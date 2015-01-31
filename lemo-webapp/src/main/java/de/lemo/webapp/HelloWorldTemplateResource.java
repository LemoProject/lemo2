package de.lemo.webapp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Template;

@Path("/hello2")
public class HelloWorldTemplateResource {

	@GET
	@Template(name = "/test.mustache")
	public String test2() {
		return "World";
	}

}
