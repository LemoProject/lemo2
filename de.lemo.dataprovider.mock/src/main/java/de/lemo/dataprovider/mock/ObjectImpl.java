package de.lemo.dataprovider.mock;

import java.util.List;
import java.util.Set;

import de.lemo.dataprovider.api.*;

public class ObjectImpl implements LA_Object {
	
	String type = null;
	
	public ObjectImpl() {
		this.type="test";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LA_Object getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LA_Object> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public String getDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> extAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getExtAttribute(String attr) {
		// TODO Auto-generated method stub
		return null;
	}

}
