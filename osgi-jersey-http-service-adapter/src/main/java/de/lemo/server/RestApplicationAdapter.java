package de.lemo.server;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.Servlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.felix.ipojo.ComponentInstance;
import org.apache.felix.ipojo.ConfigurationException;
import org.apache.felix.ipojo.Factory;
import org.apache.felix.ipojo.InstanceManager;
import org.apache.felix.ipojo.MissingHandlerException;
import org.apache.felix.ipojo.UnacceptableConfiguration;
import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Context;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Unbind;
import org.apache.felix.ipojo.annotations.Validate;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.servlet.ServletContainer;
import org.ops4j.pax.web.extender.whiteboard.ResourceMapping;
import org.ops4j.pax.web.extender.whiteboard.runtime.DefaultResourceMapping;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Instantiate
@Component
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

	private Set<Factory> factories = new HashSet<>();
	private Map<Factory, Set<ComponentInstance>> factoryInstances = new HashMap<>();

	@Context
	private BundleContext context;

	@Bind(filter = "(service.pid=*)", aggregate = true, optional = true)
	public void bindIPojoFactory(Factory factory) {

		String className = factory.getDescription().getAttribute("implementation-class");
		try {
			Class<?> resourceClass = factory.getBundleContext().getBundle().loadClass(className);
			if (resourceClass.isAnnotationPresent(Path.class)) {
				factories.add(factory);
				try {
					createInstance(factory, null);
				} catch (UnacceptableConfiguration | MissingHandlerException | ConfigurationException e) {
					logger.error("factory failed", e);
				}
				reloadApplication();
			}
		} catch (ClassNotFoundException e) {
			logger.error("Failed to bind IPojo Factory", e);
		}
	}

	@Unbind
	public void unbindIPojoFactory(Factory factory) {
		boolean removed = factories.remove(factory);
		if (removed) {
			for (ComponentInstance componentInstance : factoryInstances.remove(factory)) {
				componentInstance.dispose();
			}
			reloadApplication();
			return;
		}
	}

	private Object createInstance(Factory factory, Dictionary configuration) throws ClassNotFoundException, UnacceptableConfiguration, MissingHandlerException,
			ConfigurationException {

		ComponentInstance componentInstance = factory.createComponentInstance(configuration);
		if (componentInstance.getState() == ComponentInstance.VALID) {
			Object resourceInstance = ((InstanceManager) componentInstance).getPojoObject();
			logger.info("PATH  FACTORY " + resourceInstance);

			Set<ComponentInstance> factoryComponentInstances = factoryInstances.get(factory);
			if (factoryComponentInstances == null) {
				factoryComponentInstances = new HashSet<>();
				factoryInstances.put(factory, factoryComponentInstances);
			}
			factoryComponentInstances.add(componentInstance);

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

		// @GET
		// @RolesAllowed("user")
		// public String foo() {
		// return "no auth";
		// }

		@GET
		@Path("/")
		public String pluginList() {
			String r = "<h2>Plugins</h2>";
			for (Entry<Factory, Set<ComponentInstance>> entry : app.factoryInstances.entrySet()) {
				r += "<b>" + entry.getKey().getName() + "</b><br/>";
				for (ComponentInstance instance : entry.getValue()) {
					r += "&nbsp;&nbsp;&nbsp;&nbsp;" + instance.getInstanceName() + "</b><br/>";
				}
				r += "<br/>";
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

		for (Factory factory : factories) {
			unbindIPojoFactory(factory);
		}
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
		for (Object resourceInstance : factoryInstances.values()) {
			resourceConfig.register(resourceInstance);
		}
		// for (Factory factory : factories) {
		//
		// try {
		// Dictionary configuration = new Hashtable<>();
		// // for (PropertyDescription element : factory.getComponentDescription().getProperties()) {
		// // configuration.put(element.getName(), element.getCurrentValue());
		// // }
		// Object resourceInstance = createInstance(factory, configuration);
		// if (resourceInstance != null) {
		// resourceConfig.register(resourceInstance);
		// }
		// } catch (ClassNotFoundException | UnacceptableConfiguration | MissingHandlerException |
		// ConfigurationException e) {
		// logger.error("ComponentFactory instance creation failed", e);
		// }
		//
		// }
		servletContainer.reload(resourceConfig);

		// reload resource context
		// Map<String, Bundle> pluginPathMapping = new HashMap<>();
		// for (Object resource : resourceInstances.values()) {
		// Bundle bundle = FrameworkUtil.getBundle(resource.getClass());
		// String path = "/" + trim(resource.getClass().getAnnotation(Path.class).value(), '/');
		// pluginPathMapping.put(path, bundle);
		// }
		// resourceContext.reload(pluginPathMapping);
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
