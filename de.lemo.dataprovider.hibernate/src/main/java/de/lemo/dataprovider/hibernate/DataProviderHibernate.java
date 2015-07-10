package de.lemo.dataprovider.hibernate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dataprovider.api.DataProvider;
import de.lemo.dataprovider.api.LA_Context;
import de.lemo.dataprovider.api.LA_Person;




@Component
@Provides
@Instantiate
public class DataProviderHibernate implements DataProvider{
	
	@Requires
	private EntityManagerFactory emf;
	private List<LA_Context> contexts = null; 
	


	@SuppressWarnings("unchecked")
	@Override
	public List<LA_Context> getCourses() {
		
		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(org.hibernate.Session.class);
		
		if(this.contexts == null)
		{
			Criteria criteria = session.createCriteria(IContext.class, "context");
			criteria.add(Restrictions.isNull("context.parent"));
			
			if(criteria.list().size() > 0)
				this.contexts = new HashSet<LA_Context>(criteria.list());
			else
			{
				session.clear();
				session.close();
				return new HashSet<LA_Context>();
			}
			session.clear();
			session.close();
		}
		return this.contexts;
	}

	@Override
	public Set<LA_Context> getCourses(LA_Person person) {
		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(org.hibernate.Session.class);
		
		Set<LA_Context> contexts = new HashSet<LA_Context>();
		Criteria criteria = session.createCriteria(IPerson.class, "person");
		criteria.add(Restrictions.eq("person.name", person.getName()));
		if(criteria.list().size() == 0)
			return contexts;
		else
		{
			for(PersonContext pc : ((IPerson)criteria.list().get(0)).getPersonContexts())
			{
				contexts.add((LA_Context) pc.getLearningContext());
			}
			
		}		
		return contexts;
		
	}

	@Override
	public LA_Person getPerson(String login) {
		LA_Person person = null;
		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(org.hibernate.Session.class);
		
		Criteria criteria = session.createCriteria(IPerson.class, "person");
		criteria.add(Restrictions.eq("person.name", login));
		
		if(criteria.list().size() == 0)
		{
			session.clear();
			session.close();
			return new EDI_Person();
		}
		else
		{		
			person = (LA_Person)criteria.list().get(0);
			session.clear();
			session.close();
		}		
		return person;
	}

}
