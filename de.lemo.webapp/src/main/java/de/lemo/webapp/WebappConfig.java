package de.lemo.webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Dictionary;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.Servlet;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

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
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.DelegatingFilterProxy;

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
		
		Dictionary filterProps = new Hashtable();
		String[] urls = {"/*"};
		filterProps.put("filter-name", "Spring Security Filter");
		filterProps.put("urlPatterns", urls);
		filterProps.put("servlet-name", name);
		bundleContext.registerService(Filter.class.getName(), new DelegatingFilterProxy(), filterProps );
		
		logger.info("filter chain initialized");
		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContainer.getServletContext());
		ContextLoaderListener contextLoaderListener = new ContextLoaderListener(applicationContext);

		Dictionary listenerProps = new Hashtable();
		listenerProps.put("listener-name", "Spring Security Listener");
		listenerProps.put("servlet-name", name);
		bundleContext.addServiceListener((ServiceListener) contextLoaderListener);		
		logger.info("contextloaderlistener initialized");
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
		Object toolId = properties.get("lemo.tool.id");
		if (toolId == null) {
			toolId = analyticsTool.getClass().getName();
		}
		Object toolName = properties.get("lemo.tool.name");
		if (toolName == null) {
			toolName = toolId;
		}

		properties.put("name", "" + toolName);
		properties.put("url", ANALYTICS_PAGE + "/" + toolId);
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

	// enable this OSGi-managed component instance for injection
	private class ApplicationBinder extends AbstractBinder {
		@Override
		protected void configure() {
			bind(WebappConfig.this).to(WebappConfig.class);
		}
	}
	
	private class SpringFilterChain implements Filter {
		private FilterConfig filterConfig = null;
		@Override
		public void init(FilterConfig filterConfig) throws ServletException {
			this.filterConfig = filterConfig;			
		}

		@Override
		public void doFilter(ServletRequest request, ServletResponse response,
				FilterChain chain) throws IOException, ServletException {
    	      if (filterConfig == null)
    	         return;
    	      StringWriter sw = new StringWriter();
    	      PrintWriter writer = new PrintWriter(sw);
    	      logger.error("Filter reched!");
    	      filterConfig.getServletContext().
    	         log(sw.getBuffer().toString());
    	      chain.doFilter(request, response);			
		}

		@Override
		public void destroy() {
			// TODO Auto-generated method stub
			
		}

	}

}
