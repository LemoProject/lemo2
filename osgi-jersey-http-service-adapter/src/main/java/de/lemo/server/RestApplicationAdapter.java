package de.lemo.server;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.Servlet;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.ops4j.pax.web.extender.whiteboard.ResourceMapping;
import org.ops4j.pax.web.extender.whiteboard.runtime.DefaultResourceMapping;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, metatype = false)
public class RestApplicationAdapter {

	private static final Logger logger = LoggerFactory.getLogger(RestApplicationAdapter.class);

	@Reference(name = "applications", referenceInterface = Application.class, cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC, bind = "bindApplication", unbind = "unbindApplication", updated = "updateApplication")
	private Map<Application, ServiceRegistration> applications = new HashMap<>();
	private Map<Application, ServiceRegistration> resourceHttpContexts = new HashMap<>();
	private Map<Application, ServiceRegistration> resourceMappings = new HashMap<>();

	protected void bindApplication(final Application application) {
 
		Bundle applicationBundle = FrameworkUtil.getBundle(application.getClass());
		BundleContext applicationContext = applicationBundle.getBundleContext();

		logger.info("Registering JAX-RS application {} from bundle {}", application.getClass().getName(), applicationBundle);

		String applicationName = application.getClass().getName() + "-" + applicationBundle.getBundleId();
		String applicationPath = getApplicationPath(application);
		String resourceName = applicationName + "-Resources";
		String resourcePath = applicationPath + "/assets";
		String httpContextId = applicationName + "-HttpContext";

		ServiceRegistration resourceHttpContextRegistration = registerResourceHttpContext(applicationContext, httpContextId);
		ServiceRegistration resourceMappingRegistration = registerResourceMapping(applicationContext, resourceName, resourcePath, httpContextId);
		ServiceRegistration applicationRegistration = registerApplication(application, applicationContext, applicationName, applicationPath, httpContextId);

		resourceHttpContexts.put(application, resourceHttpContextRegistration);
		resourceMappings.put(application, resourceMappingRegistration);
		applications.put(application, applicationRegistration);

	}

	protected void updateApplication(Application application) {
		logger.info("Updating JAX-RS application {}", application);
	}

	protected void unbindApplication(Application application) {
		logger.info("Unregistering JAX-RS application {}", application);

		ServiceRegistration resourceHttpContextRegistration = resourceHttpContexts.remove(application);
		ServiceRegistration resourceMappingRegistration = resourceMappings.remove(application);
		ServiceRegistration applicationRegistration = applications.remove(application);

		if (resourceMappingRegistration != null) {
			resourceMappingRegistration.unregister();
		}
		if (resourceHttpContextRegistration != null) {
			resourceHttpContextRegistration.unregister();
		}
		if (applicationRegistration != null) {
			applicationRegistration.unregister();
		}

	}

	private ServiceRegistration registerApplication(Application application, BundleContext applicationContext, String applicationName, String aplicationPath,
			String httpContextId) {

		ResourceConfig resourceConfig;
		if (application instanceof ResourceConfig) {
			// ResourceConfig can be unmodifiable, need to wrap it
			resourceConfig = new ResourceConfig((ResourceConfig) application);
		} else {
			resourceConfig = ResourceConfig.forApplication(application);
		}
		resourceConfig.setApplicationName(applicationName);

		ServletContainer webappServlet = new ServletContainer(resourceConfig);

		Dictionary<String, Object> webappServletProperties = createServletProperties(applicationName, aplicationPath, httpContextId);
		ServiceRegistration registerService = applicationContext.registerService(Servlet.class.getName(), webappServlet, webappServletProperties);
		return registerService;
	}

	private ServiceRegistration registerResourceHttpContext(BundleContext applicationContext, String httpContextId) {
		ResourceHttpContext resourceHttpContext = new ResourceHttpContext(applicationContext, "/");
		Dictionary<String, Object> props = new Hashtable<>();
		props.put(HttpConstants.CONTEXT_ID, httpContextId);
		return applicationContext.registerService(HttpContext.class.getName(), resourceHttpContext, props);
	}

	private ServiceRegistration registerResourceMapping(BundleContext applicationContext, String name, String path, String httpContextId) {
		DefaultResourceMapping resourceMapping = new DefaultResourceMapping();
		resourceMapping.setAlias(path);
		resourceMapping.setPath(name);
		// resourceMapping.setHttpContextId(httpContextId);
		ServiceRegistration registerService = applicationContext.registerService(ResourceMapping.class.getName(), resourceMapping, null);
		return registerService;
	}

	private Dictionary<String, Object> createServletProperties(String name, String path, String httpContextId) {
		Dictionary<String, Object> props = new Hashtable<>();

		// osgi http service
		props.put(HttpConstants.SERVLET_NAME, name); // TODO should work without "init" but doesn't (?)
		props.put(HttpConstants.INIT_PREFIX + HttpConstants.SERVLET_NAME, name);
		props.put(HttpConstants.ALIAS, path);
		props.put(HttpConstants.CONTEXT_ID, httpContextId);
		// jersey
		props.put(ServerProperties.APPLICATION_NAME, name);
		// props.put(ServerProperties.METAINF_SERVICES_LOOKUP_DISABLE, "true");
		// props.put(ServerProperties.FEATURE_AUTO_DISCOVERY_DISABLE, "true");
		// props.put(ServerProperties.WADL_FEATURE_DISABLE, "true");

		return props;
	}

	private String getApplicationPath(Application application) {
		ApplicationPath applicationPathAnnotation = application.getClass().getAnnotation(ApplicationPath.class);
		if (applicationPathAnnotation == null) {
			throw new RuntimeException("Missing @ApplicationPath annotation");
		}
		String path = applicationPathAnnotation.value();
		// trim trailing slash
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		// add leading slash
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		return path;
	}

}
