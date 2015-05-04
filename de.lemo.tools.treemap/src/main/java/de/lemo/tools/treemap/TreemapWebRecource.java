package de.lemo.tools.treemap;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lemo.persistence.lemo.entities.Config;
import de.lemo.rest.api.WebResource;

@Component
@Provides
@Instantiate
@Path("tools/treemap")
public class TreemapWebRecource implements WebResource{

	private static final Logger logger = LoggerFactory.getLogger(TreemapWebRecource.class);

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

