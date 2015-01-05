package de.lemo.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.internal.util.collection.Value;
import org.glassfish.jersey.server.mvc.Viewable;
import org.glassfish.jersey.server.mvc.spi.AbstractTemplateProcessor;
import org.jvnet.hk2.annotations.Optional;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheException;
import com.github.mustachejava.MustacheFactory;

/*
 *  TODO own module?
 */

@Provider
public class OsgiMustacheTemplateProcessor extends AbstractTemplateProcessor<Mustache> {

	private MustacheFactory mustacheFactory;
	private Configuration config;

	/**
	 * Create an instance of this processor with injected {@link Configuration config} and (optional)
	 * {@link ServletContext servlet context}.
	 *
	 * @param config
	 *            configuration to configure this processor from.
	 * @param serviceLocator
	 *            service locator to initialize template object factory if needed.
	 * @param servletContext
	 *            (optional) servlet context to obtain template resources from.
	 */
	@Inject
	public OsgiMustacheTemplateProcessor(final Configuration config, final ServiceLocator serviceLocator, @Optional final ServletContext servletContext) {
		super(config, servletContext, "mustache", "mustache");
		this.config = config;

		this.mustacheFactory = getTemplateObjectFactory(serviceLocator, MustacheFactory.class, new Value<MustacheFactory>() {
			@Override
			public MustacheFactory get() {
				return new DefaultMustacheFactory();
			}
		});
	}

	@Override
	protected Mustache resolve(String templatePath, Reader reader) {
		return mustacheFactory.compile(reader, templatePath);
	}

	@Override
	public void writeTo(Mustache mustache, Viewable viewable, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream out)
			throws IOException {
		Charset encoding = setContentType(mediaType, httpHeaders);
		mustache.execute(new OutputStreamWriter(out, encoding), viewable.getModel()).flush();
	}
 
	@Override
	public Mustache resolve(String name, MediaType mediaType) {
		InputStreamReader reader;
		URL resource;
		try {
			Bundle applicationBundle = getApplicationBundle(config);
			resource = applicationBundle.getResource(name);
			reader = new InputStreamReader(resource.openStream());
		} catch (Exception e) {
			throw new MustacheException("Failed to load template resource.", e);
		}
		return resolve(resource.toExternalForm(), reader);
	}

	private Bundle getApplicationBundle(Configuration config) {
		Object bundleId = config.getProperty(AdapterConstants.WEBAPP_BUNDLE_ID);
		if (bundleId == null) {
			throw new MustacheException("Missing parameter: " + AdapterConstants.WEBAPP_BUNDLE_ID);
		}
		return FrameworkUtil.getBundle(getClass()).getBundleContext().getBundle((long) bundleId);
	}
}