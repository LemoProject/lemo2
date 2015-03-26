package de.lemo.webapp;

import java.text.Normalizer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ApplicationPath;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;

import de.lemo.plugin.api.Analysis;
import freemarker.cache.ClassTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;

@ApplicationPath(LemoResourceConfig.APP_PATH)
//@Component(immediate = true, metatype = false)
//@Service(Application.class)
public class LemoResourceConfig extends ResourceConfig {

//	@Reference(name = "analyses", referenceInterface = Analysis.class, cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC, bind = "bindAnalysis", unbind = "unbindAnalysis")
	private Map<String, Analysis> analyses = new HashMap<>();
	private Map<String, Analysis> analysesReadOnlyView = Collections.unmodifiableMap(analyses);

	public static final String APP_PATH = "/lemo";
	public static final String HOME_PAGE = "/";
	public static final String ANALYTICS_PAGE = "/analytics";

	public static final String ASSETS = APP_PATH + "/assets";

	public LemoResourceConfig() {

		packages(getClass().getPackage().getName());
		register(new ApplicationBinder());

		property(FreemarkerMvcFeature.TEMPLATES_BASE_PATH, "/templates");
		property(FreemarkerMvcFeature.TEMPLATE_OBJECT_FACTORY, createFreemarkerConiguration());
		register(FreemarkerMvcFeature.class);

	}

	public Map<String, Analysis> getAnalyses() {
		return analysesReadOnlyView;
	}


	protected void bindAnalysis(Analysis analysis) {
		analyses.put(getAnalysisPagePath(analysis), analysis);
	}

	protected void unbindAnalysis(Analysis analysis) {
		analyses.remove(getAnalysisPagePath(analysis));
	}

	private Configuration createFreemarkerConiguration() {
		try {
			Configuration configuration = new Configuration();
			BeansWrapper beansWrapper = BeansWrapper.getDefaultInstance();

			configuration.setTemplateLoader(new ClassTemplateLoader(getClass(), "/"));

			configuration.setSharedVariable("basePath", APP_PATH);
			configuration.setSharedVariable("assetPath", ASSETS);

			configuration.setSharedVariable("homePagePath", APP_PATH + HOME_PAGE);
			configuration.setSharedVariable("analyticsPagePath", APP_PATH + ANALYTICS_PAGE);
			configuration.setSharedVariable("analysisPluginPath", "");

			configuration.setSharedVariable("analysisPlugins", beansWrapper.wrap(analyses));

			return configuration;
		} catch (TemplateModelException e) {
			throw new RuntimeException("Failed to create template config", e);
		}
	}

	private String getAnalysisPagePath(Analysis analysis) {
		/* TODO is naive transliteration for urls necessary? */
		// TODO use Pattern.compile once

		// separate letters and diacritics
		String analysisPath = Normalizer.normalize(analysis.getClass().getName(), Normalizer.Form.NFD);
		// strip non-ascii
		analysisPath = analysisPath.replaceAll("[^\\p{ASCII}]", "");
		// trim leading non-alphanumeric
		analysisPath = analysisPath.replaceAll("^[^\\p{Alnum}]*|[^\\p{Alnum}]*$", "");
		// replace non-alphanumeric sequences with single dash
		analysisPath = analysisPath.replaceAll("[^\\p{Alnum}]+", "-");
		return analysisPath.toLowerCase();
	}

	// Enable current OSGi-managed component for injection
	private class ApplicationBinder extends AbstractBinder {
		@Override
		protected void configure() {
			bind(LemoResourceConfig.this).to(LemoResourceConfig.class);
		}
	}

}
