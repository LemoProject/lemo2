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
import org.apache.felix.ipojo.annotations.ServiceProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lemo.persistence.umed.entities.Config;
import de.lemo.rest.api.WebResource;
import de.lemo.tools.api.AnalyticsTool;

@Component
@Provides
@Instantiate
@Path("tools/treemap")
public class TreemapAnalysis implements WebResource, AnalyticsTool{

	private static final Logger logger = LoggerFactory.getLogger(TreemapAnalysis.class);

	@ServiceProperty(name = "lemo.tool.id")
	private String id = "treemap";

	@ServiceProperty(name = "lemo.tool.name")
	private String name = "Tree Map";
	
	
	@ServiceProperty(name = "lemo.tool.description.short")
	private String descriptionShort = "Ladida.";

	@ServiceProperty(name = "lemo.tool.description.long")
	private String descriptionLong = "LLLLAAAADDDDIIIIDDDDAAAA.";
	
	@ServiceProperty(name = "lemo.tool.image.icon.monochrome")
	private String iconMonochrome = "img/icon-monochrome.svg";

	@ServiceProperty(name = "lemo.tool.image.icon.color")
	private String iconColor = "img/icon-color.svg";

	@ServiceProperty(name = "lemo.tool.image.preview")
	private String imagePreview = "img/preview.png";
	
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
