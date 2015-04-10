package de.lemo.webapp;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.Servlet;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Context;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Validate;
import org.glassfish.jersey.servlet.ServletContainer;
import org.ops4j.pax.web.extender.whiteboard.ResourceMapping;
import org.ops4j.pax.web.extender.whiteboard.runtime.DefaultResourceMapping;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registers the Jersey application with the OSGi HttpService.
 * 
 * @author Leonard Kappe
 *
 */
@Component
@Instantiate
public class WebappComponent {

	private static final Logger logger = LoggerFactory.getLogger(WebappComponent.class);

	private String path = "/";
	private String name = "Lemo Web App";

	@Context
	private BundleContext bundleContext;

	private ServiceRegistration<?> webAppServiceRegistration;
	private ServiceRegistration<?> resourceMappingServiceRegistration;

	@Validate
	public void activate() {
		ServletContainer servletContainer = new ServletContainer(new WebappResourceConfig());

		Dictionary<String, String> props = new Hashtable<>();
		props.put("alias", path);
		props.put("servlet-name", name);
		props.put("init.servlet-name", name);

		webAppServiceRegistration = bundleContext.registerService(Servlet.class.getName(), servletContainer, props);

//		DefaultResourceMapping resourceMapping = new DefaultResourceMapping();
//		resourceMapping.setAlias("/assets");
//		resourceMapping.setPath("/");
//		resourceMappingServiceRegistration = bundleContext.registerService(ResourceMapping.class.getName(), resourceMapping, null);

	}

	@Invalidate
	protected void deactivate() {
		try {
			webAppServiceRegistration.unregister();
		} catch (Exception e) {
			logger.error("unregister failed", e);
		}

		try {
			resourceMappingServiceRegistration.unregister();
		} catch (Exception e) {
			logger.error("unregister failed", e);
		}
	}

}
