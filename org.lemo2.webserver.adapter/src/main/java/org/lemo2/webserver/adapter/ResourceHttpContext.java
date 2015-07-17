package org.lemo2.webserver.adapter;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ResourceHttpContext implements HttpContext {

	private static final Logger logger = LoggerFactory.getLogger(RestResourceAdapter.class);

	private BundleContext context;

	public ResourceHttpContext(BundleContext context) {
		this.context = context;
	}

	@Override
	public boolean handleSecurity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return true;
	}

	@Override
	public URL getResource(String name) {
		String resource = "/assets" + name.substring(name.indexOf('/'));
		URL resourceUrl = context.getBundle().getResource(resource);
		if (resourceUrl == null) {
			logger.warn("Failed to load resource {}{}", context.getBundle().getSymbolicName(), resource);
		} else {
			logger.info("Loaded resource {}{}", context.getBundle().getSymbolicName(), resource);
		}

		return resourceUrl;
	}

	@Override
	public String getMimeType(String name) {
		return null;
	}

}