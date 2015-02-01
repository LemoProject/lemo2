package de.lemo.plugin.api;

import java.util.List;

public interface Analysis {

	String getId();

	/**
	 * The name or title of this analysis.
	 */
	String getName();

	/**
	 * A short description of the main analysis.
	 */
	String getShortDescription();

	/**
	 * A longer description of the main analysis.
	 */
	String getLongDescription();

	String getIconPath();

	String getPreviewImagePath();

	/**
	 * A list of URLs that will be included as scripts the analysis page.
	 */
	List<String> getScriptPaths();

	/**
	 * A list of URLs that will included as style sheets in the analysis page.
	 */
	List<String> getStyleSheetPaths();

}