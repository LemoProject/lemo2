package de.lemo.analysis.activitylearningobject;

import javax.ws.rs.Path;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import de.lemo.rest.api.WebResource;

@Component
@Provides
@Instantiate
@Path("tools/activitylearningobject")
public class ActivityLearningObjectWebRecource implements WebResource {

}
