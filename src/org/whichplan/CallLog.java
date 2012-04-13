package org.whichplan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CallLog {

	private List<Call> calls;

	public CallLog(List<Call> calls) {
		this.calls = calls;
	}

	public List<Call> getCalls() {
		return this.calls;
	}

	public int sumDurations() {
		int durationSum = 0;
		for (Call call : calls) {
			durationSum += call.getDuration();
		}
		return durationSum;
	}

	public Map<String, Integer> sumDurationsByOperator() {
		Map<String, Integer> durationsByOperator = new HashMap<String, Integer>();
		for (Call call : calls) {
			Integer current = durationsByOperator.get(call.getOperator());
			if(current == null) {
				current = 0;
			}
			durationsByOperator.put(call.getOperator(), current+call.getDuration());
		}
		return durationsByOperator;
	}
	
}
