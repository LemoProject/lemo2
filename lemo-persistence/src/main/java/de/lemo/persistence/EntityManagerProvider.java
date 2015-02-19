package de.lemo.persistence;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.sql.DataSource;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate=true)
public class EntityManagerProvider {

	private static final Logger logger = LoggerFactory.getLogger(EntityManagerProvider.class);

	@Reference 
	private PersistenceProvider persistenceProvider;

	@Reference  
	 private DataSourceFactory dsf;
	
//	private ServiceRegistration<EntityManager> serviceRegistration;

	private String unitName = "mining";
 
	@Activate  
	private void activate() {  
    BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
//		logger.info("ACTIVATE " + persistenceProvider);
//   
//		 Properties props = new Properties();
//		 props.put(DataSourceFactory.JDBC_URL, "jdbc:derby:memory:library;create=true");
//		 DataSource dataSource = dsf.createDataSource(props);
//		 bundleContext.registerService(DataSource.class, dataSource, null);
//		
//		
//		EntityManagerFactory entityManagerFactory = persistenceProvider.createEntityManagerFactory(unitName, null);
//	 
//		
// 
//		EntityManager entityManager = entityManagerFactory.createEntityManager();
//
//		Dictionary<String, String> props = new Hashtable<>();
//		props.put("osgi.unit.name", unitName);
//		props.put("osgi.unit.provider", persistenceProvider.getClass().getName());
//  
// 		serviceRegistration = bundleContext.registerService(EntityManager.class, entityManager, props);
// 
	}   

	@Deactivate
	private void deactivate() {
//		if (serviceRegistration != null) {
//			serviceRegistration.unregister();
//			serviceRegistration = null;
//		}
	}

}
