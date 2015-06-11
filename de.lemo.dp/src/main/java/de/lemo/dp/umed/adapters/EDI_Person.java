package de.lemo.dp.umed.adapters;

import de.lemo.dp.ED_Person;
import de.lemo.dp.umed.entities.Person;
import de.lemo.dp.umed.interfaces.IPerson;

public class EDI_Person extends Person implements ED_Person, IPerson {

	@Override
	public String getName() {
		return this.getName();
	}

}
