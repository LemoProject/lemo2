package de.lemo.plugin.api;

import java.util.List;
import java.util.Map;

public interface Analysis {

	/**
	 * A short description of the main analysis.
	 */
	String DESCRIPTION_SHORT = "description_short";

	/**
	 * A longer description of the main analysis.
	 */
	String DESCRIPTION_LONG = "description_long";

	String IMAGE_PREVIEW = "image_preview";

	String ICON_COLOR = "icon_color";

	String ICON_MONOCHROME = "icon_monochrome";

	/**
	 * The name or title of this analysis. Will be used as title. May contain HTML.
	 * 
	 * HTML and whitespace and non-ASCII characters will be stripped to create an URL for the analysis page.
	 * 
	 * Example: {@code <blink>My Analysis™</blink>} will be converted to `my-analysis` made available at an URL like
	 * this one: `localhost:8080/lemo/analysis/my-analysis`.
	 */
	String getName();

	/**
	 * Unique path of the analysis web API.
	 */
	String getPath();

	/**
	 * A list of URLs that will included as style sheets in the analysis page.
	 */
	List<String> getScriptPaths();

	Map<String, String> getProperties();

}