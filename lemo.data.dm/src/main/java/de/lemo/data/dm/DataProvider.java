package de.lemo.data.dm;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;

import org.apache.felix.ipojo.annotations.Requires;

import de.lemo.persistence.umed.entities.Config;

public class DataProvider {
	
	@Requires
	private EntityManagerFactory emf;
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@GET
	public String getString()
	{
		
		EntityManager em = emf.createEntityManager();
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery(Config.class);
		Root<Config> r = cq.from(Config.class);
		cq.select(r);
		TypedQuery<Config> tq = em.createQuery(cq);
		List<Config> list = tq.getResultList();
		
		
		return list.get(0).getDatabaseModel();
	}

}
