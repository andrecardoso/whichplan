package org.whichplan.plan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.whichplan.call.Call;
import org.whichplan.call.CallLog;

import junit.framework.TestCase;

public class BestPlanFinderTest extends TestCase {

	public void testCalculatePlansShouldReturnAllImplementedPlans() {
		List<Call> calls = new ArrayList<Call>();		
		calls.add(new Call(new Date(), 30, "888888888", "Oi Móvel"));
		calls.add(new Call(new Date(), 30, "888888128", "TIM"));
		BestPlanFinder finder = new BestPlanFinder(calls);
		
		List<Plan> plans = finder.calculatePlans();
		assertEquals(BestPlanFinder.implementedPlans.size(), plans.size());	
	}
	
	public void testCalculatePlansShouldSortImplementedPlansByCost() {
		List<Call> calls = new ArrayList<Call>();		
		BestPlanFinder finder = new BestPlanFinder(calls);

		BestPlanFinder.implementedPlans = new ArrayList<Plan>();
		Plan expensive = new ExpensivePlan();
		Plan cheap = new CheapPlan();
		Plan best = new BestPlan();
		BestPlanFinder.implementedPlans.add(expensive);
		BestPlanFinder.implementedPlans.add(cheap);
		BestPlanFinder.implementedPlans.add(best);
		
		List<Plan> plans = finder.calculatePlans();
		assertEquals(best, plans.get(0));
		assertEquals(cheap, plans.get(1));
		assertEquals(expensive, plans.get(2));
	}
	
	class ExpensivePlan implements Plan {
		@Override
		public double calculate(CallLog callLog) {
			return 100;
		}
	}
	
	class CheapPlan implements Plan {
		@Override
		public double calculate(CallLog callLog) {
			return 50;
		}
	}
	
	class BestPlan implements Plan {
		@Override
		public double calculate(CallLog callLog) {
			return 10;
		}
	}

}
