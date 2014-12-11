package de.lemo.launcher;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.CountDownLatch;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.framework.startlevel.FrameworkStartLevel;

public class Launcher {

	private static final String MODULES = ""

	/**/+ " lemo-core"
	/**/+ " lemo-logging"
	/**/+ " lemo-server"

	/**/+ " lemo-an"

	/**/+ " lemo-persistence"
	/**/+ " lemo-persistence-entities"

//	 /**/+ " lemo-plugins"

	/**/;

	private static File launcherRoot = new File("").getAbsoluteFile();
	private static String projectRoot = launcherRoot.getParent();

	private static final String fileinstallDirectories;
	static {
		String[] modules = MODULES.trim().split("\\s+");

		String lemoModuleDirs = "";
		for (String module : modules) {
			if (!module.isEmpty()) {
				String moduleDir = projectRoot + "/" + module + "/target/bundle";
				moduleDir = moduleDir.replace('/', File.separatorChar);
				lemoModuleDirs += "," + moduleDir;
				System.out.println("include module: " + module + "\t" + moduleDir);
			}
		}
		lemoModuleDirs = lemoModuleDirs.substring(1);

		String libBundleDir = projectRoot + "/target/bundles";
		libBundleDir = libBundleDir.replace('/', File.separatorChar);
		System.out.println("bundle dependency dir:\t\t" + libBundleDir);

		// fileinstallDirectories = libBundleDir;
		// fileinstallDirectories = lemoModuleDirs + "," + libBundleDir;
		// fileinstallDirectories = libBundleDir + "," + lemoModuleDirs;
		fileinstallDirectories = lemoModuleDirs;

		System.out.println("fileinstallDirectories:\t\t" + fileinstallDirectories);

	}

	private static Framework framework;

	public static void main(String[] argv) throws Exception {

		System.out.println("Debug Launcher started.");

		Map<String, String> frameworkConfig = new HashMap<>();

		frameworkConfig.put(Constants.FRAMEWORK_STORAGE, launcherRoot + "/target/felix-cache");
		frameworkConfig.put(Constants.FRAMEWORK_STORAGE_CLEAN, Constants.FRAMEWORK_STORAGE_CLEAN_ONFIRSTINIT);
		frameworkConfig.put(Constants.FRAMEWORK_BSNVERSION, Constants.FRAMEWORK_BSNVERSION_SINGLE);

		frameworkConfig.put("org.osgi.service.http.port", "8080");

		frameworkConfig.put("felix.log.level", "3");

		// frameworkConfig.put(FileInstallConfig.ENABLE_CONFIG_SAVE, "true");
		frameworkConfig.put(FileInstallConfig.DIR, fileinstallDirectories);
		frameworkConfig.put(FileInstallConfig.POLL, "1000");
		frameworkConfig.put(FileInstallConfig.UPDATE_WITH_LISTENERS, "true");
		frameworkConfig.put(FileInstallConfig.NO_INITIAL_DELAY, "true");
		// frameworkConfig.put(FileInstallConfig.LOG_DEFAULT, FileInstallConfig.LOG_STDOUT);
		// frameworkConfig.put(FileInstallConfig.LOG_DEFAULT, FileInstallConfig.LOG_JUL);
		// frameworkConfig.put(FileInstallConfig.LOG_LEVEL, "2");
		frameworkConfig.put(FileInstallConfig.START_NEW_BUNDLES, "true");
		// frameworkConfig.put(FileInstallConfig.ACTIVE_LEVEL, "6");
		frameworkConfig.put(FileInstallConfig.START_LEVEL, "5");

		//
		// change default system packages
		//

		// jre javax.transaction is incomplete, lemo-core provides full implementation
		// PropertiesReader propertiesReader = new PropertiesReader(new
		// InputStreamReader(Felix.class.getResourceAsStream("/default.properties")));
		// Properties properties = new Properties();
		// properties.load(propertiesReader);
		// String systemPackages = properties.getProperty(Constants.FRAMEWORK_SYSTEMPACKAGES);
		// systemPackages =
		// systemPackages.replace("javax.transaction.xa;version=\"0.0.0.1_007_JavaSE\", javax.transaction;version=\"0.0.0.1_007_JavaSE\",",
		// "");
		// frameworkConfig.put(Constants.FRAMEWORK_SYSTEMPACKAGES, systemPackages);

		//
		// add extra system packages
		//

		// xerces xml has classloader issues
		String xmlApi = "javax.xml; javax.xml.datatype; javax.xml.namespace; javax.xml.parsers; javax.xml.transform; javax.xml.transform.dom; javax.xml.transform.sax; javax.xml.transform.stream; javax.xml.validation; javax.xml.xpath; org.apache.xmlcommons; org.w3c.dom.*; org.xml.sax.*;version=1.4.1";
		String transactionApi = "javax.transaction; javax.transaction.xa; version=1.0.0";
		String injectApi = "javax.inject; version=1.0.0";
		String jaxpXercesImpl = "org.apache.xerces.jaxp; version=1.0.0";
		String systemPackagesExtra = injectApi + "," + xmlApi + "," + transactionApi + "," + jaxpXercesImpl;
		frameworkConfig.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, systemPackagesExtra);

