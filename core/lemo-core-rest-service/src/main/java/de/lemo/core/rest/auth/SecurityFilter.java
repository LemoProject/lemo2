package de.lemo.core.rest.auth;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		HmacSecurityContext securityContext = new HmacSecurityContext(null, null, false);

		requestContext.setSecurityContext(securityContext);

//		logger.info("############## FILTER");
	}

}
