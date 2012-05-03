package org.whichplan.plan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.whichplan.call.Call;

import junit.framework.TestCase;

public class PlanAnalyserTest extends TestCase {

	public void testAnalysePlansShouldReturnAllImplementedPlans() {
		List<Call> calls = new ArrayList<Call>();		
		calls.add(new Call(new Date(), 30, "888888888", "Oi Móvel"));
		calls.add(new Call(new Date(), 30, "888888128", "TIM"));
		PlanAnalyser analyser = new PlanAnalyser(calls);
		
		List<Plan> plans = analyser.analysePlans();
		assertEquals(analyser.implementedPlans.size(), plans.size());	
	}
	
	public void testAnalysePlansShouldSortImplementedPlansByCost() {
		List<Call> calls = new ArrayList<Call>();		
		PlanAnalyser analyser = new PlanAnalyser(calls);

		analyser.implementedPlans = new ArrayList<Plan>();
		Plan expensive = new ExpensivePlan();
		Plan cheap = new CheapPlan();
		Plan best = new BestPlan();
		analyser.implementedPlans.add(expensive);
		analyser.implementedPlans.add(cheap);
		analyser.implementedPlans.add(best);
		
		List<Plan> plans = analyser.analysePlans();
		assertEquals(best, plans.get(0));
		assertEquals(cheap, plans.get(1));
		assertEquals(expensive, plans.get(2));
	}
	
	class ExpensivePlan implements Plan {
		@Override
		public double calculate(Call call) {
			return 0;
		}

		@Override
		public double getCost() {
			return 100;
		}
	}
	
	class CheapPlan implements Plan {
		@Override
		public double calculate(Call call) {
			return 0;
		}

		@Override
		public double getCost() {
			return 50;
		}
	}
	
	class BestPlan implements Plan {
		@Override
		public double calculate(Call call) {
			return 0;
		}

		@Override
		public double getCost() {
			return 10;
		}
	}

}
