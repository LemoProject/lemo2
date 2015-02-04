package de.lemo.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import de.lemo.plugin.api.Analysis;

@Component
@Service
public class CircleGraphAnalysis implements Analysis {

	public final static String ID = "circle-graph";

	private List<String> scripts = new ArrayList<String>();
	{
		scripts.add("js/circlegraph.js");
		scripts = Collections.unmodifiableList(scripts);
	}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public String getName() {
		return "Circle Graph";
	}

	@Override
	public String getShortDescription() {
		return "Zeigt Navigationsschritte der Nutzer zwischen einzelnen Lernobjekten.";
	}

	@Override
	public String getLongDescription() {
		return "Mit der Analyse „Circle Graph“ können Sie einen Einblick in das Navigationsverhalten der Nutzer erhalten, "
				+ "insbesondere in die Reihenfolge, in der Studierende die Lernobjekte aufrufen.";
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
		return "img/circlegraph-icon.svg";
	}

	@Override
	public String getPreviewImagePath() {
		return "img/preview.png";
	}

}
