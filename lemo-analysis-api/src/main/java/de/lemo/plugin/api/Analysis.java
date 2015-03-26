package de.lemo.plugin.api;

public interface Analysis {

	/**
	 * The name or title of this analysis. Will be used as title. May contain HTML.
	 * 
	 * HTML and whitespace and non-ASCII characters will be stripped to create an URL for the analysis page.
	 * 
	 * Example: {@code <blink>My Analysis™</blink>} will be converted to `my-analysis` made available at an URL like
	 * this one: `localhost:8080/lemo/analysis/my-analysis`.
	 */
	String NAME = "name";

	/**
	 * A list of URLs that will included as style sheets in the analysis page.
	 */
	String SCRIPT_PATH = "name";

	/**
	 * A short description of the main analysis.
	 */
	String DESCRIPTION_SHORT = "description-short";

	/**
	 * A longer description of the main analysis.
	 */
	String DESCRIPTION_LONG = "description-long";

	String IMAGE_PREVIEW = "image-preview";

	String IMAGE_ICON_COLOR = "image-icon-color";

	String IMAGE_ICON_MONOCHROME = "image-icon-monochrome";

}