		//
		// load framework
		//

		FrameworkFactory frameworkFactory = loadFrameworkFactory();
		framework = frameworkFactory.newFramework(frameworkConfig);

		framework.init();

		BundleContext context = framework.getBundleContext();

		for (File file : new File(projectRoot + "/target/startbundles").listFiles()) {
			if (file.getName().endsWith(".jar")) {
				try {
					context.installBundle("file:" + file.getAbsolutePath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		framework.start();

		// startBundleWithStartLevel(framework.getBundleContext(), "slf4j.api", 1);
		// startBundleWithStartLevel(framework.getBundleContext(), "slf4j.log4j12", 1);
		// startBundleWithStartLevel(framework.getBundleContext(), "log4j", 1);
		// startBundleWithStartLevel(framework.getBundleContext(), "ch.qos.logback.core", 1);
		// startBundleWithStartLevel(framework.getBundleContext(), "ch.qos.logback.classic", 1);
		// startBundleWithStartLevel(framework.getBundleContext(), "org.slf4j.osgi-over-slf4j", 1);
		startBundleWithStartLevel(framework.getBundleContext(), "org.ops4j.pax.logging.pax-logging-api", 1);
		startBundleWithStartLevel(framework.getBundleContext(), "org.ops4j.pax.logging.pax-logging-service", 1);

		startBundleWithStartLevel(framework.getBundleContext(), "org.apache.felix.eventadmin", 2);
		startBundleWithStartLevel(framework.getBundleContext(), "org.apache.felix.configadmin", 2);

		awaitStartLevel(3);

		for (File file : new File(projectRoot + "/target/bundles").listFiles()) {
			if (file.getName().endsWith(".jar")) {
				try {
					final Bundle bundle = context.installBundle("file:" + file.getAbsolutePath());
					if (bundle.getSymbolicName() == null) {
						System.out.println("Not a bundle: " + file.getName());
						System.exit(1);
					}
					setStartLevel(bundle, 5);
					bundle.start();
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
		awaitStartLevel(6);

		startBundleWithStartLevel(framework.getBundleContext(), "org.apache.felix.fileinstall", 3);

		// ServiceReference configurationAdminReference =
		// context.getServiceReference(ConfigurationAdmin.class.getName());
		//
		// System.out.println("configurationAdminReference " + configurationAdminReference);
		// if (configurationAdminReference != null) {
		//
		//
		// ConfigurationAdmin confAdmin = (ConfigurationAdmin) context.getService(configurationAdminReference);
		//
		// // Configuration configuration = confAdmin.getConfiguration("org.apache.felix.fileinstall");
		// Configuration configuration = confAdmin.createFactoryConfiguration("org.apache.felix.fileinstall", null);
		// Dictionary config = new Hashtable<>();
		//
		// config.put(FileInstallConfig.ENABLE_CONFIG_SAVE, "true");
		// config.put(FileInstallConfig.DIR, fileinstallDirectories);
		// config.put(FileInstallConfig.POLL, "1000");
		// config.put(FileInstallConfig.UPDATE_WITH_LISTENERS, "true");
		// config.put(FileInstallConfig.NO_INITIAL_DELAY, "true");
		// config.put(FileInstallConfig.LOG_DEFAULT, FileInstallConfig.LOG_STDOUT);
		// // frameworkConfig.put(FileInstallConfig.LOG_DEFAULT, DirectoryWatcher.LOG_JUL);
		// config.put(FileInstallConfig.LOG_LEVEL, "4");
		// config.put(FileInstallConfig.START_NEW_BUNDLES, "true");
		// // frameworkConfig.put(FileInstallConfig.ACTIVE_LEVEL, "20");
		// config.put(FileInstallConfig.START_LEVEL, "20");
		//
		// configuration.update(config);
		//
		// }

		// Hashtable<String, Object> props = new Hashtable<String, Object>();
		// props.put("url.handler.protocol", JarDirUrlHandler.PROTOCOL);
		// ServiceRegistration<?> registerService =
		// framework.getBundleContext().registerService(org.osgi.service.url.URLStreamHandlerService.class.getName(),
		// new JarDirUrlHandler(), props);
		//
		// Bundle launcherBundle =
		// context.installBundle("jardir:D:/workspace/lemo-dms/lemo-logging/target/bundle/classes/");
		// Bundle installBundle = installBundle(framework.getBundleContext(), Launcher.class);

		// framework.getBundleContext().installBundle("jardir:"
		// +Launcher.class.getProtectionDomain().getCodeSource().getLocation().toString());
		// registerService.unregister();

		// launcherBundle.start();

		framework.waitForStop(0);
		System.exit(0);
	}

	private static void awaitStartLevel(int startLevel) {
		FrameworkStartLevel frameworkLevel = framework.adapt(FrameworkStartLevel.class);
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		framework.adapt(FrameworkStartLevel.class).setStartLevel(startLevel, new FrameworkListener() {
			@Override
			public void frameworkEvent(FrameworkEvent event) {
				countDownLatch.countDown();
			}
		});
		System.out.println(startLevel + " " + frameworkLevel.getStartLevel());

		if (frameworkLevel.getStartLevel() >= startLevel) {
			countDownLatch.countDown();
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static boolean startBundleWithStartLevel(BundleContext context, String symbolicName, int startLevel) throws BundleException {
		for (Bundle bundle : context.getBundles()) {
			if (bundle.getSymbolicName().equals(symbolicName)) {
				setStartLevel(bundle, startLevel);
				bundle.start(Bundle.START_ACTIVATION_POLICY);
				return true;
			}
		}
		System.out.println("NOT FOUND " + symbolicName);
		return false;
	}

	private static void setStartLevel(Bundle bundle, int startLevel) {
		bundle.adapt(BundleStartLevel.class).setStartLevel(startLevel);
	}

	private static void initLogging(BundleContext context) throws BundleException {

		// add jar url handler
		// Hashtable<String, Object> props = new Hashtable<String, Object>();
		// props.put("url.handler.protocol", JarDirUrlHandler.PROTOCOL);
		// ServiceRegistration<?> registerService =
		// context.registerService(org.osgi.service.url.URLStreamHandlerService.class.getName(), new JarDirUrlHandler(),
		// props);

		// System.out.println(projectRoot);
		// String javaapi = projectRoot + "/lemo-core/target/bundles/provided/";
		// for (File file : new File(javaapi).listFiles()) {
		// if (file.isFile() && file.getName().endsWith(".jar")) {
		// System.out.println("installing " + file);
		// context.installBundle("file:" + file.getAbsolutePath());
		// }
		// }

		// CORE

		// String coreLocation = "jardir:" + projectRoot + "/lemo-core/target/bundle/classes";
		// Bundle coreBundle = context.installBundle(coreLocation);
		// coreBundle.start();
		// System.out.println("coreBundle start");

		// LOGGER

		// felix logger dependency
		// installBundle(context, org.slf4j.Logger.class);
		// installBundle(context, org.slf4j.impl.StaticLoggerBinder.class);
		// installBundle(context, org.slf4j.helpers.Util.class);
		// installBundle(context, org.apache.felix.log.Activator.class);
		// installBundle(context, org.ops4j.pax.logging.PaxLogger.class); // api
		// installBundle(context, org.ops4j.pax.logging.service.internal.PaxLoggerImpl.class);

		// dependencies
		// String coreDependenciesLocation2 = projectRoot + "/lemo-logging/target/bundles/provided/";
		// File file2 = new File(coreDependenciesLocation2);
		// if (file2.listFiles() != null) {
		// for (File file : new File(coreDependenciesLocation2).listFiles()) {
		// if (file.isFile() && file.getName().endsWith(".jar")) {
		// System.out.println("installing " + file);
		// context.installBundle("file:" + file.getAbsolutePath());
		// }
		// }
		// }

		// // logger bundle
		String loggingLocation = "jardir:" + projectRoot + "/lemo-logging/target/bundle/classes";
		Bundle loggingBundle = context.installBundle(loggingLocation);
		loggingBundle.start();
		System.out.println("loggingBundle start");

		// remove jar url handler
		// registerService.unregister();

	}

	private static Bundle installBundle(BundleContext context, Class<?> class1) throws BundleException {
		String location = class1.getProtectionDomain().getCodeSource().getLocation().toString();
		return context.installBundle(location);
	}

	private static List<Bundle> installBundles(BundleContext context, List<Class<?>> bundles) throws BundleException {
		List<Bundle> installed = new ArrayList<>();
		for (Class<?> class1 : bundles) {
			Bundle installBundle = installBundle(context, class1);
			installed.add(installBundle);
		}
		return installed;
	}

	private static void startBundles(BundleContext context, List<Bundle> bundles) throws BundleException {
		for (Bundle bundle : bundles) {
			bundle.start();
		}
	}

	private static void printBundleInfo(Bundle[] bundles, boolean onlyWarning) {

		System.out.println("Bundles: " + bundles.length);
		System.out.println("---+-------------+--------------------------------------------------------");
		for (Bundle bundle : bundles) {
			String name = bundle.getSymbolicName();
			printBundleInfo(bundle);
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

	private static FrameworkFactory loadFrameworkFactory() {
		try {
			return ServiceLoader.load(FrameworkFactory.class).iterator().next();
		} catch (Exception e) {
			throw new IllegalStateException("Unable to load FrameworkFactory service.", e);
		}
	}
}