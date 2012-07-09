package de.lemo.dms.connectors.clix2010.clixDBClass;

public class EComposing {
	
	private Long id;
	private Long composing;
	private Long component;
	private String endDate;
	private String startDate;
	private Long composingType;
	
	public Long getComposingType() {
		return composingType;
	}

	public void setComposingType(Long composingType) {
		this.composingType = composingType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public EComposing()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getComposing() {
		return composing;
	}

	public void setComposing(Long composing) {
		this.composing = composing;
	}

	public Long getComponent() {
		return component;
	}

	public void setComponent(Long component) {
		this.component = component;
	}
	
	

}
