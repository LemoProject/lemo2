package de.htwb.kappe.embeddedserver.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.felix.dm.annotation.api.Component;
 

@Component
@Path("/r2")
@Produces(MediaType.TEXT_PLAIN)
public class HelloWorldResource   {

	@GET
	public String sayHello() {
		return "Hello, Resource";
	}

}