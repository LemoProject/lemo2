package org.lemo2.analysis.activitygraph2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.lemo2.rest.api.WebResource;

@Component
@Provides
@Instantiate
@Path("tools/activitygraph2")
public class ActivityGraph2WebResource implements WebResource{

	private static final Logger logger = LoggerFactory.getLogger(ActivityGraph2WebResource.class);
	
	@GET
	public 	String getResult(@Context SecurityContext context){
		
		return "Test";
	}
	
	
}
