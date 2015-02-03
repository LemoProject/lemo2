package de.lemo.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;

@ApplicationPath(CircleGraphAnalysis.PATH)
@Component(immediate = true, metatype = false)
@Service(Application.class)
public class RestApplicationComponent2 extends ResourceConfig {

	public RestApplicationComponent2() {

		packages(getClass().getPackage().getName());

		// property(FreemarkerMvcFeature.TEMPLATE_OBJECT_FACTORY, createFreemarkerConiguration());
		property(FreemarkerMvcFeature.TEMPLATES_BASE_PATH, "/templates");
		register(FreemarkerMvcFeature.class);
	}

	private Configuration createFreemarkerConiguration() {
		try {
			Configuration configuration = new Configuration();
			configuration.setTemplateLoader(new ClassTemplateLoader(getClass(), "/"));
			configuration.setSharedVariable("basePath", "/lemo");
			configuration.setSharedVariable("assetPath", "/lemo/assets");
			configuration.setSharedVariable("analysisPath", "/lemo/analysis");
			return configuration;
		} catch (TemplateModelException e) {
			throw new RuntimeException("Failed to create template config", e);
		}
	}
}
