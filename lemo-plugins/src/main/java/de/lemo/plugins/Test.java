package de.lemo.plugins;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import de.lemo.persistence.entities.Foo;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Test {

	static final Logger logger = LoggerFactory.getLogger(Test.class);

	private EntityManagerFactory emf;
 
	
	@Reference
	void setEntityManagerF(EntityManagerFactory emf) { 
		logger.info("add em " + em); 
		this.emf = emf; 
	} 

	void unsetEntityManagerF(EntityManagerFactory emf) {
		this.emf = emf;
	}
	 
//	@Reference(target = "(osgi.unit.name=test2)") 
	
	void setEntityManager(EntityManager entityManager) { 
		logger.info("add em " + em); 
		this.em = entityManager; 
	} 

	void unsetEntityManager(EntityManager entityManager) {
		this.em = null;
	}
  
	private EntityManager em;  
  
	@Activate
	void activate() {  
 
		EntityManager em = emf.createEntityManager();
		logger.info("EntityManager!!!" ); 
 		 
		TypedQuery<Foo> query = em.createQuery("SELECT c FROM Foo c", Foo.class);
		List<Foo> results = query.getResultList();
		logger.warn("results " + results.size()); 
		for (Foo foo : results) { 
			logger.warn("FOO!!" + foo.getText());
		}
 
		
		// logger.warn("find " + find);

//		 Foo foo = new Foo();
//		 foo.setText("shshgh34");
//		 em.getTransaction().begin();
//		 em.persist(foo);
//		 em.getTransaction().commit();

	}

}
