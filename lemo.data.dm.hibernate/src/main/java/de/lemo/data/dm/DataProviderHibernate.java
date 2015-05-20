package de.lemo.data.dm;

import java.util.ArrayList;
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
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lemo.data.dm.entities.ED_LearningActivity;
import de.lemo.data.dm.entities.ED_LearningContext;
import de.lemo.data.dm.entities.ED_Path;
import de.lemo.data.dm.entities.ED_Person;
import de.lemo.persistence.umed.entities.LearningContext;
import de.lemo.persistence.umed.entities.LearningContextExt;
import de.lemo.persistence.umed.entities.PersonContext;
import de.lemo.persistence.umed.metamodels.LearningContext_;

@Component
@Provides
@Instantiate
public class DataProviderHibernate implements IDataProvider{
	
	@Requires
	private EntityManagerFactory emf;
	private static final Logger logger = LoggerFactory.getLogger(DataProviderHibernate.class);

	@SuppressWarnings("unchecked")
	@Override
	public ED_LearningContext getLearningContext(Long id) {
		
		List<Long> cids = new ArrayList<Long>();
		cids.add(1L);
		
		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(org.hibernate.Session.class);
		
		Criteria criteria = session.createCriteria(LearningContext.class, "context");
		criteria.add(Restrictions.eq("context.id", id));
		LearningContext context;
		
		if(criteria.list().size() > 0)
			context = (LearningContext) criteria.list().get(0);
		else
			return new ED_LearningContext();
		
		ED_LearningContext edContext = new ED_LearningContext();
		edContext.setId(context.getId());
		edContext.setName(context.getName());		
		if(context.getParent() != null)
			edContext.setParent(context.getParent().getId());
		
		criteria = session.createCriteria(LearningContextExt.class, "contextExt");
		criteria.add(Restrictions.eq("contextExt.learningContext.id", id));
		
		for(LearningContextExt lce : (List<LearningContextExt>)criteria.list())
		{
			if(lce.getAttr().equals("CourseLastRequest"))
				edContext.setLastAccess(Long.valueOf(lce.getValue()));
			else if(lce.getAttr().equals("CourseFirstRequest"))
				edContext.setFirstAccess(Long.valueOf(lce.getValue()));
		}
		
		criteria = session.createCriteria(PersonContext.class, "personContext");
		criteria.add(Restrictions.eq("personContext.learningContext.id", id));
		
		long count = 0;
		for(PersonContext pc : (List<PersonContext>)criteria.list())
		{
			if(pc.getRole().equals("student"))
				count++;
		}
		
		edContext.setUserCount(count);

		return edContext;
	}

	@Override
	public List<ED_LearningContext> getLearningContexts(List<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ED_LearningContext> getLearningContextsPerson(Long person) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ED_Person> getPersons(Long context, String role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ED_LearningActivity> getLearningActivities(Long person, Long startTime, Long endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ED_LearningActivity> getLearningActivities(Long context,
			String action, Long startTime, Long endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ED_LearningActivity> getLearningActivities(Long context,
			Long person, List<Long> objects, Long startTime, Long endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ED_Path> getPaths(Long context, Long startTime, Long endTime) {
		// TODO Auto-generated method stub
		return null;
	}

}
