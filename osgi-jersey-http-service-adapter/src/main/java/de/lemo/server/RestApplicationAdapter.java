package de.lemo.server;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.security.RolesAllowed;
import javax.servlet.Servlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.felix.ipojo.ComponentFactory;
import org.apache.felix.ipojo.ComponentInstance;
import org.apache.felix.ipojo.ConfigurationException;
import org.apache.felix.ipojo.InstanceManager;
import org.apache.felix.ipojo.MissingHandlerException;
import org.apache.felix.ipojo.UnacceptableConfiguration;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Context;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.whiteboard.Wbp;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.servlet.ServletContainer;
import org.ops4j.pax.web.extender.whiteboard.ResourceMapping;
import org.ops4j.pax.web.extender.whiteboard.runtime.DefaultResourceMapping;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Instantiate
@Component
@Wbp(filter = "(service.pid=*)", onArrival = "onArrival", onDeparture = "onDeparture")
public class RestApplicationAdapter {

	private static final Logger logger = LoggerFactory.getLogger(RestApplicationAdapter.class);

	private static final String applicationName = "lemo";
	private static final String assetsName = "assets";
	private static final String applicationBasePath = "/lemo";
	private static final String assetPath = applicationBasePath + "/assets";

	private ServletContainer servletContainer;
	private ResourceHttpContext resourceContext;

	private ServiceRegistration servletContainerRegistration;
	private ServiceRegistration resourceContextRegistration;
	private ServiceRegistration resourceMappingRegistration;

	private Map<ServiceReference, Object> resourceInstances = new HashMap<>();
	private Map<ServiceReference, ComponentFactory> resourceFactories = new HashMap<>();

	@Context
	private BundleContext context;

	public synchronized void onArrival(ServiceReference ref) throws ClassNotFoundException {

		Object service = context.getService(ref);

		if (service.getClass().isAnnotationPresent(Path.class)) {
			logger.info("PATH ANNOTATION SINGLETON " + service.getClass());
			resourceInstances.put(ref, service);
			reloadApplication();
		} else if (service instanceof ComponentFactory) {
			logger.info("PATH ANNOTATION FACTORY " + service.getClass());
			ComponentFactory factory = (ComponentFactory) service;
			Class<?> resourceClass = factory.loadClass(factory.getClassName());
			if (resourceClass.isAnnotationPresent(Path.class)) {
				resourceFactories.put(ref, factory);
				reloadApplication();
			}
		}
	}

	public synchronized void onDeparture(ServiceReference ref) throws ClassNotFoundException {
		if (resourceInstances.remove(ref) != null) {
			reloadApplication();
		}
	}

	private Object createInstance(ComponentFactory factory) throws ClassNotFoundException, UnacceptableConfiguration, MissingHandlerException,
			ConfigurationException {

		ComponentInstance instance = factory.createComponentInstance(null);
		if (instance.getState() == ComponentInstance.VALID) {
			Object resourceInstance = ((InstanceManager) instance).getPojoObject();
			logger.info("PATH  FACTORY " + resourceInstance);
			return resourceInstance;
		} else {
			logger.error("Cannot get an implementation object from an invalid instance");
		}

		return null;
	}

	@Path("/")
	public static class Foo {
		private RestApplicationAdapter app;

		public Foo(RestApplicationAdapter app) {
			this.app = app;
		}

		@GET
		@RolesAllowed("user")
		public String foo() {
			return "no auth";
		}

		@GET
		@Path("plugins")
		public String pluginList() {
			String r = "Plugins<br><br>";
			for (Entry<ServiceReference, Object> entry : app.resourceInstances.entrySet()) {
				r += "<b>" + entry.getValue().getClass() + "</b><br>" + "<br><br>";
			}
			return r;
		}
	}

	@Validate
	public void activate() {
		initServletContainer(context);
		initResourceMapping(context);
		reloadApplication();
	}

