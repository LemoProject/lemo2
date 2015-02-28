package de.lemo.server.auth;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HmacSecurityContext implements SecurityContext {

	private static final Logger logger = LoggerFactory.getLogger(HmacSecurityContext.class);
	
	private Principal principal;
	private String digest;

	private boolean isSecure;

	public HmacSecurityContext(Principal principal, String digest, boolean secure) {
		this.principal = principal;
		this.digest = digest;
		this.isSecure = secure;
	}

	@Override
	public Principal getUserPrincipal() {
		return principal;
	}

	@Override
	public boolean isUserInRole(String role) {

		logger.info("############## isUserInRole " + role);
		return false;
	}

	@Override
	public boolean isSecure() {
		return isSecure;
	}

	@Override
	public String getAuthenticationScheme() {
		return SecurityContext.DIGEST_AUTH;
	}

}
