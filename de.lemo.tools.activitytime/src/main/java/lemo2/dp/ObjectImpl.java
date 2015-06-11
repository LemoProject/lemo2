package lemo2.dp;

import java.util.List;

public class ObjectImpl implements ED_Object {
	
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
	public ED_Object getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ED_Object> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

}
