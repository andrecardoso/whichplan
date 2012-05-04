package org.whichplan.plan;

import org.whichplan.call.Call;
import org.whichplan.operator.LandLineOperator;
import org.whichplan.operator.MobileOperator;

public class ClaroCartao implements Plan {

	private double cost = 0;

	private static final double ONE_MINUTE_IN_SECONDS = 60;
	private static final double PRICE_PER_MINUTE = 1.53;

	@SuppressWarnings("rawtypes")
	private static Enum[] SAME_MOBILE_OPERATORS = { MobileOperator.CLARO };
	
	@SuppressWarnings("rawtypes")
	private static Enum[] SAME_LANDLINE_OPERATORS = { LandLineOperator.EMBRATEL };

	@Override
	public double calculate(Call call) {
		if (call.getOperator() == null) {
			this.cost += (call.getDuration() / ONE_MINUTE_IN_SECONDS)
					* PRICE_PER_MINUTE;
		} else if (isSameMobileOperator(call.getOperator()) || isSameLandLineOperator(call.getOperator())) {
			this.cost += 0.21;
		} else if (LandLineOperator.isLandLine(call.getOperator())) {
			this.cost += 0.50;
		} else {
			this.cost += (call.getDuration() / ONE_MINUTE_IN_SECONDS)
					* PRICE_PER_MINUTE;
		}
		return this.cost;
	}

	@SuppressWarnings("rawtypes")
	private boolean isSameLandLineOperator(String operator) {
		for (Enum sameOperatorLandLine : SAME_LANDLINE_OPERATORS) {
			if (((LandLineOperator) sameOperatorLandLine).getDescription().equals(operator))
				return true;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	private boolean isSameMobileOperator(String operator) {
		for (Enum sameOperatorMobile : SAME_MOBILE_OPERATORS) {
			if (((MobileOperator) sameOperatorMobile).getDescription().equals(operator))
				return true;
		}

		return false;
	}

	@Override
	public double getCost() {
		return this.cost;
	}

	@Override
	public String toString() {
		return "ClaroCartao [cost=" + cost + "]";
	}

}
