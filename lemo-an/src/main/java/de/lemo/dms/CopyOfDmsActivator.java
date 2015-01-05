package de.lemo.dms;

import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Component()
public class CopyOfDmsActivator {

	static final Logger logger = LoggerFactory.getLogger(CopyOfDmsActivator.class);

//	@Reference()
	void setApplication(Application application) {
		logger.info("APP bind");
	}

	void unsetApplication(Application application) { 
		logger.info("APP unbind");
	}

	private Application app;
 
//	@Activate
	void activate() {
		logger.info("APP TRACk ACTiVATe");
	}

}
