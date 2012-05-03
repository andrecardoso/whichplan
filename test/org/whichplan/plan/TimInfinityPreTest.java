package org.whichplan.plan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.whichplan.call.Call;
import org.whichplan.plan.Plan;
import org.whichplan.plan.TimInfinityPre;

import junit.framework.TestCase;

public class TimInfinityPreTest extends TestCase {

	public void testCalculateLocalCallsBetweenTim() {
		List<Call> calls = new ArrayList<Call>();		
		calls.add(new Call(new Date(), 30, "888888888", "TIM"));
		calls.add(new Call(new Date(), 3666666, "888888128", "TIM"));
		calls.add(new Call(new Date(), 1000000000, "888888128", "TIM"));
		calls.add(new Call(new Date(), 3555550, "888888128", "TIM"));

		Plan timInfinity = new TimInfinityPre();
		calculateCalls(calls, timInfinity);
		// each TIM-TIM call costs 0.25
		assertEquals(0.25*calls.size(), timInfinity.getCost());
	}
	
	private void calculateCalls(List<Call> calls, Plan plan) {
		for (Call call : calls) {
			plan.calculate(call);
		}
	}
	
	public void testCalculateLocalCallsBetweenTimAndOthers() {
		List<Call> calls = new ArrayList<Call>();		
		calls.add(new Call(new Date(), 30, "888888888", "Oi Móvel"));
		calls.add(new Call(new Date(), 3666666, "888888128", "TIM"));
		calls.add(new Call(new Date(), 60, "888888128", "Claro"));
		calls.add(new Call(new Date(), 3555550, "888888128", "TIM"));

		Plan timInfinity = new TimInfinityPre();
		calculateCalls(calls, timInfinity);
		// each TIM-TIM call costs 0.25
		double expected = 0.25*2;
		// each TIM-Other call costs 1.39/min
		expected += ((60+30)/60)*1.39;
		assertEquals(expected, timInfinity.getCost());
	}
	
	public void testCalculateLocalCallsBetweenTimAndLandlines() {
		List<Call> calls = new ArrayList<Call>();		
		calls.add(new Call(new Date(), 30, "888888888", "Oi Fixo"));
		calls.add(new Call(new Date(), 60, "888888128", "GVT"));
		calls.add(new Call(new Date(), 3555550, "888888128", "Embratel"));

		Plan timInfinity = new TimInfinityPre();
		calculateCalls(calls, timInfinity);
		// each TIM-Landline call costs 0.50
		double expected = 0.50*calls.size();
		assertEquals(expected, timInfinity.getCost());
	}
}
