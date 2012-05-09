package org.whichplan.plan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.whichplan.call.Call;
import org.whichplan.plan.OiCartao;
import org.whichplan.plan.Plan;

import junit.framework.TestCase;


public class OiCartaoTest extends TestCase {

	double pricePerMinute = 1.43;
	int oneMinute = 60;
	int totalBonusDuration = oneMinute*2;
	
	public void testGetCostShouldReturnZeroWhenNoCallsCalculated() {
		double charge = 30;
		OiCartao oiCartao = new OiCartao(charge);
		assertEquals(0.0, oiCartao.getCost());
	}

	public void testGetTotalCostShouldReturnZeroWhenNoCallsCalculated() {
		double charge = 30;
		OiCartao oiCartao = new OiCartao(charge);
		assertEquals(charge, oiCartao.getTotalCost());
	}
	
	public void testCalculateShouldntSumCallCostsLessThanBonus() {
		List<Call> calls = new ArrayList<Call>();		
		calls.add(new Call(new Date(), oneMinute, "888888888", "Oi Móvel"));
		calls.add(new Call(new Date(), oneMinute, "888888128", "TIM"));
			
		OiCartao oiCartao = new OiCartao(pricePerMinute);
		calculateCalls(calls, oiCartao);

		assertEquals(pricePerMinute, oiCartao.getCost());
	}

	private void calculateCalls(List<Call> calls, Plan oiCartao) {
		for (Call call : calls) {
			oiCartao.calculate(call);
		}
	}
	
	public void testCalculateShouldntSumCallCostsEqualBonus() {
		List<Call> calls = new ArrayList<Call>();		
		calls.add(new Call(new Date(), totalBonusDuration, "888888888", "Oi Móvel"));
		calls.add(new Call(new Date(), oneMinute, "888888128", "TIM"));

		Plan oiCartao = new OiCartao(pricePerMinute);
		calculateCalls(calls, oiCartao);
		assertEquals(pricePerMinute, oiCartao.getCost());
	}
	
	public void testCalculateShouldSumCallCostsGreaterThanBonus() {
		List<Call> calls = new ArrayList<Call>();		
		calls.add(new Call(new Date(), totalBonusDuration, "888888888", "Oi Móvel"));
		calls.add(new Call(new Date(), 30, "888888888", "Oi Móvel"));
		calls.add(new Call(new Date(), 30, "888888128", "TIM"));

		Plan oiCartao = new OiCartao(pricePerMinute);
		calculateCalls(calls, oiCartao);
		assertEquals(pricePerMinute, oiCartao.getCost());
	}
	
	public void testGetTotalCostShouldReturnChargeValueWhenCostLessThanCharge() {
		double charge = pricePerMinute*3;
		OiCartao oiCartao = new OiCartao(charge);
		oiCartao.calculate(new Call(new Date(), oneMinute*2, "88888888", "TIM"));
		
		assertEquals(charge, oiCartao.getTotalCost());		
	}
	
	public void testGetTotalCostShouldReturnChargeValueWhenCostEqualsCharge() {
		double charge = pricePerMinute*3;
		OiCartao oiCartao = new OiCartao(charge);
		oiCartao.calculate(new Call(new Date(), oneMinute*3, "88888888", "TIM"));
		
		assertEquals(charge, oiCartao.getTotalCost());		
	}
	
	public void testGetTotalCostShouldReturnCostsWhenCostsGreaterThanCharge() {
		double charge = pricePerMinute*3;
		OiCartao oiCartao = new OiCartao(charge);
		oiCartao.calculate(new Call(new Date(), oneMinute*4, "88888888", "TIM"));
		
		assertEquals(charge+pricePerMinute, oiCartao.getTotalCost());		
	}
	
}

