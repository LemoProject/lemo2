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
import org.apache.felix.scr.annotations.References;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.ops4j.pax.web.extender.whiteboard.ResourceMapping;
import org.ops4j.pax.web.extender.whiteboard.runtime.DefaultResourceMapping;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@References({ @Reference(name = "applications", referenceInterface = Application.class, cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC, bind = "registerApplication", unbind = "unregisterApplication", updated = "updateApplication"), })
@Component(immediate = true, metatype = false)
public class RestApplicationAdapter {

	private static final Logger logger = LoggerFactory.getLogger(RestApplicationAdapter.class);

	private Map<Application, ServiceRegistration> applicationRegistrations = new HashMap<>();
	private Map<Application, ServiceRegistration> resourceMappingRegistrations = new HashMap<>();

	protected void registerApplication(Application application) {
		Dictionary<String, Object> servletProperties = createProperties(application);

		ResourceConfig resourceConfig;
		if (application instanceof ResourceConfig) {
			// ResourceConfig can be unmodifiable, need to wrap it
			resourceConfig = new ResourceConfig((ResourceConfig) application);
		} else {
			resourceConfig = ResourceConfig.forApplication(application);
		}

		String path = (String) servletProperties.get(AdapterConstants.ALIAS);
		resourceConfig.setApplicationName((String) servletProperties.get(AdapterConstants.SERVLET_NAME));
		logger.info("REGISTERED JAX-RS application {} at path {}", resourceConfig.getApplicationName(), path);

		ServletContainer servlet = new ServletContainer(resourceConfig);
		BundleContext webappBundleContext = FrameworkUtil.getBundle(application.getClass()).getBundleContext();

		DefaultResourceMapping resourceMapping = new DefaultResourceMapping();
		resourceMapping.setAlias(path + "/resources");
		resourceMapping.setPath("/META-INF/webapp");
		ServiceRegistration resourceMappingRegistration = webappBundleContext.registerService(ResourceMapping.class.getName(), resourceMapping, null);

		ServiceRegistration webappRegistration = webappBundleContext.registerService(Servlet.class.getName(), servlet, servletProperties);

		// ServiceRegistration registration2 = webappBundleContext.registerService(Filter.class.getName(), servlet,
		// createFilterProperties(application));
		applicationRegistrations.put(application, webappRegistration);
		resourceMappingRegistrations.put(application, resourceMappingRegistration);
	}

	protected void updateApplication(Application applicaton) {
		logger.warn("update ignored: " + applicaton);
	}

	protected void unregisterApplication(Application applicaton) {
		logger.info("removeApplication " + applicaton);

		ServiceRegistration registration = applicationRegistrations.remove(applicaton);
		ServiceRegistration registration2 = resourceMappingRegistrations.remove(applicaton);

		if (registration != null) {
			try {
				registration.unregister();
				registration2.unregister();
			} catch (IllegalStateException e) {
				logger.warn(String.format("Unregistering REST application %s  failed", applicaton), e);
			}
		} else {
			logger.warn("Unregistering REST application %s failed, no registration found", applicaton);
		}
	}

	private Dictionary<String, Object> createFilterProperties(Application application) {

		String name = application.getClass().getName();
		Dictionary<String, Object> props = new Hashtable<>();

		String path = getApplicationPath(application);

		String[] urls = { "/*" };
		String[] servlets = { name };
		props.put("filter-name", name + "Filter");
		props.put("urlPatterns", urls);
		// props.put("servletNames", servlets);
		// props.put("pattern", "/*");

		props.put(AdapterConstants.INIT_PREFIX + "jersey.config.servlet.filter.staticContentRegex", ".*css");
		props.put("jersey.config.servlet.filter.staticContentRegex", ".*css");

		return props;
	}

	private Dictionary<String, Object> createProperties(Application application) {

		String name = application.getClass().getName();

		Dictionary<String, Object> props = new Hashtable<>();

		String path = getApplicationPath(application);
		props.put(AdapterConstants.ALIAS, path);

		props.put(AdapterConstants.SERVLET_NAME, name); // TODO should work without "init" but doesn't (?)
		props.put(AdapterConstants.INIT_PREFIX + AdapterConstants.SERVLET_NAME, name);

		props.put(AdapterConstants.INIT_PREFIX + "jersey.config.servlet.filter.staticContentRegex", ".*css");
		props.put("jersey.config.servlet.filter.staticContentRegex", ".*css");

		props.put(ServerProperties.METAINF_SERVICES_LOOKUP_DISABLE, true);
		props.put(ServerProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true);
		props.put(ServerProperties.WADL_FEATURE_DISABLE, true);
		props.put(ServerProperties.APPLICATION_NAME, name);

		return props;
	}

	private String getApplicationPath(Application application) {
		String path = "/";
		ApplicationPath applicationPathAnnotation = application.getClass().getAnnotation(ApplicationPath.class);
		if (applicationPathAnnotation != null) {
			path = applicationPathAnnotation.value();
		}
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
