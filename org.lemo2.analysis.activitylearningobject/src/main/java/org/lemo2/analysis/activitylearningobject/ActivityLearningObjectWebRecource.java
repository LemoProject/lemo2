package org.lemo2.analysis.activitylearningobject;

import javax.ws.rs.Path;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import org.lemo2.analysis.api.WebResource;

@Component
@Provides
@Instantiate
@Path("tools/activitylearningobject")
public class ActivityLearningObjectWebRecource implements WebResource {

}