	@Invalidate
	protected void deactivate() {
		tryUnregister(servletContainerRegistration);
		tryUnregister(resourceMappingRegistration);
		tryUnregister(resourceContextRegistration);
	}

	private void initServletContainer(BundleContext applicationContext) {
		Dictionary<String, Object> webappServletProperties = createServletProperties(applicationName, applicationBasePath);
		servletContainer = new ServletContainer();

		servletContainerRegistration = applicationContext.registerService(Servlet.class.getName(), servletContainer, webappServletProperties);
	}

	private void initResourceMapping(BundleContext applicationContext) {
		resourceContext = new ResourceHttpContext();
		resourceContextRegistration = applicationContext.registerService(HttpContext.class.getName(), resourceContext, null);
		resourceMappingRegistration = registerResourceMapping(applicationContext, assetsName, assetPath);
	}

	private void reloadApplication() {
		if (servletContainer == null || resourceContext == null) {
			// if service binding happens before @activate, ignore reloading
			return;
		}

		// reload servlet container
		ResourceConfig resourceConfig = new ResourceConfig();
		resourceConfig.register(RolesAllowedDynamicFeature.class);
		resourceConfig.packages("de.lemo.server.auth");
		resourceConfig.registerInstances(new Foo(this));
		for (Object resourceInstance : resourceInstances.values()) {
			resourceConfig.register(resourceInstance);
		}
		for (ComponentFactory factory : resourceFactories.values()) {
			try {
				Object resourceInstance = createInstance(factory);
				if (resourceInstance != null) {
					resourceConfig.register(resourceInstance);
				}
			} catch (ClassNotFoundException | UnacceptableConfiguration | MissingHandlerException | ConfigurationException e) {
				logger.error("ComponentFactory instance creation failed", e);
			}

		}
		servletContainer.reload(resourceConfig);

		// reload resource context
		Map<String, Bundle> pluginPathMapping = new HashMap<>();
		for (Object resource : resourceInstances.values()) {
			Bundle bundle = FrameworkUtil.getBundle(resource.getClass());
			String path = "/" + trim(resource.getClass().getAnnotation(Path.class).value(), '/');
			pluginPathMapping.put(path, bundle);
		}
		resourceContext.reload(pluginPathMapping);
	}

	private boolean tryUnregister(ServiceRegistration registration) {
		if (registration != null) {
			try {
				registration.unregister();
			} catch (Exception e) {
				logger.warn("Failed to unregister service {}", registration);
				return false;
			}
		}
		return true;
	}

	private ServiceRegistration registerResourceMapping(BundleContext applicationContext, String name, String path) {
		DefaultResourceMapping resourceMapping = new DefaultResourceMapping();
		resourceMapping.setAlias(path);
		resourceMapping.setPath(name);
		ServiceRegistration registerService = applicationContext.registerService(ResourceMapping.class.getName(), resourceMapping, null);
		return registerService;
	}

	private Dictionary<String, Object> createServletProperties(String name, String path) {
		Dictionary<String, Object> props = new Hashtable<>();

		// osgi http service
		props.put(HttpConstants.SERVLET_NAME, name); // TODO should work without "init" but doesn't (?)
		props.put(HttpConstants.INIT_PREFIX + HttpConstants.SERVLET_NAME, name);
		props.put(HttpConstants.ALIAS, path);

		// jersey
		props.put(ServerProperties.APPLICATION_NAME, name);
		// props.put(ServerProperties.METAINF_SERVICES_LOOKUP_DISABLE, "true");
		// props.put(ServerProperties.FEATURE_AUTO_DISCOVERY_DISABLE, "true");
		// props.put(ServerProperties.WADL_FEATURE_DISABLE, "true");

		return props;
	}

	private String trim(String string, char remove) {
		StringBuilder sb = new StringBuilder(string);
		while (sb.charAt(0) == remove) {
			sb.deleteCharAt(0);
		}
		while (sb.charAt(sb.length() - 1) == remove) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
}
