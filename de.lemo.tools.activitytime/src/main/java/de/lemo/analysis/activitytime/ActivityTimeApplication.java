package de.lemo.analysis.activitytime;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath(ActivityTimeAnalysis.PATH)
public class ActivityTimeApplication extends ResourceConfig {

	public ActivityTimeApplication() {

		packages(getClass().getPackage().getName());

		// property(FreemarkerMvcFeature.TEMPLATE_OBJECT_FACTORY, createFreemarkerConiguration());
		// property(FreemarkerMvcFeature.TEMPLATES_BASE_PATH, "/templates");
		// register(FreemarkerMvcFeature.class);
	}

	// private Configuration createFreemarkerConiguration() {
	// try {
	// Configuration configuration = new Configuration();
	// configuration.setTemplateLoader(new ClassTemplateLoader(getClass(), "/"));
	// configuration.setSharedVariable("basePath", "/lemo");
	// configuration.setSharedVariable("assetPath", "/lemo/assets");
	// configuration.setSharedVariable("analysisPath", "/lemo/analysis");
	// return configuration;
	// } catch (TemplateModelException e) {
	// throw new RuntimeException("Failed to create template config", e);
	// }
	// }
}
