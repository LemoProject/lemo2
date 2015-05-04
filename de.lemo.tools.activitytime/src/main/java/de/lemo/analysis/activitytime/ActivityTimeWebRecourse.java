package de.lemo.analysis.activitytime;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import de.lemo.rest.api.WebResource;


@Component
@Provides
@Instantiate
@Path("tools/activitytime")
public class ActivityTimeWebRecourse implements WebResource{

@GET
public String getResult(){
	return "Test";
}
}
