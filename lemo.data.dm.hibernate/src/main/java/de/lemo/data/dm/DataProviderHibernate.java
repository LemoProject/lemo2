package de.lemo.data.dm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
import de.lemo.persistence.umed.entities.LearningActivity;
import de.lemo.persistence.umed.entities.LearningActivityExt;
import de.lemo.persistence.umed.entities.LearningContext;
import de.lemo.persistence.umed.entities.LearningContextExt;
import de.lemo.persistence.umed.entities.ObjectContext;
import de.lemo.persistence.umed.entities.Person;
import de.lemo.persistence.umed.entities.PersonContext;
import de.lemo.persistence.umed.entities.PersonExt;

/**
 * Implements the de.lemo.dm.IDataProvider interface using org.hibernate
 * 
 * @author sschwarzrock
 *
 */
@Component
@Provides
@Instantiate
public class DataProviderHibernate implements IDataProvider{
	
	@Requires
	private EntityManagerFactory emf;
	private static final Logger logger = LoggerFactory.getLogger(DataProviderHibernate.class);

	@SuppressWarnings("unchecked")
	public ED_LearningContext getLearningContext(Long id) {
		
		logger.info("Starting getLearningContext for LearningContextId " + id);
		
		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(org.hibernate.Session.class);
		
		Criteria criteria = session.createCriteria(LearningContext.class, "context");
		criteria.add(Restrictions.eq("context.id", id));
		LearningContext context;
		
		if(criteria.list().size() > 0)
			context = (LearningContext) criteria.list().get(0);
		else
		{
			session.clear();
			session.close();
			return new ED_LearningContext();
		}
		
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
			else
				edContext.addExtension(lce.getAttr(), lce.getValue());
		}
		
		criteria = session.createCriteria(PersonContext.class, "personContext");
		criteria.add(Restrictions.eq("personContext.learningContext.id", id));
		
		for(PersonContext pc : (List<PersonContext>)criteria.list())
		{
			if(pc.getRole().equals("student"))
				edContext.increaseUserCount();
		}
		
		session.clear();
		session.close();

