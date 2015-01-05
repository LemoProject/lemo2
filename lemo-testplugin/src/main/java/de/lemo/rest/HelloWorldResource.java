package de.lemo.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 
 
@Path("/r1")
@Produces(MediaType.TEXT_PLAIN)
public class HelloWorldResource   {

	@GET
	public String sayHello() {
		return "Hello, Resource";
	}
 
}