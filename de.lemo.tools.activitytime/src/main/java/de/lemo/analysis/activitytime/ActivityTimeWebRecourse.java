package de.lemo.analysis.activitytime;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;

import de.lemo.dp.DataProvider;
import de.lemo.dp.ED_Context;
import de.lemo.rest.api.WebResource;


@Component
@Provides
@Instantiate
@Path("tools/activitytime")
public class ActivityTimeWebRecourse implements WebResource{
	
@Requires
DataProvider dataProvider;

@GET
public String getResult(){
	Set<ED_Context> courses = dataProvider.getCourses();
	ED_Context myContext = null;
	for(ED_Context context : courses){
		myContext = context;
	}
	return myContext.getName();
}
}
