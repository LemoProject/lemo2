package de.lemo.data.dm;

import java.util.List;

import de.lemo.data.dm.entities.ED_LearningActivity;
import de.lemo.data.dm.entities.ED_LearningContext;
import de.lemo.data.dm.entities.ED_Path;
import de.lemo.data.dm.entities.ED_Person;

public class DataProviderHibernate implements IDataProvider{

	@Override
	public ED_LearningContext getLearningContext(Long id) {
		// TODO Auto-generated method stub
		return null;
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
	public List<ED_LearningActivity> getLearningActivities(Long person) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ED_LearningActivity> getLearningActivities(Long context,
			String action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ED_LearningActivity> getLearningActivities(Long context,
			Long person, List<Long> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ED_Path> getPaths(Long context) {
		// TODO Auto-generated method stub
		return null;
	}

}
