package de.lemo.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature;

@Component(immediate = true, metatype = false)
@Service(Application.class)
@ApplicationPath("/rest2")
public class RestApplicationComponent2 extends ResourceConfig {

	public RestApplicationComponent2() {

		packages("de.lemo.rest");

		register(MustacheMvcFeature.class);

		// property(MustacheMvcFeature.TEMPLATE_BASE_PATH, "/templates");

	}   

}
