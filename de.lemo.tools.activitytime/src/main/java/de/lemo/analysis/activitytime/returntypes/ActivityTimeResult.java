package de.lemo.analysis.activitytime.returntypes;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ActivityTimeResult {
	
	ActivityTimeList data[] = null;
	
	public ActivityTimeResult() {
		// TODO Auto-generated constructor stub
	}
	
	public ActivityTimeResult(ResultListHashMapObject resultListHashMap,long startDate,double intervall) {
		List<ResultListLongObject> resultList = resultListHashMap.getEntries();
		List<Long> result = resultList.get(0).getElements();
		int resultSize = result.size()/2;
		this.data = new ActivityTimeList[2];
		this.data[0] = new ActivityTimeList(result.subList(0, resultSize),"Course 1", startDate, intervall);
		this.data[1] = new ActivityTimeList(result.subList(resultSize, result.size()),"Course 1 User", startDate, intervall);
	}
	
	@XmlElement
	public ActivityTimeList[] getData(){
		return this.data;
	}
}
