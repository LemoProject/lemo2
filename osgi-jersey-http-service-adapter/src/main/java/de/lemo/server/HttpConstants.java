package de.lemo.server;

public interface HttpConstants {

	/*
	 * @see org.apache.felix.http.whiteboard.HttpWhiteboardConstants
	 */

	public static final String CONTEXT_ID = "contextId";
	public static final String CONTEXT_SHARED = "context.shared";
	public static final String ALIAS = "alias";
	public static final String PATTERN = "pattern";
	public static final String INIT_PREFIX = "init.";

	/*
	 * @see org.ops4j.pax.web.service.WebContainerConstants
	 */

	public static final String SERVLET_NAME = "servlet-name";
	public static final String FILTER_NAME = "filter-name";
	public static final String BUNDLE_CONTEXT_ATTRIBUTE = "osgi-bundlecontext";

}
