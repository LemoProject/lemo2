package de.lemo.analysis.circlegraph;

import org.ops4j.pax.web.extender.whiteboard.ResourceMapping;

//@Component
//@Provides
//@Instantiate
public class StaticResources implements ResourceMapping {

	@Override
	public String getAlias() {
		return "/lemo/tools/circlegraph/assets";
	}

	@Override
	public String getPath() {
		return "/assets";
	}

	@Override
	public String getHttpContextId() {
		return null;
	}
}