		return edContext;
	}

	@SuppressWarnings("unchecked")
	public List<ED_LearningContext> getLearningContexts(List<Long> ids) {
		
		
		logger.info("Starting getLearningContext for LearningContextIds ");
		
		Map<Long, ED_LearningContext> edContexts = new HashMap<Long, ED_LearningContext>();
		
		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(org.hibernate.Session.class);
		
		Criteria criteria = session.createCriteria(LearningContext.class, "context");
		criteria.add(Restrictions.in("context.id", ids));
		
		for(LearningContext context : (List<LearningContext>)criteria.list())
		{		
			ED_LearningContext edContext = new ED_LearningContext();
			edContext.setId(context.getId());
			edContext.setName(context.getName());		
			if(context.getParent() != null)
				edContext.setParent(context.getParent().getId());
			edContext.setUserCount(0L);
			edContexts.put(edContext.getId(), edContext);
		}
			
		criteria = session.createCriteria(LearningContextExt.class, "contextExt");
		criteria.add(Restrictions.in("contextExt.learningContext.id", ids));
		
		for(LearningContextExt lce : (List<LearningContextExt>)criteria.list())
		{
			if(edContexts.get(lce.getLearningContext().getId()) != null)
			{
				if(lce.getAttr().equals("CourseLastRequest"))
					edContexts.get(lce.getLearningContext().getId()).setLastAccess(Long.valueOf(lce.getValue()));
				else if(lce.getAttr().equals("CourseFirstRequest"))
					edContexts.get(lce.getLearningContext().getId()).setFirstAccess(Long.valueOf(lce.getValue()));
				else
					edContexts.get(lce.getLearningContext().getId()).addExtension(lce.getAttr(), lce.getValue());
			}
		}
		
		criteria = session.createCriteria(PersonContext.class, "personContext");
		criteria.add(Restrictions.in("personContext.learningContext.id", ids));
		
		for(PersonContext pc : (List<PersonContext>)criteria.list())
		{
			if(pc.getRole().equals("student") && edContexts.get(pc.getLearningContext().getId()) != null)
				edContexts.get(pc.getLearningContext().getId()).increaseUserCount();
		}
		
		criteria = session.createCriteria(ObjectContext.class, "objectContext");
		criteria.add(Restrictions.in("objectContext.learningContext.id", ids));
		
		for(ObjectContext oc : (List<ObjectContext>) criteria.list())
		{
			if(edContexts.get(oc.getLearningContext().getId()) != null)
				edContexts.get(oc.getLearningContext().getId()).addLearningobject(oc.getLearningObject().getId());
		}

		session.clear();
		session.close();
		
		return new ArrayList<ED_LearningContext>(edContexts.values());
	}

	@SuppressWarnings("unchecked")
	public List<ED_LearningContext> getLearningContextsPerson(Long person, String role) {
		
		logger.info("Starting getLearningContext for Person " + person);
		
		Map<Long, ED_LearningContext> edContexts = new HashMap<Long, ED_LearningContext>();
		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(org.hibernate.Session.class);
		
		List<Long> ids = new ArrayList<Long>();
		
		Criteria criteria = session.createCriteria(PersonContext.class, "personContext");
		criteria.add(Restrictions.eq("personContext.person.id", person));
		criteria.add(Restrictions.like("personContext.role", role));
		
		for(PersonContext pc : (List<PersonContext>) criteria.list())
		{
			ids.add(pc.getLearningContext().getId());
		}
		
		if(ids.isEmpty())
		{
			session.clear();
			session.close();
			return new ArrayList<ED_LearningContext>();
		}
			
		criteria = session.createCriteria(LearningContext.class, "context");
		criteria.add(Restrictions.in("context.id", ids));
		
		for(LearningContext context : (List<LearningContext>)criteria.list())
		{		
			ED_LearningContext edContext = new ED_LearningContext();
			edContext.setId(context.getId());
			edContext.setName(context.getName());		
			if(context.getParent() != null)
				edContext.setParent(context.getParent().getId());
			edContext.setUserCount(0L);
			edContexts.put(edContext.getId(), edContext);
		}
			
		criteria = session.createCriteria(LearningContextExt.class, "contextExt");
		criteria.add(Restrictions.in("contextExt.learningContext.id", ids));
		
		for(LearningContextExt lce : (List<LearningContextExt>)criteria.list())
		{
			if(edContexts.get(lce.getLearningContext().getId()) != null)
			{
				if(lce.getAttr().equals("CourseLastRequest"))
					edContexts.get(lce.getLearningContext().getId()).setLastAccess(Long.valueOf(lce.getValue()));
				else if(lce.getAttr().equals("CourseFirstRequest"))
					edContexts.get(lce.getLearningContext().getId()).setFirstAccess(Long.valueOf(lce.getValue()));
				else
					edContexts.get(lce.getLearningContext().getId()).addExtension(lce.getAttr(), lce.getValue());
			}
		}
		
		criteria = session.createCriteria(PersonContext.class, "personContext");
		criteria.add(Restrictions.in("personContext.learningContext.id", ids));
		
		for(PersonContext pc : (List<PersonContext>)criteria.list())
		{
			if(pc.getRole().equals("student") && edContexts.get(pc.getLearningContext().getId()) != null)
				edContexts.get(pc.getLearningContext().getId()).increaseUserCount();
		}
		
		criteria = session.createCriteria(ObjectContext.class, "objectContext");
		criteria.add(Restrictions.in("objectContext.learningContext.id", ids));
		
		for(ObjectContext oc : (List<ObjectContext>) criteria.list())
		{
			if(edContexts.get(oc.getLearningContext().getId()) != null)
				edContexts.get(oc.getLearningContext().getId()).addLearningobject(oc.getLearningObject().getId());
		}
		
		session.clear();
		session.close();
		
		return new ArrayList<ED_LearningContext>(edContexts.values());
	}

	@SuppressWarnings("unchecked")
	public List<ED_Person> getPersons(Long context, String role) {
		
		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(org.hibernate.Session.class);
		
		Map<Long, ED_Person> persons = new HashMap<Long, ED_Person>();
		
		List<Long> ids = new ArrayList<Long>();
		
		Criteria criteria = session.createCriteria(PersonContext.class, "personContext");
		if(context != null)
			criteria.add(Restrictions.eq("personContext.learningContext.id", context));
		if(role != null)
			criteria.add(Restrictions.like("personContext.role", role));
		
		for(PersonContext pc : (List<PersonContext>) criteria.list())
		{
			ED_Person edPerson = new ED_Person();
			edPerson.setId(pc.getPerson().getId());
			edPerson.addContextRole(pc.getLearningContext().getId(), pc.getRole());
			ids.add(edPerson.getId());
			persons.put(edPerson.getId(), edPerson);
		}
		
		criteria = session.createCriteria(Person.class, "person");
		criteria.add(Restrictions.in("person.id", ids));
		
		for(Person p : (List<Person>) criteria.list())
		{
			if(persons.get(p.getId()) != null)
			{
				persons.get(p.getId()).setName(p.getName());
			}
		}
		
		criteria = session.createCriteria(PersonExt.class, "personExt");
		criteria.add(Restrictions.in("personExt.person.id", ids));
		
		for(PersonExt pExt : (List<PersonExt>) criteria.list())
		{
			if(persons.get(pExt.getPerson().getId()) != null)
			{
				persons.get(pExt.getPerson().getId()).addExtension(pExt.getAttribute(), pExt.getValue());
			}
		}
		
		session.clear();
		session.close();
		
		
		return new ArrayList<ED_Person>(persons.values());
	}

	@SuppressWarnings("unchecked")
	public List<ED_LearningActivity> getLearningActivities(Long person, Long startTime, Long endTime) {
		
		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(org.hibernate.Session.class);
		
		Map<Long, ED_LearningActivity> activities = new HashMap<Long, ED_LearningActivity>();
		
		Criteria criteria = session.createCriteria(LearningActivity.class, "activity");
		
		if(person != null)
			criteria.add(Restrictions.eq("activity.person.id", person));
		if(startTime != null)
			criteria.add(Restrictions.ge("activity.time", startTime));
		if(endTime != null)
			criteria.add(Restrictions.le("activity.time", endTime));
		
		for(LearningActivity la : (List<LearningActivity>) criteria.list())
		{
			ED_LearningActivity act = new ED_LearningActivity();
			act.setId(la.getId());
			act.setPerson(la.getPerson().getId());
			act.setLearningContext(la.getLearningContext().getId());
			act.setLearningObject(la.getLearningObject().getId());
			act.setTime(la.getTime());
			act.setInfo(la.getInfo());
			act.setReference(la.getReference().getId());
			act.setAction(la.getAction());
			
			activities.put(act.getId(), act);
		}
		
		criteria = session.createCriteria(LearningActivityExt.class, "activityExt");
		criteria.add(Restrictions.in("activityExt.learningActivity.id", activities.keySet()));
		
		for(LearningActivityExt laExt : (List<LearningActivityExt>) criteria.list())
		{
			activities.get(laExt.getLearningActivity().getId()).addExtension(laExt.getAttr(), laExt.getValue());
		}
		
		session.clear();
		session.close();
		
		
		return new ArrayList<ED_LearningActivity>(activities.values());
	}

	@SuppressWarnings("unchecked")
	public List<ED_LearningActivity> getLearningActivities(Long context,
			String action, Long startTime, Long endTime) {
		
		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(org.hibernate.Session.class);
		
		Map<Long, ED_LearningActivity> activities = new HashMap<Long, ED_LearningActivity>();
		
		Criteria criteria = session.createCriteria(LearningActivity.class, "activity");
		
		if(context != null)
			criteria.add(Restrictions.eq("activity.learningContext.id", context));
		if(startTime != null)
			criteria.add(Restrictions.ge("activity.time", startTime));
		if(endTime != null)
			criteria.add(Restrictions.le("activity.time", endTime));
		if(action != null)
			criteria.add(Restrictions.like("activity.action", action));
		
		for(LearningActivity la : (List<LearningActivity>) criteria.list())
		{
			ED_LearningActivity act = new ED_LearningActivity();
			act.setId(la.getId());
			act.setPerson(la.getPerson().getId());
			act.setLearningContext(la.getLearningContext().getId());
			act.setLearningObject(la.getLearningObject().getId());
			act.setTime(la.getTime());
			act.setInfo(la.getInfo());
			act.setReference(la.getReference().getId());
			act.setAction(la.getAction());
			
			activities.put(act.getId(), act);
		}
		
		criteria = session.createCriteria(LearningActivityExt.class, "activityExt");
		criteria.add(Restrictions.in("activityExt.learningActivity.id", activities.keySet()));
		
		for(LearningActivityExt laExt : (List<LearningActivityExt>) criteria.list())
		{
			activities.get(laExt.getLearningActivity().getId()).addExtension(laExt.getAttr(), laExt.getValue());
		}
		
		session.clear();
		session.close();
		
		return new ArrayList<ED_LearningActivity>(activities.values());
	}

	@SuppressWarnings("unchecked")
	public List<ED_LearningActivity> getLearningActivities(Long context,
			Long person, List<Long> objects, Long startTime, Long endTime) {

		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(org.hibernate.Session.class);
		
		Map<Long, ED_LearningActivity> activities = new HashMap<Long, ED_LearningActivity>();
		
		Criteria criteria = session.createCriteria(LearningActivity.class, "activity");
		
		if(context != null)
			criteria.add(Restrictions.eq("activity.learningContext.id", context));
		if(startTime != null)
			criteria.add(Restrictions.ge("activity.time", startTime));
		if(endTime != null)
			criteria.add(Restrictions.le("activity.time", endTime));
		if(objects != null && !objects.isEmpty())
			criteria.add(Restrictions.in("activity.learningobject.id", objects));
		if(person != null)
			criteria.add(Restrictions.ge("activity.person.id", person));
		
		
		for(LearningActivity la : (List<LearningActivity>) criteria.list())
		{
			ED_LearningActivity act = new ED_LearningActivity();
			act.setId(la.getId());
			act.setPerson(la.getPerson().getId());
			act.setLearningContext(la.getLearningContext().getId());
			act.setLearningObject(la.getLearningObject().getId());
			act.setTime(la.getTime());
			act.setInfo(la.getInfo());
			act.setReference(la.getReference().getId());
			act.setAction(la.getAction());
			
			activities.put(act.getId(), act);
		}
		
		criteria = session.createCriteria(LearningActivityExt.class, "activityExt");
		criteria.add(Restrictions.in("activityExt.learningActivity.id", activities.keySet()));
		
		for(LearningActivityExt laExt : (List<LearningActivityExt>) criteria.list())
		{
			activities.get(laExt.getLearningActivity().getId()).addExtension(laExt.getAttr(), laExt.getValue());
		}
		
		session.clear();
		session.close();		
		
		return new ArrayList<ED_LearningActivity>(activities.values());
	}


	public List<ED_Path> getPaths(Long context, Long startTime, Long endTime) {
		// TODO Auto-generated method stub
		return null;
	}

}
