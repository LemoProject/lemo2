package de.lemo.dataprovider.hibernate;

import java.util.ArrayList;
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
import de.lemo.dataprovider.api.LA_Object;
import de.lemo.dataprovider.api.LA_Person;
import de.lemo.persistence.d4la.entities.Context;
import de.lemo.persistence.d4la.entities.Person;
import de.lemo.persistence.d4la.entities.PersonContext;




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
			Criteria criteria = session.createCriteria(Context.class, "context");
			criteria.add(Restrictions.isNull("context.parent"));
			
			if(criteria.list().size() > 0)
				this.contexts = new ArrayList<LA_Context>(criteria.list());
			else
			{
				session.clear();
				session.close();
				return new ArrayList<LA_Context>();
			}
			session.clear();
			session.close();
		}
		return this.contexts;
	}


	public List<LA_Context> getCourses(LA_Person person) {
		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(org.hibernate.Session.class);
		
		List<LA_Context> contexts = new ArrayList<LA_Context>();
		Criteria criteria = session.createCriteria(Person.class, "person");
		criteria.add(Restrictions.eq("person.name", person.getName()));
		if(criteria.list().size() == 0)
			return contexts;
		else
		{
			for(PersonContext pc : ((Person)criteria.list().get(0)).getPersonContexts())
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
		
		Criteria criteria = session.createCriteria(Person.class, "person");
		criteria.add(Restrictions.eq("person.name", login));
		
		if(criteria.list().size() == 0)
		{
			session.clear();
			session.close();
			return new Person();
		}
		else
		{		
			person = (LA_Person)criteria.list().get(0);
			session.clear();
			session.close();
		}		
		return person;
	}


	@Override
	public List<LA_Context> getCoursesByInstructor(String userId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public LA_Context getContext(String descriptor) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public LA_Object getObject(String descriptor) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean testConnection() {
		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(org.hibernate.Session.class);
		Criteria criteria = session.createCriteria(Person.class, "person");
		if(criteria.list().size() == 0){
			return false;			
		} else {
			return true;
		}
	}

}
