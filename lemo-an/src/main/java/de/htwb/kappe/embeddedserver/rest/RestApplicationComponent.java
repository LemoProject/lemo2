package de.htwb.kappe.embeddedserver.rest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Component(provides = Application.class)
@ApplicationPath("/rest2")
public class RestApplicationComponent extends Application {

	static final Logger logger = LoggerFactory.getLogger(RestApplicationComponent.class);

	public RestApplicationComponent() {

		// register(OsgiMustacheMvcFeature.class);

		// register(CopyOfHelloWorldResource.class);
	}

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(CopyOfHelloWorldResource.class);
		return classes;
	}

}
