package de.lemo.server;

import java.util.Collections;
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
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@References({ @Reference(name = "applications", referenceInterface = Application.class, cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC, bind = "registerApplication", unbind = "unregisterApplication", updated = "updateApplication"), })
@Component(immediate = true, metatype = false)
public class RestApplicationAdapter {

	private static final Logger logger = LoggerFactory.getLogger(RestApplicationAdapter.class);

	private Map<Application, ServiceRegistration> applications = new HashMap<>();
	ServiceRegistration registration;

	protected void registerApplication(Application application) {
		logger.info("addApplication " + application);

		Dictionary<String, Object> servletProperties = createProperties(application);

		ResourceConfig resourceConfig;
		if (application instanceof ResourceConfig) {
			// ResourceConfig can be unmodifiable, need to wrap it

			resourceConfig = new ResourceConfig((ResourceConfig) application);
			resourceConfig.register(OsgiMustacheTemplateProcessor.class);

			long bundleId = FrameworkUtil.getBundle(application.getClass()).getBundleId();
			resourceConfig.addProperties(Collections.singletonMap(AdapterConstants.WEBAPP_BUNDLE_ID, (Object) bundleId));

		} else {
			resourceConfig = ResourceConfig.forApplication(application);
		}

		resourceConfig.setApplicationName((String) servletProperties.get(AdapterConstants.SERVLET_NAME));
		logger.info("REGISTERED JAX-RS application {} at path {}" , resourceConfig.getApplicationName(), servletProperties.get(AdapterConstants.ALIAS));
 
		ServletContainer servlet = new ServletContainer(resourceConfig);

		BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		ServiceRegistration registration = bundleContext.registerService(Servlet.class.getName(), servlet, servletProperties);
		applications.put(application, registration);

	}

	protected void updateApplication(Application applicaton) {
		logger.warn("update ignored: " + applicaton);
	}

	protected void unregisterApplication(Application applicaton) {
		logger.info("removeApplication " + applicaton);

		ServiceRegistration registration = applications.remove(applicaton);

		if (registration != null) {
			try {
				registration.unregister();
			} catch (IllegalStateException e) {
				logger.warn(String.format("Unregistering REST application %s  failed", applicaton), e);
			}
		} else {
			logger.warn("Unregistering REST application %s failed, no registration found", applicaton);
		}
	}

	private Dictionary<String, Object> createProperties(Application application) {

		String name = application.getClass().getName();

		Dictionary<String, Object> props = new Hashtable<>();

		String path = getApplicationPath(application);
		props.put(AdapterConstants.ALIAS, path);

		props.put(AdapterConstants.SERVLET_NAME, name); // TODO should work without "init" but doesn't (?)
		props.put(AdapterConstants.INIT_PREFIX + AdapterConstants.SERVLET_NAME, name);

		props.put(ServerProperties.METAINF_SERVICES_LOOKUP_DISABLE, true);
		props.put(ServerProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true);
		props.put(ServerProperties.WADL_FEATURE_DISABLE, true);
		props.put(ServerProperties.APPLICATION_NAME, name);
  
		Bundle webAppBundle = FrameworkUtil.getBundle(application.getClass());
		props.put(AdapterConstants.WEBAPP_BUNDLE_ID, webAppBundle.getBundleId());
		props.put(AdapterConstants.WEBAPP_BUNDLE_CONTEXT, webAppBundle.getBundleContext());

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
