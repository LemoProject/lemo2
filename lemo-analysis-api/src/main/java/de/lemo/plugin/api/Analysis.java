package de.lemo.plugin.api;

import java.net.URL;
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

	URL getIcon();

	URL getPreviewImage();

	/**
	 * A list of URLs that will be included as scripts the analysis page.
	 */
	List<URL> getScripts();

	/**
	 * A list of URLs that will included as stylesheets in the analysis page.
	 */
	List<URL> getStyleSheets();

}