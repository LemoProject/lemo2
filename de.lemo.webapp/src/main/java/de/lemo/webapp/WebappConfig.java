package de.lemo.webapp;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Servlet;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Context;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Modified;
import org.apache.felix.ipojo.annotations.Unbind;
import org.apache.felix.ipojo.annotations.Validate;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.glassfish.jersey.servlet.ServletContainer;
import org.ops4j.pax.web.extender.whiteboard.ResourceMapping;
import org.ops4j.pax.web.extender.whiteboard.runtime.DefaultResourceMapping;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lemo.tools.api.AnalyticsTool;
import freemarker.cache.ClassTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;

/**
 * Registers the Jersey application with the OSGi HttpService.
 * 
 * @author Leonard Kappe
 *
 */
@Component
@Instantiate
public class WebappConfig {

	public static final String ASSETS = "/assets";
	public static final String APP_PATH = "/";
	public static final String HOME_PAGE = "/";
	public static final String ANALYTICS_PAGE = "/analytics";

	private static final Logger logger = LoggerFactory.getLogger(WebappConfig.class);

	@Context
	private BundleContext bundleContext;

	private String path = "/*";
	private String name = "Lemo Web App";

	private Map<AnalyticsTool, Map<String, Object>> analyticsTools = new HashMap<>();

	private ServletContainer servletContainer;
	private ServiceRegistration<?> webAppServiceRegistration;
	private ServiceRegistration<?> resourceMappingServiceRegistration;

	@Validate
	public void initServletContainer() {

		// webapp
		servletContainer = new ServletContainer(createResourceConfig());
		Dictionary<String, String> props = new Hashtable<>();
		props.put("alias", path);
		props.put("servlet-name", name);
		props.put("init.servlet-name", name);
		webAppServiceRegistration = bundleContext.registerService(Servlet.class.getName(), servletContainer, props);

		logger.info("servletcontainer initialized");

		// static resources
		DefaultResourceMapping resourceMapping = new DefaultResourceMapping();
		resourceMapping.setPath("/assets"); // defines the internal path in the bundle
		resourceMapping.setAlias("/assets"); // defines the url used to load assets
		resourceMappingServiceRegistration = bundleContext.registerService(ResourceMapping.class.getName(), resourceMapping, null);

		logger.info("resourcemapping initialized");

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

	@Bind(aggregate = true, optional = true)
	public void bindAnalyticsTool(AnalyticsTool analyticsTool, Map<String, Object> properties) {
		addTool(analyticsTool, properties);
	}

	@Modified
	public void modifiedAnalyticsTool(AnalyticsTool analyticsTool, Map<String, Object> properties) {
		addTool(analyticsTool, properties);
	}

	@Unbind
	public void unbindAnalyticsTool(AnalyticsTool analyticsTool) {
		analyticsTools.remove(analyticsTool);
	}

	public Map<String, Object> getToolProperties(String toolId) {
		for (Entry<AnalyticsTool, Map<String, Object>> entry : analyticsTools.entrySet()) {
			Map<String, Object> properties = entry.getValue();
			if (properties.get("lemo.tool.id").equals(toolId)) {
				return properties;
			}
		}
		return null;
	}

	private void addTool(AnalyticsTool analyticsTool, Map<String, Object> properties) {
		analyticsTools.put(analyticsTool, properties);
		String toolId = "" + properties.getOrDefault("lemo.tool.id", analyticsTool.getClass().getName());
		properties.put("url", "/analytics/" + toolId);
		properties.put("name", properties.getOrDefault("lemo.tool.name", toolId));
		properties.put("assets", "/lemo/tools/" + toolId + "/assets");

	}

	public ResourceConfig createResourceConfig() {

		ResourceConfig resourceConfig = new ResourceConfig();
		resourceConfig.packages("de.lemo.webapp");

		resourceConfig.register(new ApplicationBinder());

		Configuration freemarkerConfiguration = createFreemarkerConiguration();
		resourceConfig.property(FreemarkerMvcFeature.TEMPLATE_OBJECT_FACTORY, freemarkerConfiguration);
		resourceConfig.property(FreemarkerMvcFeature.TEMPLATES_BASE_PATH, "/templates");
		resourceConfig.register(FreemarkerMvcFeature.class);

		return resourceConfig;
	}

	private Configuration createFreemarkerConiguration() {
		try {
			Configuration freemarkerConfiguration = new Configuration();
			BeansWrapper beansWrapper = BeansWrapper.getDefaultInstance();

			freemarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(getClass(), "/"));

			freemarkerConfiguration.setSharedVariable("basePath", APP_PATH);
			freemarkerConfiguration.setSharedVariable("assetPath", ASSETS);

			freemarkerConfiguration.setSharedVariable("homePagePath", HOME_PAGE);
			freemarkerConfiguration.setSharedVariable("analyticsPagePath", ANALYTICS_PAGE);
			freemarkerConfiguration.setSharedVariable("analysisPluginPath", "");

			freemarkerConfiguration.setSharedVariable("tools", beansWrapper.wrap(analyticsTools.values()));

			return freemarkerConfiguration;

		} catch (TemplateModelException e) {
			throw new RuntimeException("Failed to create freemarker template config", e);
		}
	}

	// Enable OSGi-managed component for injection
	private class ApplicationBinder extends AbstractBinder {
		@Override
		protected void configure() {
			bind(WebappConfig.this).to(WebappConfig.class);
		}
	}

}
