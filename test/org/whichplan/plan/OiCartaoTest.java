package org.whichplan.plan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.whichplan.call.Call;
import org.whichplan.call.CallLog;
import org.whichplan.plan.OiCartao;
import org.whichplan.plan.Plan;

import junit.framework.TestCase;


public class OiCartaoTest extends TestCase {

	public void testCalculateShouldntSumCallCostsLessThanBonus() {
		List<Call> calls = new ArrayList<Call>();		
		calls.add(new Call(new Date(), 30, "888888888", "Oi Móvel"));
		calls.add(new Call(new Date(), 30, "888888128", "TIM"));

		Plan oiCartao = new OiCartao();
		double moneySpent = oiCartao.calculate(new CallLog(calls));
		// It should return 1.43/2 because Oi Móvel deduces bonus
		assertEquals(1.43/2, moneySpent);
	}
	
	public void testCalculateShouldntSumCallCostsEqualBonus() {
		List<Call> calls = new ArrayList<Call>();		
		calls.add(new Call(new Date(), 1678, "888888888", "Oi Móvel"));
		calls.add(new Call(new Date(), 30, "888888128", "TIM"));

		Plan oiCartao = new OiCartao();
		double moneySpent = oiCartao.calculate(new CallLog(calls));
		// It should return 1.43/2 because Oi Móvel deduces bonus
		assertEquals(1.43/2, moneySpent);
	}
	
	public void testCalculateShouldSumCallCostsGreaterThanBonus() {
		List<Call> calls = new ArrayList<Call>();		
		calls.add(new Call(new Date(), 1678, "888888888", "Oi Móvel"));
		calls.add(new Call(new Date(), 30, "888888888", "Oi Móvel"));
		calls.add(new Call(new Date(), 30, "888888128", "TIM"));

		Plan oiCartao = new OiCartao();
		double moneySpent = oiCartao.calculate(new CallLog(calls));
		assertEquals(1.43, moneySpent);
	}
	
}
