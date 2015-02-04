package de.lemo.analysis.activitytime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import de.lemo.plugin.api.Analysis;

@Component
@Service
public class ActivityTimeAnalysis implements Analysis {

	public final static String ID = "activity-time";

	private List<String> scripts = new ArrayList<String>();
	{
		scripts.add("js/activitytime.js");
		scripts = Collections.unmodifiableList(scripts);
	}

	@Override
	public String getId() {  
		return ID;
	}

	@Override
	public String getName() {
		return "Activity Time";
	}

	@Override
	public String getShortDescription() {
		return "Zeigt Aktivitäten der Nutzer in Kursen an.";
	}

	@Override
	public String getLongDescription() {
		return "Mit der Analyse „Aktivität Zeit (Heatmap)“ erhalten Sie einen Überblick über die Intensität  der  täglichen Materialnutzung  im "
				+ " Lauf  der  Zeit  anhand  verschieden intensiv  gefärbten  Farbfeldern  und  können  diese  mit  der  Intensität  der  Aktivität in anderen Kursen vergleichen.";
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
		return "img/icon.svg";
	}

	@Override
	public String getPreviewImagePath() {
		return "img/preview.png";
	}

}
