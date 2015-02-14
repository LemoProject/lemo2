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
		int slash = name.indexOf("/");
		if (slash < 0) {
			logger.warn("Invalid resource name: {} ", name);
			return null;
		}
		String bundle = name.substring(0, slash);
		String resourceName = name.substring(slash);
		String internalPath = basePath + resourceName;
		logger.debug("Requested resource {} for bundle {} at {}", resourceName, bundle, internalPath);

		return context.getBundle().getEntry(internalPath);
	}

	@Override
	public String getMimeType(String name) {
		return null;
	}

}
