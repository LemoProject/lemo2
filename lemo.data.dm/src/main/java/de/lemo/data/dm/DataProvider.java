package de.lemo.data.dm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lemo.persistence.umed.entities.LearningContext;
import de.lemo.persistence.umed.metamodels.LearningContext_;

@Component
@Provides
@Instantiate
public class DataProvider {
	
	@Requires
	private EntityManagerFactory emf;
	private static final int BATCH_SIZE = 50;
	private static final Logger logger = LoggerFactory.getLogger(DataProvider.class);
	
	public String getString()
	{
		
		List<Long> cids = new ArrayList<Long>();
		cids.add(1L);
		
		EntityManager em = emf.createEntityManager();
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery(LearningContext.class);
		Root<LearningContext> r = cq.from(LearningContext.class);
		cq.where(r.get(LearningContext_.id).in(cids));
		cq.select(r);
		TypedQuery<LearningContext> tq = em.createQuery(cq);
		List<LearningContext> list = tq.getResultList();
		
		
		return list.get(0).getName();
	}
	
	public void persist(List<?> data)
	{
		EntityManager em = emf.createEntityManager();
		String className = "";
		int classOb = 0;
		em.getTransaction().begin();
		int i = 0;
		for (Object obj:data) 
		{
			if (!className.equals("") && !className.equals(obj.getClass().getName()))
			{
				logger.info("Wrote " + classOb + " objects of class " + className + " to database.");
				classOb = 0;
			}
			className = obj.getClass().getName();
			i++;
			classOb++;
			em.persist(obj);
			if ((i % BATCH_SIZE) == 0) 
			{
				em.flush();
				em.clear();
			}
		}
		em.getTransaction().commit();
		logger.info("Wrote " + classOb + " objects of class " + className + " to database.");
	}
	
	public void persistCollection(final List<Collection<?>> data)
	{
		EntityManager em = emf.createEntityManager();
		String className = "";
		int classOb = 0;
		em.getTransaction().begin();
		int i = 0;
		for (int j = 0; j < data.size(); j++) 
		{
			for (final Object obj:data.get(j)) 
			{
				if (!className.equals("") && !className.equals(obj.getClass().getName()))
				{
					logger.info("Wrote " + classOb + " objects of class " + className + " to database.");
					classOb = 0;
				}
				className = obj.getClass().getName();
				i++;
				classOb++;
				em.persist(obj);
				if ((i % BATCH_SIZE) == 0) 
				{
					em.flush();
					em.clear();
				}
			}
		}
		em.getTransaction().commit();
		logger.info("Wrote " + classOb + " objects of class " + className + " to database.");
	}

}
