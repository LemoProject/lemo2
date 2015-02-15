package de.lemo.analysis.circlegraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import de.lemo.plugin.api.Analysis;

@Component
@Service
public class CircleGraphAnalysis implements Analysis {

	public final static String PATH = "circle-graph";

	private final Map<String, String> properties;
	{
		// TODO i18n
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(Analysis.DESCRIPTION_SHORT, "Zeigt Navigationsschritte der Nutzer zwischen einzelnen Lernobjekten.");
		properties.put(Analysis.DESCRIPTION_LONG, "Mit der Analyse „Circle Graph“ können Sie einen Einblick in das Navigationsverhalten der Nutzer erhalten, "
				+ "insbesondere in die Reihenfolge, in der Studierende die Lernobjekte aufrufen.");

		properties.put(Analysis.ICON_COLOR, "img/icon-color.svg");
		properties.put(Analysis.ICON_MONOCHROME, "img/icon-monochrome.svg");
		properties.put(Analysis.IMAGE_PREVIEW, "img/preview.png");
		this.properties = Collections.unmodifiableMap(properties);
	}

	private final List<String> scripts;
	{
		List<String> scripts = new ArrayList<String>();
		scripts.add("js/circlegraph.js");
		scripts.add("js/fake_data.js");
		this.scripts = Collections.unmodifiableList(scripts);
	}

	@Override
	public String getPath() {
		return PATH;
	}

	@Override
	public String getName() {
		return "Circle Graph";
	}

	@Override
	public Map<String, String> getProperties() {
		return properties;
	}

	@Override
	public List<String> getScriptPaths() {
		return scripts;
	}

}
