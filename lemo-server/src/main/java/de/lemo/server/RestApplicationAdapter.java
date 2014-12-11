package de.lemo.server;

import java.lang.reflect.Field;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.Servlet;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.felix.dm.Component;
import org.apache.felix.dm.annotation.api.AdapterService;
import org.apache.felix.dm.annotation.api.Inject;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AdapterService(adapteeService = Application.class, provides = Servlet.class, added = "added")
public class RestApplicationAdapter extends ServletContainer {

	private static final Logger logger = LoggerFactory.getLogger(RestApplicationAdapter.class);

	@Inject
	private Component component;

	void added(Application app) {
		ResourceConfig resourceConfig = resourceConfigForApplication(app);
		setResourceConfig(resourceConfig);
	}

	private ResourceConfig resourceConfigForApplication(Application app) {
		ResourceConfig resourceConfig = ResourceConfig.forApplication(app);
		resourceConfig.setApplicationName(app.getClass().getName());

		// add everything from the same package
		resourceConfig = resourceConfig.packages(true, app.getClass().getPackage().getName());

		Dictionary appProperties = createProperties(app);

		String basePath = "" + appProperties.get("alias");
		logger.info("Adding JAX-RS application " + resourceConfig.getApplicationName());
		logger.info(resourceConfig.getClasses().size() + " classes, alias: " + basePath);
//		for (Class<?> resourceClass : resourceConfig.getClasses()) {
//			Resource resource;
//			ClassLoader myClassLoader = getClass().getClassLoader();
//			ClassLoader originalContextClassLoader = Thread.currentThread().getContextClassLoader();
//			try {
//				Thread.currentThread().setContextClassLoader(myClassLoader);
//				resource = Resource.from(resourceClass);
//			} finally {
//				Thread.currentThread().setContextClassLoader(originalContextClassLoader);
//			}
//
//			logger.info(basePath + resource.getPath() + "\t" + resource.getName());
//		}
		Dictionary serviceProperties = component.getServiceProperties();
		serviceProperties.put("alias", basePath);
		component.setServiceProperties(serviceProperties);

		return resourceConfig;
	}

	private Dictionary createProperties(Application app) {

		Dictionary props = new Hashtable();

		String path = "/";
		ApplicationPath applicationPathAnnotation = app.getClass().getAnnotation(ApplicationPath.class);
		if (applicationPathAnnotation != null) {
			path = applicationPathAnnotation.value();
		}
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		props.put("alias", path);

		props.put(ServerProperties.METAINF_SERVICES_LOOKUP_DISABLE, true);
		props.put(ServerProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true);
		props.put(ServerProperties.WADL_FEATURE_DISABLE, true);

		return props;
	}

	private void setResourceConfig(ResourceConfig resourceConfig) {
		/*
		 * The ServletContainer constructor takes the ResourceConfig as constructor argument, but the Application which
		 * is used to create the ResourceConfig is not available at the time when this AdapterService is created, nor
		 * when the factory method is called. Reflections are the only way to set the ResourceConfig.
		 */
		try {
			Field resourceConfigField = ServletContainer.class.getDeclaredField("resourceConfig");
			resourceConfigField.setAccessible(true);
			resourceConfigField.set(this, resourceConfig);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// could happen if the ServletContainer implementation gets changes
			throw new RuntimeException("Application->ServletContainer Adapter initialization failed.", e);
		}
	}
}
