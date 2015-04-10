package de.lemo.rest.adapter;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Resource2HttpContext implements HttpContext {

	private static final Logger logger = LoggerFactory.getLogger(Resource2HttpContext.class);

	private static final String ASSETS_PREFIX = "assets";

	private Map<String, Bundle> pluginPathMapping = new HashMap<>();
 

	public void reload(Map<String, Bundle> assetPathMapping) {
		this.pluginPathMapping = new HashMap<>(assetPathMapping);
	}

	@Override
	public boolean handleSecurity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return true;
	}

	@Override
	public URL getResource(String requestPath) {
 
		String assetPath = requestPath;//.substring(ASSETS_PREFIX.length());

		logger.info("Requested resource at {}", assetPath);

		for (Entry<String, Bundle> entry : pluginPathMapping.entrySet()) {
			String pluginPath = entry.getKey();
			logger.info("pluginPath {}", pluginPath);
			if (assetPath.startsWith(pluginPath)) {
				Bundle bundle = entry.getValue();
				String internalResourcePath = "/assets" + assetPath.substring(pluginPath.length());
				return bundle.getEntry(internalResourcePath);
			}
		}

		return null;
	}

	@Override
	public String getMimeType(String name) {
		return null;
	}

}
