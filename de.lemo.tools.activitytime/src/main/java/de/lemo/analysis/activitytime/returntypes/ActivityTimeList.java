package de.lemo.analysis.activitytime.returntypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ActivityTimeList{
		
	private ArrayList<long[]> values = new ArrayList<long[]>();
	private String key = "";
	
	public ActivityTimeList(){
		
	}
	
	public ActivityTimeList(List<Long> list, String key) {
		this.key = key;
		long[] valuePair = null;
		for(Long longObject : list){
			valuePair = new long[2];
			valuePair[0] =  0L;
			valuePair[1] =  longObject;
			this.values.add(valuePair);
		}
	}
	
	@XmlElement
	public ArrayList<long[]> getvalues(){
		this.values.removeAll(Collections.singleton(null));
		return this.values;
	}
	
	@XmlElement
	public String getKey(){
		return this.key;
	}

	
}
