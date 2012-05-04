package org.whichplan.plan;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.whichplan.call.Call;
import org.whichplan.call.CallLog;

public class PlanAnalyser {

	List<Plan> implementedPlans = Arrays.asList(new Plan[]{new TimInfinityPre(), new OiCartao(), new ClaroCartao()});
	
	private CallLog callLog;
	
	public PlanAnalyser(List<Call> calls) {
		this.callLog = new CallLog(calls);
	}

	public List<Plan> analysePlans() {
		calculatePlans();		
		sortByCost();
		return implementedPlans;
	}

	private void sortByCost() {
		Collections.sort(implementedPlans, new Comparator<Plan>() {
			@Override
			public int compare(Plan one, Plan other) {
				double costOne = one.getCost();
				double costOther = other.getCost();
				return Double.compare(costOne, costOther);
			}
		});
	}

	private void calculatePlans() {
		List<Call> calls = this.callLog.getCalls();
		for (Call call : calls) {
			for (Plan plan : implementedPlans) {
				plan.calculate(call);
			}
		}
	}

}
