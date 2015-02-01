package de.lemo.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import de.lemo.plugin.api.Analysis;

@Component
@Service
public class TestAnalysis implements Analysis {

	private List<String> scripts = new ArrayList<String>();
	{
		scripts.add("/js/circlegraph.js");
		scripts = Collections.unmodifiableList(scripts);
	}

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
	public List<String> getScriptPaths() {
		return scripts;
	}

	@Override
	public List<String> getStyleSheetPaths() {
		return Collections.emptyList();
	}

	@Override
	public String getIconPath() {
		return null;
	}

	@Override
	public String getPreviewImagePath() {
		return null;
	}

}
