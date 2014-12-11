package de.lemo.server;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Set;

import javax.servlet.Servlet;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.servlet.ServletContainer;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ServerActivator implements BundleActivator {

	private static final Logger logger = LoggerFactory.getLogger(ServerActivator.class);

	private BundleContext context; 

	private ServiceTracker tracker;

	@Override
	public void start(BundleContext context) throws Exception {
		this.context = context;

		this.tracker = new ServiceTracker(context, Application.class.getName(), new Customizer());
		this.tracker.open();

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		this.tracker.close();
		this.tracker = null;
		this.context = null;
	}

	private class Customizer implements ServiceTrackerCustomizer {

		private Dictionary createProps(Application app, ServiceReference reference) {

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

		public Object addingService(ServiceReference reference) {

			Application app = (Application) context.getService(reference);
			ResourceConfig resourceConfig = ResourceConfig.forApplication(app);
			resourceConfig.setApplicationName(app.getClass().getName() + "@" + reference.getBundle().getSymbolicName());

			// add everything from the same package
			resourceConfig = resourceConfig.packages(true, app.getClass().getPackage().getName());

			Dictionary appProperties = createProps(app, reference);

			String basePath = "" + appProperties.get("alias");
			logger.info("Adding JAX-RS application " + resourceConfig.getApplicationName());
			logger.info(resourceConfig.getClasses().size() + " classes, alias: " + basePath);
			for (Class<?> resourceClass : resourceConfig.getClasses()) {
				Resource resource = Resource.from(resourceClass);
				logger.info(basePath + resource.getPath() + "\t" + resource.getName());
			}
			ServletContainer servlet = new ServletContainer(resourceConfig);

			Bundle sourceBundle = reference.getBundle();
			BundleContext sourceContext = sourceBundle.getBundleContext();

			ServiceRegistration reg = sourceContext.registerService(Servlet.class.getName(), servlet, appProperties);
			return reg;
		}

		/** {@inheritDoc} */
		public void modifiedService(ServiceReference reference, Object service) {
			Application app = (Application) context.getService(reference);
			ServiceRegistration reg = (ServiceRegistration) service;

			logger.info("Modifying JAX-RS application: " + reg);

			reg.setProperties(createProps(app, reference));
		}

		/** {@inheritDoc} */
		public void removedService(ServiceReference reference, Object service) {
			ServiceRegistration reg = (ServiceRegistration) service;

			logger.info("Removing JAX-RS application: " + reg);

			reg.unregister();
			context.ungetService(reference);
		}

	}

	// private void attachHttpServiceTracker(BundleContext context) {
	// httpServiceTracker = new ServiceTracker(context, HttpService.class.getName(), null) {
	//
	// @Override
	// public Object addingService(ServiceReference serviceRef) {
	// ExtHttpService httpService = (ExtHttpService) context.getService(serviceRef);
	//
	// httpContext = httpService.createDefaultHttpContext(); // (1)
	//
	// ServerActivator.this.httpService = httpService;
	//
	// // publishStaticResources();
	// //
	// // for (String alias : packagesPerAlias.keySet()) { // (3)
	// // publishWebServices(alias, packagesPerAlias.get(alias));
	// // }
	//
	// return httpService;
	// }
	//
	// @Override
	// public void removedService(ServiceReference reference, Object service) {
	// ServerActivator.this.httpService = null;
	// super.removedService(reference, service);
	// }
	//
	// };
	//
	// this.httpServiceTracker.open();
	// }

	// private void publishWebServices(String alias, Set<String> packages) {
	// synchronized (packages) {
	// ServletContainer sc = new ServletContainer(); //(1)
	// Dictionary<String, String> jerseyServletParams = new Hashtable<String, String>();
	// log.info(MessageFormat.format("Publish services founded in {0} under {1}",
	// CollectionUtil.createTokenSeparatedValues(packages, ';'), alias));
	// jerseyServletParams.put("com.sun.jersey.config.property.packages",
	// CollectionUtil.createTokenSeparatedValues(packages, ';')); //(2)
	// try {
	// httpService.registerServlet(alias, sc, jerseyServletParams, httpContext); //(3)
	//
	// sc.getServletContext().setAttribute("osgi-bundlecontext", context); //(4)
	//
	// String contextPath = "http://" + InetAddress.getLocalHost().getHostAddress() + ":8080" + ROOTALIAS;
	// ch.psi.da.api.dto.ServiceReference.setContextPath(contextPath);
	//
	// } catch (ServletException e) {
	// log.error("", e);
	// } catch (NamespaceException e) {
	// log.info(MessageFormat.format("Namespace {0} already in use. Unregister namespace and register again.",
	// alias));
	// httpService.unregister(alias);
	// try {
	// httpService.registerServlet(alias, sc, jerseyServletParams, httpContext);
	// } catch (ServletException e1) {
	// log.error("", e1);
	// } catch (NamespaceException e1) {
	// log.error("", e);
	// }
	// } catch (UnknownHostException e) {
	// log.error("", e);
	// }
	// }

	// @Override
	// public void start(BundleContext context) throws Exception {

	// logger.info("server start");

	// BundleContext bundleContext = FrameworkUtil.getBundle(JettyService.class).getBundleContext();
	//
	// int port = 8080;
	// Server server = new Server(port);
	// HandlerList handlerList = new HandlerList();
	// WebAppContext webAppContext = new WebAppContext();
	//
	// // add the directories containing classes that will be scanned for annotations
	// for (String path : getClassPathResources()) {
	// webAppContext.getMetaData().addContainerResource(Resource.newResource(path));
	// }
	//
	// // auto discover Servlet API 3 and JAX-RS annotations
	// webAppContext.setConfigurations(new Configuration[] { new AnnotationConfiguration() });
	//
	// // serve resources
	// // if (resourcePaths != null) {
	// // String[] resources = resourcePaths.toArray(new String[resourcePaths.size()]);
	// // ResourceHandler resourceHandler = new ResourceHandler();
	// // resourceHandler.setBaseResource(new ResourceCollection(resources));
	// // handlerList.addHandler(resourceHandler);
	// // }
	//
	// handlerList.addHandler(webAppContext);
	// server.setHandler(handlerList);
	//
	//
	//
	// // WebAppContext webAppContext = new WebAppContext();
	// // webAppContext.setResourceBase("./war");
	// // webAppContext.setDescriptor("./war/WEB-INF/web.xml");
	// // webAppContext.setContextPath("/");
	// webAppContext.setParentLoaderPriority(true);
	//
	// webAppContext.setAttribute("osgi-bundlecontext", bundleContext);
	//
	// server.setHandler(webAppContext);
	//
	// server.start();
	// }

}
