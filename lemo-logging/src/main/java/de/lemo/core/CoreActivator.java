package de.lemo.core;

import java.io.File;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 

public class CoreActivator implements BundleActivator, ServiceListener {
	// private LogbackAdaptor m_backendlogger = new LogbackAdaptor();
	// private LinkedList<LogReaderService> m_readers = new LinkedList<LogReaderService>();
	// private BundleContext m_context;
	private static File launcherRoot = new File("").getAbsoluteFile();
	private static String projectRoot = launcherRoot.getParent();

	final static Logger logger = LoggerFactory.getLogger(CoreActivator.class);

	// private Log m_jclLogger;
	// private org.apache.juli.logging.Log m_juliLogger;

	@Override
	public void start(final BundleContext context) throws Exception {

		System.out.println(LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME).getClass());

		printBundleInfo(context.getBundles(), false);

		// final Bundle framework = context.getBundle(0);
		//
		// for (File file : new File(projectRoot + "/target/startbundles").listFiles()) {
		// if (file.getName().endsWith(".jar")) {
		// try {
		// context.installBundle("file:" + file.getAbsolutePath());
		// } catch (Exception e) {
		// logger.error("Failed to install bundle jar: " + file, e);
		// }
		// }
		// }
		//
		// final FrameworkStartLevel startLevel = framework.adapt(FrameworkStartLevel.class);
		// startLevel.setInitialBundleStartLevel(1);
		// framework.start();
		// logger.info("Framework Level " + startLevel.getStartLevel());

		// startBundleWithStartLevel(framework.getBundleContext(), "ch.qos.logback.core", 1);
		// startBundleWithStartLevel(framework.getBundleContext(), "ch.qos.logback.classic", 1);
		// startBundleWithStartLevel(framework.getBundleContext(), "org.apache.felix.log", 2);
		//
		// initLogging(framework.getBundleContext());
		//
		// startBundleWithStartLevel(framework.getBundleContext(), "org.ops4j.pax.logging.pax-logging-api", 1);
		// startBundleWithStartLevel(framework.getBundleContext(), "org.ops4j.pax.logging.pax-logging-service", 1);

		// startBundleWithStartLevel(framework.getBundleContext(), "org.apache.felix.configadmin", 2);
		// startBundleWithStartLevel(framework.getBundleContext(), "org.apache.felix.eventadmin", 2);
		// startBundleWithStartLevel(framework.getBundleContext(), "org.apache.felix.fileinstall", 3);

		// startLevel.setStartLevel(25, new FrameworkListener() {
		// @Override
		// public void frameworkEvent(FrameworkEvent event) {
		// logger.info("Framework Level event " + startLevel.getStartLevel());
		// logger.info("Framework Level event " + event.getThrowable());
		// printBundleInfo(event.getBundle().getBundleContext().getBundles(), false);
		// }
		// });

		// startLevel.setStartLevel(4, new FrameworkListener() {
		// @Override
		// public void frameworkEvent(FrameworkEvent event) {
		// logger.info("Framework Level " + startLevel.getStartLevel());
		// printBundleInfo(framework.getBundleContext().getBundles(), false);
		// final FrameworkStartLevel f = framework.adapt(FrameworkStartLevel.class);
		//
		// f.setStartLevel(6, new FrameworkListener() {
		// @Override
		// public void frameworkEvent(FrameworkEvent event) {
		// logger.info("Framework Level " + f.getStartLevel());
		// printBundleInfo(framework.getBundleContext().getBundles(), false);
		// final FrameworkStartLevel f = framework.adapt(FrameworkStartLevel.class);
		//
		// f.setStartLevel(25);
		//
		// }
		// });
		// }
		// });

		// Bundle installBundle = installBundle(context, ConfigurationAdminImpl.class);
		// installBundle.start();
		// Bundle installBundle2 = installBundle(context, EventAdminImpl.class);
		// installBundle2.start();
		// Bundle installBundle3 = installBundle(context, FileInstall.class);
		// installBundle3.start();

