package org.whichplan.plan;

import java.util.List;

import org.whichplan.call.Call;
import org.whichplan.call.CallLog;

public class TimInfinityPre implements Plan {

	private static final int ONE_MINUTE_IN_SECONDS = 60;

	private static final double PRICE_PER_MINUTE = 1.39;
	
	private static String[] MOBILE_OPERATORS = {"TIM", "Oi Móvel", "Claro", "Vivo"};
	
	@Override
	public double calculate(CallLog callLog) {
		List<Call> calls = callLog.getCalls();
		double moneySpent = 0;
		for (Call call : calls) {
			if(isTim(call.getOperator())) {
				moneySpent += 0.25;
			} else if(isLandLine(call.getOperator())) {
				moneySpent += 0.50;
			} else {
				moneySpent += (call.getDuration()/ONE_MINUTE_IN_SECONDS)*PRICE_PER_MINUTE;
			}
		}
		
		return moneySpent;
	}

	private boolean isLandLine(String operator) {
		for (String mobileOperator : MOBILE_OPERATORS) {
			if(mobileOperator.equals(operator))
				return false;
		}
		return true;
	}

	private boolean isTim(String operator) {
		return "TIM".equals(operator);
	}

	@Override
	public String toString() {
		return "TIM Infinity Pré";
	}
}
