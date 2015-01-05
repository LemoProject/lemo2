package de.lemo.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

@Component(immediate = true, metatype = false)
@Service(value = { Application.class })
@ApplicationPath("/rest1")
public class RestApplicationComponent extends Application {

	public RestApplicationComponent() {

	}

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(HelloWorldResource.class);
		return classes;
	}

}
