package org.whichplan.plan;


import org.whichplan.call.Call;

public class TimInfinityPre implements Plan {

	private static final int ONE_MINUTE_IN_SECONDS = 60;

	private static final double PRICE_PER_MINUTE = 1.39;
	
	private static String[] MOBILE_OPERATORS = {"TIM", "Oi Móvel", "Claro", "Vivo"};
	
	private double cost = 0;
	
	@Override
	public double calculate(Call call) {
		if(isTim(call.getOperator())) {
			this.cost += 0.25;
		} else if(isLandLine(call.getOperator())) {
			this.cost += 0.50;
		} else {
			this.cost += (call.getDuration()/ONE_MINUTE_IN_SECONDS)*PRICE_PER_MINUTE;
		}
		return this.cost;
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
	public double getCost() {
		return this.cost;
	}

	@Override
	public String toString() {
		return "TIM Infinity Pré [cost=" + cost + "]";
	}
}