		// for (Bundle bundle : framework.getBundleContext().getBundles()) {
		// if (bundle.getSymbolicName().equals("org.osgi.logging")) {
		// bundle.start();
		// }
		// }
		// for (Bundle bundle : framework.getBundleContext().getBundles()) {
		// System.out.println("check " + bundle.getSymbolicName());
		// if (bundle.getSymbolicName().equals("de.lemo.logging")) {
		// System.out.println("starting de.lemo.logging");
		// bundle.start(Bundle.START_TRANSIENT);
		// }
		// }

		// new Thread() {
		// public void run() {
		// try {
		// //
		// while (true) {
		// sleep(2000);
		// printBundleInfo(context.getBundles(), false);

		// System.out.println("try restart");
		// for (Bundle bundle : context.getBundles()) {
		//
		// if (bundle.getSymbolicName().startsWith("de.lemo") && bundle.getState() != Bundle.ACTIVE) {
		// System.out.println(bundle.getSymbolicName());
		// try {
		// bundle.start();
		// } catch (BundleException e) {
		//
		// e.printStackTrace();
		// }
		// }
		// }
		// }
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// };
		// }.start();

	}

	private static boolean startBundleWithStartLevel(BundleContext context, String symbolicName, int startLevel) throws BundleException {
		for (Bundle bundle : context.getBundles()) {
			if (bundle.getSymbolicName().equals(symbolicName)) {
				bundle.adapt(BundleStartLevel.class).setStartLevel(startLevel);
				bundle.start(Bundle.START_ACTIVATION_POLICY);
				return true;
			}
		}
		System.out.println("NOT FOUND");
		return false;
	}

	// public void start(BundleContext context) throws Exception {
	// m_juliLogger = org.apache.juli.logging.LogFactory.getLog(CoreActivator.class);
	// m_slf4jLogger = LoggerFactory.getLogger(CoreActivator.class);
	// m_jdkLogger = java.util.logging.Logger.getLogger(CoreActivator.class.getName());
	// m_slf4jLogger.info("Starting Example...    (slf4j)");
	// m_jdkLogger.info("Starting Example...    (jdk)");
	// m_juliLogger.info("Starting Example...    (juli)");
	// m_context = context;

	//
	// Register this class as a listener to updates of the service list
	//
	// String filter = "(objectclass=" + LogReaderService.class.getName() + ")";
	// try {
	// context.addServiceListener(this, filter);
	// } catch (InvalidSyntaxException e) {
	// e.printStackTrace();
	// }

	//
	// Register the LogbackAdaptor to all the LogReaderService objects available
	// on the server. That's right, ALL of them.
	//
	// ServiceReference[] refs = context.getServiceReferences(org.osgi.service.log.LogReaderService.class.getName(),
	// null);
	// if (refs != null) {
	// for (int i = 0; i < refs.length; i++) {
	// LogReaderService service = (LogReaderService) context.getService(refs[i]);
	// if (service != null) {
	// m_readers.add(service);
	// service.addLogListener(m_backendlogger);
	// }
	// }
	// }

	// logger = LoggerFactory.getLogger(CoreActivator.class);

	// }

	public void stop(BundleContext context) throws Exception {

		// for (Iterator<LogReaderService> i = m_readers.iterator(); i.hasNext();) {
		// LogReaderService lrs = i.next();
		// lrs.removeLogListener(m_backendlogger);
		// i.remove();
		// }
	}

	// We use a ServiceListener to dynamically keep track of all the LogReaderService service being
	// registered or unregistered
	public void serviceChanged(ServiceEvent event) {
		// System.out.println("serviceChanged!");
		// LogReaderService lrs = (LogReaderService) m_context.getService(event.getServiceReference());
		// if (lrs != null) {
		// if (event.getType() == ServiceEvent.REGISTERED) {
		// m_readers.add(lrs);
		// lrs.addLogListener(m_backendlogger);
		// } else if (event.getType() == ServiceEvent.UNREGISTERING) {
		// lrs.removeLogListener(m_backendlogger);
		// m_readers.remove(lrs);
		// }
		// }
	}

	private static void printBundleInfo(Bundle[] bundles, boolean onlyWarning) {
		// Arrays.sort(bundles, new Comparator<Bundle>() {
		// @Override
		// public int compare(Bundle o1, Bundle o2) {
		// return o1.getSymbolicName().compareTo(o2.getSymbolicName());
		// }
		// });

		System.out.println("Bundles: " + bundles.length);
		System.out.println("---+-------------+--------------------------------------------------------");
		String lastName = "";
		for (Bundle bundle : bundles) {
			String name = bundle.getSymbolicName();
			if (name.equals(lastName) || bundle.getState() != Bundle.ACTIVE) {
				printBundleInfo(bundle);
			} else if (!onlyWarning) {
				printBundleInfo(bundle);
			}
			lastName = name;
		}
		System.out.println("---+-------------+--------------------------------------------------------");
	}

	private static void printBundleInfo(Bundle bundle) {
		String bundleInfo = String.format("%2d | %-11s | %s / %s", bundle.getBundleId(), getState(bundle), bundle.getSymbolicName(), bundle.getVersion());
		System.out.println(bundleInfo);
	}

	private static String getState(Bundle bundle) {
		switch (bundle.getState()) {
		case Bundle.ACTIVE:
			return "Active";
		case Bundle.INSTALLED:
			return "Installed";
		case Bundle.RESOLVED:
			return "Resolved";
		case Bundle.STARTING:
			return "Starting";
		case Bundle.STOPPING:
			return "Stopping";
		case Bundle.UNINSTALLED:
			return "Uninstalled";
		}
		return "UNKOWN";
	}
}
//
// import java.util.Iterator;
// import java.util.LinkedList;
//
// import org.osgi.framework.BundleActivator;
// import org.osgi.framework.BundleContext;
// import org.osgi.framework.InvalidSyntaxException;
// import org.osgi.framework.ServiceEvent;
// import org.osgi.framework.ServiceListener;
// import org.osgi.service.log.LogReaderService;
// import org.osgi.util.tracker.ServiceTracker;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
//
// public class CoreActivator implements BundleActivator {
//
// private static final Logger LOG = LoggerFactory.getLogger(CoreActivator.class);
//
// private ConsoleLogger m_console = new ConsoleLogger();
// private LinkedList<LogReaderService> m_readers = new LinkedList<LogReaderService>();
//
// private ServiceListener m_servlistener = new ServiceListener() {
// public void serviceChanged(ServiceEvent event) {
// BundleContext bc = event.getServiceReference().getBundle().getBundleContext();
// LogReaderService lrs = (LogReaderService) bc.getService(event.getServiceReference());
// if (lrs != null) {
// if (event.getType() == ServiceEvent.REGISTERED) {
// m_readers.add(lrs);
// lrs.addLogListener(m_console);
// } else if (event.getType() == ServiceEvent.UNREGISTERING) {
// lrs.removeLogListener(m_console);
// m_readers.remove(lrs);
// }
// }
// }
// };
//
// @Override
// public void start(BundleContext context) throws Exception {
// // Get a list of all the registered LogReaderService, and add the console listener
// ServiceTracker logReaderTracker = new ServiceTracker(context, org.osgi.service.log.LogReaderService.class.getName(),
// null);
// logReaderTracker.open();
// Object[] readers = logReaderTracker.getServices();
// if (readers != null) {
// for (int i = 0; i < readers.length; i++) {
// LogReaderService lrs = (LogReaderService) readers[i];
// m_readers.add(lrs);
// lrs.addLogListener(m_console);
// }
// }
//
// logReaderTracker.close();
//
// // Add the ServiceListener, but with a filter so that we only receive events related to LogReaderService
// String filter = "(objectclass=" + LogReaderService.class.getName() + ")";
// try {
// context.addServiceListener(m_servlistener, filter);
// } catch (InvalidSyntaxException e) {
// e.printStackTrace();
// }
// }
//
// @Override
// public void stop(BundleContext context) throws Exception {
// for (Iterator<LogReaderService> i = m_readers.iterator(); i.hasNext();) {
// LogReaderService lrs = i.next();
// lrs.removeLogListener(m_console);
// i.remove();
// }
// }
// }
