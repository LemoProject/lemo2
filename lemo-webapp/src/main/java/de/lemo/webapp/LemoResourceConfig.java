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
import org.apache.felix.scr.annotations.References;
import org.apache.felix.scr.annotations.Service;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature;

import de.lemo.plugin.api.Analysis;

@ApplicationPath("/lemo")
@Component(immediate = true, metatype = false)
@Service(Application.class)
@References({ @Reference(name = "analyses", referenceInterface = Analysis.class, cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC, bind = "registerAnalysis", unbind = "unregisterAnalysis"), })
public class LemoResourceConfig extends ResourceConfig {

	private Map<String, Analysis> analyses = new HashMap<>();
	private Map<String, Analysis> analysesReadOnlyView = Collections.unmodifiableMap(analyses);

	public LemoResourceConfig() {
  
		packages(getClass().getPackage().getName());
	  
		register(new ApplicationBinder()); 
 
		// property(MustacheMvcFeature.TEMPLATE_BASE_PATH, "/templates");
		// register(MustacheMvcFeature.class);
		register(FreemarkerMvcFeature.class);
		  
	}  
   
	public Map<String, Analysis> getAnalyses() {
		return analysesReadOnlyView;  
	}
         
	protected void registerAnalysis(Analysis analysis) {
		analyses.put(analysis.getId(), analysis);
	}

	protected void unregisterAnalysis(Analysis analysis) { 
		analyses.remove(analysis.getId());
	}
  
	// Enable current OSGi-managed component for injection
	private class ApplicationBinder extends AbstractBinder {
		@Override
		protected void configure() {
			bind(LemoResourceConfig.this).to(LemoResourceConfig.class);
		}
	}

}
