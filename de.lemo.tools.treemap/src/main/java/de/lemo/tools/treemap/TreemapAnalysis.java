package de.lemo.tools.treemap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.ServiceProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lemo.persistence.lemo.entities.Config;
import de.lemo.persistence.lemo.entities.abstractions.ILog;
import de.lemo.rest.api.WebResource;

@Component
@Provides
@Instantiate
@Path("tools/treemap")
public class TreemapAnalysis implements WebResource{

	private static final Logger logger = LoggerFactory.getLogger(TreemapAnalysis.class);

	
	
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
	
	public List<?> compute(){

		

		final List<Long> courses;
		final Long startTime;
		final Long endTime;
		List<String> resourceTypes;
		List<Long> gender;
		List<Long> learningObjects;

		boolean allTypes = resourceTypes.isEmpty();
		EntityManager em = emf.createEntityManager();
		
		List<Long> users = new ArrayList<Long>(StudentHelper.getCourseStudentsAliasKeys(courses, gender).values());
		if(users.isEmpty()) {
			logger.warn("Could not find associated users.");
			return new ResultListResourceRequestInfo();
		}
		
		CriteriaBuilder cBuilder = em.getCriteriaBuilder();
		CriteriaQuery cq = cBuilder.createQuery(ILog.class);
		Root<ILog> r = cq.from(ILog.class);
		Expression<Long> expression = r.get("course");
		Predicate condition = expression.in(courses);
		
		ParameterExpression<List<Long>> p = cBuilder.parameter(List.class);
		
		cq.select(r);
		TypedQuery<Config> tq = em.createQuery(cq);
		List<Config> list = tq.getResultList();
		
		
		Criteria criteria = session.createCriteria(ILog.class, "log");
		criteria.add(Restrictions.in("log.course.id", courses))
				.add(Restrictions.between("log.timestamp", startTime, endTime))
				.add(Restrictions.in("log.user.id", users));
		
		if(!learningObjects.isEmpty())
		{
			criteria.add(Restrictions.in("log.learning.id", learningObjects));
		}

		final List<ILog> logs = criteria.list();		
		
		final Map<Long, ResourceRequestInfo> rriMap = new HashMap<Long, ResourceRequestInfo>();
		final Map<Long, Set<Long>> userMap = new HashMap<Long, Set<Long>>();
		for(ILog log : logs)
		{
			String type = log.getLearning().getLOType();
			if (log.getUser() != null && (resourceTypes.contains(type) || allTypes))
			{
				Long id = Long.valueOf(log.getLearningId());
				if (rriMap.get(id) == null) {
					Set<Long> userSet = new HashSet<Long>();
					userSet.add(log.getUser().getId());
					userMap.put(id, userSet);
					rriMap.put(id, new ResourceRequestInfo(id, type, 1L, 1L, log.getLearning().getTitle(), 0L));
				} else
				{
					userMap.get(id).add(log.getUser().getId());
					rriMap.get(id).incRequests();
					rriMap.get(id).setUsers(((Integer)userMap.get(id).size()).longValue());
				}
			}
		}
		
		//Add unused Objects
		criteria = session.createCriteria(ICourseLORelation.class, "aso");
		criteria.add(Restrictions.in("aso.course.id", courses));
		List<ICourseLORelation> asoList = criteria.list();
		
		for(ICourseLORelation aso : asoList)
		{
			Long id = Long.valueOf(aso.getLearning().getId());
			if(!rriMap.containsKey(id))
			{
				String type = aso.getLearning().getClass().getSimpleName().toUpperCase();
				if(type.contains("MINING"))
				{
					type = type.substring(0, type.indexOf("MINING"));
				}
				if(allTypes || resourceTypes.contains(type))
				{			
					final ResourceRequestInfo rri = new ResourceRequestInfo(id,
							aso.getLearning().getLOType(), 0L, 0L,
							aso.getLearning().getTitle(), 0L);
					result.add(rri);
					id++;
				}
			}
		}
		
		if (rriMap.values() != null) {
			result.addAll(rriMap.values());
		}
		session.close();
		return new ArrayList<Object>();
	}


	
}
