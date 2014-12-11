//package de.lemo.server;
//
//import java.lang.reflect.Field;
//import java.util.Collection;
//import java.util.Dictionary;
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.servlet.Servlet;
//import javax.ws.rs.Path;
//
//import org.apache.felix.dm.Component;
//import org.apache.felix.dm.annotation.api.AdapterService;
//import org.apache.felix.dm.annotation.api.BundleAdapterService;
//import org.apache.felix.dm.annotation.api.Init;
//import org.apache.felix.dm.annotation.api.Inject;
//import org.apache.felix.dm.annotation.api.Start;
//import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.server.model.Resource;
//import org.glassfish.jersey.servlet.ServletContainer;
//import org.osgi.framework.Bundle;
//import org.osgi.framework.BundleContext;
//import org.osgi.framework.wiring.BundleWiring;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
////@AdapterService(adapteeService = IQuestion.class, provides = Servlet.class)
//// @BundleAdapterService(provides = Servlet.class, filter = "(Require-Bundle=de.lemo.server)", stateMask =
//// Bundle.ACTIVE)
//public class RestResourceAdapter extends ServletContainer {
//
//	private static final Logger logger = LoggerFactory.getLogger(RestResourceAdapter.class);
//
//	private Bundle bundle;
//
//	@Inject
//	private Component component;
//
//	// @Inject
//	// BundleContext bundleContext;
//
//	private IQuestion question;
//
//	@Init
//	void foo() {
//
//		Set<Resource> webResources = new HashSet<>();
//
//		Class<? extends IQuestion> resourceClass = question.getClass();
//
//		if (resourceClass.isAnnotationPresent(Path.class)) {
//			 ClassLoader myClassLoader = getClass().getClassLoader();
//			 ClassLoader originalContextClassLoader = Thread.currentThread().getContextClassLoader();
//			 try {
//			logger.info("bundleClass " + resourceClass);
//			 Thread.currentThread().setContextClassLoader(myClassLoader);
//			webResources.add(Resource.from(resourceClass));
//			 } finally {
//			 Thread.currentThread().setContextClassLoader(originalContextClassLoader);
//			 }
//
//		}
//
//		// ClassLoader tccl = Thread.currentThread().getContextClassLoader();
//		// try {
//		// Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
//
//		ResourceConfig resourceConfig = new ResourceConfig();
//		// resourceConfig.setClassLoader(wiring.getClassLoader());
//		resourceConfig.setApplicationName(resourceClass.getName());
//
//		resourceConfig.registerInstances(webResources);
//
//		String basePath = "/service";
//		logger.info("Adding JAX-RS application " + resourceConfig.getApplicationName());
//		logger.info(resourceConfig.getClasses().size() + " classes, alias: " + basePath);
//		// for (Class<?> resourceClass : resourceConfig.getClasses()) {
//		// Resource resource = Resource.from(resourceClass);
//		// logger.info(basePath + resource.getPath() + "\t" + resource.getName());
//		// }
//
//		for (Resource resource : resourceConfig.getResources()) {
//			logger.info(basePath + resource.getPath() + "\t" + resource.getName());
//		}
//		for (Object resource : resourceConfig.getSingletons()) {
//			logger.info(basePath + resource);
//		}
//
//		Dictionary serviceProperties = component.getServiceProperties();
//		serviceProperties.put("alias", basePath);
//		component.setServiceProperties(serviceProperties);
//
//	}
//
//	// @Init
//	// void foo() {
//	// BundleWiring wiring = bundle.adapt(BundleWiring.class);
//	// Collection<String> listResources = wiring.listResources("/", "*.class", BundleWiring.LISTRESOURCES_LOCAL |
//	// BundleWiring.LISTRESOURCES_RECURSE);
//	//
//	// Set<Resource> webResources = new HashSet<>();
//	//
//	// for (String fileName : listResources) {
//	// String className = fileName.replace("/", ".").substring(0, fileName.length() - ".class".length());
//	// try {
//	// Class<?> bundleClass = bundle.loadClass(className);
//	//
//	// if (bundleClass.isAnnotationPresent(Path.class)) {
//	// ClassLoader myClassLoader = getClass().getClassLoader();
//	// ClassLoader originalContextClassLoader = Thread.currentThread().getContextClassLoader();
//	// try {
//	// logger.info("bundleClass " + bundleClass);
//	// Thread.currentThread().setContextClassLoader(myClassLoader);
//	// webResources.add(Resource.from(bundleClass));
//	// } finally {
//	// Thread.currentThread().setContextClassLoader(originalContextClassLoader);
//	// }
//	//
//	// }
//	// } catch (ClassNotFoundException e) {
//	//
//	// }
//	// }
//	//
//	// // ClassLoader tccl = Thread.currentThread().getContextClassLoader();
//	// // try {
//	// // Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
//	//
//	// ResourceConfig resourceConfig = new ResourceConfig();
//	// // resourceConfig.setClassLoader(wiring.getClassLoader());
//	// resourceConfig.setApplicationName(bundle.getSymbolicName());
//	//
//	// resourceConfig.registerInstances(webResources);
//	//
//	// String basePath = "/service";
//	// logger.info("Adding JAX-RS application " + resourceConfig.getApplicationName());
//	// logger.info(resourceConfig.getClasses().size() + " classes, alias: " + basePath);
//	// // for (Class<?> resourceClass : resourceConfig.getClasses()) {
//	// // Resource resource = Resource.from(resourceClass);
//	// // logger.info(basePath + resource.getPath() + "\t" + resource.getName());
//	// // }
//	//
//	// for (Resource resource : resourceConfig.getResources()) {
//	// logger.info(basePath + resource.getPath() + "\t" + resource.getName());
//	// }
//	// for (Object resource : resourceConfig.getSingletons()) {
//	// logger.info(basePath + resource);
//	// }
//	//
//	// Dictionary serviceProperties = component.getServiceProperties();
//	// serviceProperties.put("alias", basePath);
//	// component.setServiceProperties(serviceProperties);
//	//
//	// }
//
//	private void setResourceConfig(ResourceConfig resourceConfig) {
//		/*
//		 * The ServletContainer constructor takes the ResourceConfig as constructor argument, but the Application which
//		 * is used to create the ResourceConfig is not available at the time when this AdapterService is created, nor
//		 * when the factory method is called. Reflections are the only way to set the ResourceConfig.
//		 */
//		try {
//			Field resourceConfigField = ServletContainer.class.getDeclaredField("resourceConfig");
//			resourceConfigField.setAccessible(true);
//			resourceConfigField.set(this, resourceConfig);
//		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
//			// could happen if the ServletContainer implementation gets changes
//			throw new RuntimeException("Application->ServletContainer Adapter initialization failed.", e);
//		}
//	}
//}
