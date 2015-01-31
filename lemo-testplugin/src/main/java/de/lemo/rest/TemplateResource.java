package de.lemo.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Template;

@Path("/get")
public class TemplateResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "get some REST?";
	}
  
	String foo = "dsfdf"; 
      
	@Path("tmpl") 
	@GET 
	@Template(name = "/test.mustache")
	public String test2() { 
		// return new Viewable("/test.mustache",this);
		return "no tmpl";
	}

	// @Path("tmpl")
	// @GET
	// public Viewable test2() {
	// return new Viewable("/test.mustache", this);
	// }

}
