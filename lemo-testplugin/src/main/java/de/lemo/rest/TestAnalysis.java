package de.lemo.rest;

import java.net.URL;
import java.util.List;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import de.lemo.plugin.api.Analysis;

@Component
@Service
public class TestAnalysis implements Analysis {

	@Override 
	public String getId() {
		return "test-analysis";
	} 
	
	@Override 
	public String getName() {
		return "Test Analysis";
	} 

	@Override
	public String getShortDescription() {
		return "short description";
	}

	@Override
	public String getLongDescription() {
		return "long description";
	}

	@Override
	public URL getIcon() {
		return null;
	}

	@Override
	public URL getPreviewImage() {
		return null;
	}

	@Override
	public List<URL> getScripts() {
		return null;
	}

	@Override
	public List<URL> getStyleSheets() {
		return null;
	}

}
