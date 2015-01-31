package de.lemo.webapp;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature;

@ApplicationPath("/lemo")
@Component(immediate = true, metatype = false)
@Service(Application.class)
public class LemoWebApplication extends ResourceConfig {

	public LemoWebApplication() {

		packages("de.lemo.webapp");
		register(MustacheMvcFeature.class);

	}

}
