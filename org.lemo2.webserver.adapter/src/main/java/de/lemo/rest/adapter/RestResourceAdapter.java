package de.lemo.rest.adapter;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.Servlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Context;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Unbind;
import org.apache.felix.ipojo.annotations.Validate;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.servlet.ServletContainer;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lemo.rest.api.WebResource;

@SuppressWarnings("rawtypes")
@Component
@Instantiate
public class RestResourceAdapter {

	private static final Logger logger = LoggerFactory.getLogger(RestResourceAdapter.class);

	private static final String applicationName = "lemo";
	private static final String applicationBasePath = "/lemo";

	private ServletContainer servletContainer;
	private ServiceRegistration servletContainerRegistration;

	@Context
	private BundleContext context;

	@Requires
	HttpService httpService;

	List<WebResource> resourceInstances = new ArrayList<>();

	@Bind(aggregate = true, optional = true)
	public synchronized void bindResources(WebResource webResource) {

		boolean isResource = webResource.getClass().isAnnotationPresent(Path.class);
		// boolean isSingleton = webResource.getClass().isAnnotationPresent(Singleton.class);

		if (isResource) {
			resourceInstances.add(webResource);
			reloadApplication();

			String path = trim(webResource.getClass().getAnnotation(Path.class).value(), '/');

			Bundle resourceBundle = FrameworkUtil.getBundle(webResource.getClass());
			BundleContext resourceBundleContext = resourceBundle.getBundleContext();
			ResourceHttpContext resourceHttpContext = new ResourceHttpContext(resourceBundleContext);
			try {
				httpService.registerResources("/lemo/" + path + "/assets", "Bundle" + resourceBundle.getBundleId() + "-Resources", resourceHttpContext);
			} catch (NamespaceException e) {
				logger.error("Alias already registered", e);
			}
		}
	}

	@Unbind
	public synchronized void unbindResources(WebResource webResource) throws ClassNotFoundException {
		if (resourceInstances.remove(webResource)) {
			reloadApplication();
		}

		String path = trim(webResource.getClass().getAnnotation(Path.class).value(), '/');
		httpService.unregister("/lemo/" + path + "/assets");
	}

	@Path("/system")
	public static class PluginListening {
		private RestResourceAdapter app;

		public PluginListening(RestResourceAdapter app) {
			this.app = app;
		}

		// @GET
		// @RolesAllowed("user")
		// public String foo() {
		// return "no auth";
		// }

		@GET
		public String pluginList() {
			String r = "Plugins<br><br>";
			for (WebResource entry : app.resourceInstances) {
				r += "<b>" + entry.getClass() + "</b><br>" + "<br><br>";
			}
			return r;
		}
	}

	@Validate
	public void activate() {
		initServletContainer(context);
		reloadApplication();
	}

	@Invalidate
	protected void deactivate() {
		tryUnregister(servletContainerRegistration);
	}

	private void initServletContainer(BundleContext applicationContext) {
		Dictionary<String, Object> webappServletProperties = createServletProperties(applicationName, applicationBasePath);

		servletContainer = new ServletContainer();
		servletContainerRegistration = applicationContext.registerService(Servlet.class.getName(), servletContainer, webappServletProperties);

	}

	private synchronized void reloadApplication() {
		if (servletContainer == null) {
			// if service binding happens before @activate, ignore reloading
			return;
		}

		// reload servlet container
		ResourceConfig resourceConfig = new ResourceConfig();

		// security
		resourceConfig.register(RolesAllowedDynamicFeature.class);
		resourceConfig.packages("de.lemo.rest.adapter.auth");
		resourceConfig.registerInstances(new PluginListening(this));

		for (WebResource resourceInstance : resourceInstances) {
			resourceConfig.registerInstances(resourceInstance);
		}
		servletContainer.reload(resourceConfig);
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

	private Dictionary<String, Object> createServletProperties(String name, String path) {
		Dictionary<String, Object> props = new Hashtable<>();

		// osgi http service
		props.put(HttpConstants.SERVLET_NAME, name);
		props.put(HttpConstants.INIT_PREFIX + HttpConstants.SERVLET_NAME, name);
		props.put(HttpConstants.ALIAS, path);

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
