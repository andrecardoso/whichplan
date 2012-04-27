package org.whichplan.plan;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.whichplan.call.Call;
import org.whichplan.call.CallLog;

public class BestPlanFinder {

	static List<Plan> implementedPlans = Arrays.asList(new Plan[]{new TimInfinityPre(), new OiCartao()});
	
	private CallLog callLog;
	
	public BestPlanFinder(List<Call> calls) {
		this.callLog = new CallLog(calls);
	}

	public List<Plan> calculatePlans() {
		Collections.sort(implementedPlans, new Comparator<Plan>() {

			@Override
			public int compare(Plan one, Plan other) {
				double costOne = one.calculate(callLog);
				double costOther = other.calculate(callLog);
				return Double.compare(costOne, costOther);
			}

		});
		return implementedPlans;
	}

}
