package de.lemo.analysis.activitytime;

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
public class ActivityTimeAnalysis implements Analysis {

	public final static String PATH = "activity-time";

	private final Map<String, String> properties; 
	{
		// TODO i18n
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(Analysis.DESCRIPTION_SHORT, "Zeigt Aktivitäten der Nutzer in Kursen an.");
		properties.put(Analysis.DESCRIPTION_LONG, "Mit der Analyse „Aktivität Zeit (Heatmap)“ erhalten Sie einen Überblick über die Intensität der"
				+ " täglichen Materialnutzung  im  Lauf  der  Zeit  anhand  verschieden intensiv  gefärbten  Farbfeldern  und  können "
				+ " diese  mit  der  Intensität  der  Aktivität in anderen Kursen vergleichen.");

		properties.put(Analysis.ICON_COLOR, "img/icon-color.svg");
		properties.put(Analysis.ICON_MONOCHROME, "img/icon-monochrome.svg");
		properties.put(Analysis.IMAGE_PREVIEW, "img/preview.png");
		this.properties = Collections.unmodifiableMap(properties);
	}

	private final List<String> scripts;
	{
		List<String> scripts = new ArrayList<String>();
		scripts.add("js/activitytime.js");
		this.scripts = Collections.unmodifiableList(scripts);
	}

	@Override
	public String getName() {
		return "Activity Time";
	}

	@Override
	public List<String> getScriptPaths() {
		return scripts;
	}

	@Override
	public String getPath() {
		return PATH;
	}

	@Override
	public Map<String, String> getProperties() {
		return properties;
	}

}
