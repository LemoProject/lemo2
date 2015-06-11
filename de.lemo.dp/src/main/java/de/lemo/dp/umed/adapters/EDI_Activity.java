package de.lemo.dp.umed.adapters;

import de.lemo.dp.ED_Activity;
import de.lemo.dp.ED_Context;
import de.lemo.dp.ED_Object;
import de.lemo.dp.ED_Person;
import de.lemo.dp.umed.entities.LearningActivity;
import de.lemo.dp.umed.interfaces.IActivity;

public class EDI_Activity extends LearningActivity implements ED_Activity, IActivity{

	
	public ED_Person getAPerson() {
		
		return (EDI_Person)this.getPerson();
	}

	@Override
	public ED_Context getContext() {
		return (EDI_Context) this.getContext();
	}

	@Override
	public ED_Object getObject() {
		return (EDI_Object)this.getLearningObject();
	}

	@Override
	public Long getTime() {
		return this.getTime();
	}

	@Override
	public String getAction() {
		return this.getAction();
	}

	@Override
	public String getInfo() {
		return this.getInfo();
	}

	@Override
	public ED_Activity getRef() {
		return (EDI_Activity)this.getReference();
	}


}
