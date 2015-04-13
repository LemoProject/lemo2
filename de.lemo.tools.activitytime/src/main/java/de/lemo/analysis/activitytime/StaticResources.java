package de.lemo.analysis.activitytime;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.ops4j.pax.web.extender.whiteboard.ResourceMapping;

@Component
@Provides
@Instantiate
public class StaticResources implements ResourceMapping {

	@Override
	public String getAlias() {
		return "/tools/activity-time/assets";
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
