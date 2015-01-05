package de.lemo.dms;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DmsActivator implements BundleActivator {

	static final Logger logger = LoggerFactory.getLogger(DmsActivator.class);
	private ServiceRegistration<?> service1;

	// private ServiceRegistration<?> service2;

	/** {@inheritDoc} */  
	public void start(BundleContext context) throws Exception {
		// Application restApplication = new AnnotatedRestApplication();
		// Dictionary props = new Hashtable();
		//
		// service1 = context.registerService(Application.class.getName(), restApplication, props);

		// Application restApplication2 = new CopyOfAnnotatedRestApplication2();
		// Dictionary props2 = new Hashtable();
		// props2.put("alias", "/rest2");
		// service2 = context.registerService(Application.class.getName(), restApplication2, props2);
 
	}
  
	@Override
	public void stop(BundleContext context) throws Exception {
		// service1.unregister();
		// service2.unregister();
	}
}
