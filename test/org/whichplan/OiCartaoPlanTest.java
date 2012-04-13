package org.whichplan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;


public class OiCartaoPlanTest extends TestCase {

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
