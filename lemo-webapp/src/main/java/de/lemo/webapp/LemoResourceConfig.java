package de.lemo.webapp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;

import de.lemo.plugin.api.Analysis;
import freemarker.cache.ClassTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;

@ApplicationPath("/lemo")
@Component(immediate = true, metatype = false)
@Service(Application.class)
public class LemoResourceConfig extends ResourceConfig {

	@Reference(name = "analyses", referenceInterface = Analysis.class, cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC, bind = "bindAnalysis", unbind = "unbindAnalysis")
	private Map<String, Analysis> analyses = new HashMap<>();
	private Map<String, Analysis> analysesReadOnlyView = Collections.unmodifiableMap(analyses);

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
		analyses.put(analysis.getId(), analysis);
	}

	protected void unbindAnalysis(Analysis analysis) {
		analyses.remove(analysis.getId());
	}

	private Configuration createFreemarkerConiguration() {
		try {
			Configuration configuration = new Configuration();
			BeansWrapper beansWrapper = BeansWrapper.getDefaultInstance();

			configuration.setTemplateLoader(new ClassTemplateLoader(getClass(), "/"));

			String basePath = "/lemo";
			configuration.setSharedVariable("basePath", basePath);
			configuration.setSharedVariable("assetPath", basePath + "/assets");
			configuration.setSharedVariable("analysisPath", basePath + "/analysis");
			configuration.setSharedVariable("analysisPagePath", basePath + "/analytics");

			configuration.setSharedVariable("analysisPlugins", beansWrapper.wrap(analyses.values()));

			configuration.setSharedVariable("emptyList", Collections.emptyList());

			return configuration;
		} catch (TemplateModelException e) {
			throw new RuntimeException("Failed to create template config", e);
		}
	}

	// Enable current OSGi-managed component for injection
	private class ApplicationBinder extends AbstractBinder {
		@Override
		protected void configure() {
			bind(LemoResourceConfig.this).to(LemoResourceConfig.class);
		}
	}

}
