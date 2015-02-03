package de.lemo.server;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceHttpContext implements HttpContext {

	private static final Logger logger = LoggerFactory.getLogger(ResourceHttpContext.class);

	private BundleContext context;

	private String basePath;

	public ResourceHttpContext(BundleContext context, String basePath) {
		this.context = context;
		if (basePath.equals("/")) {
			this.basePath = "";
		} else {
			this.basePath = basePath;
		}
	}

	@Override
	public boolean handleSecurity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return true;
	}

	@Override
	public URL getResource(String name) {
		if (!name.contains("/")) {
			logger.info("RESOURCE ??? {}  ", name);
			return null;
		}
		String bundle = name.substring(0, name.indexOf("/"));
		String resourceName = name.substring(name.indexOf("/"));
		String internalPath = basePath + resourceName;
		logger.info("RESOURCE {} for bundle {} at {}", resourceName, bundle, internalPath);
		URL resource2 = context.getBundle().getEntry(internalPath);
		logger.warn("FOUND? {}", resourceName);
		return resource2;
	}

	@Override
	public String getMimeType(String name) {
		return null;
	}

}
