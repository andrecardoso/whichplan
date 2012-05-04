package org.whichplan.plan;

import org.whichplan.call.Call;
import org.whichplan.operator.LandLineOperator;
import org.whichplan.operator.MobileOperator;

/**
 * Based on http://vivosempre.clientes.ananke.com.br/Regulamento_Vivo_Sempre_PR_SC_VivoPre.pdf
 */
public class VivoSemprePre implements Plan {

	private static final int ONE_MINUTE_IN_SECONDS = 60;

	private static final double PRICE_PER_MINUTE = 1.35;

	private double charge = 12;
	
	private double cost = 0;
	
	@SuppressWarnings("rawtypes")
	private static Enum[] SAME_MOBILE_OPERATORS = { MobileOperator.VIVO };
	
	public VivoSemprePre() {
	}
	
	@Override
	public double calculate(Call call) {
		if (call.getOperator() == null) {
			this.cost += (call.getDuration() / ONE_MINUTE_IN_SECONDS)
					* PRICE_PER_MINUTE;
		} else if (isSameMobileOperator(call.getOperator())) {
			this.cost += (call.getDuration() / ONE_MINUTE_IN_SECONDS)
					* 0.05;
		} else if (LandLineOperator.isLandLine(call.getOperator())) {
			this.cost += (call.getDuration() / ONE_MINUTE_IN_SECONDS)
					* 0.25;
		} else {
			this.cost += (call.getDuration() / ONE_MINUTE_IN_SECONDS)
					* PRICE_PER_MINUTE;
		}
		return this.cost;
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

	public double getTotalCost() {
		if (this.cost > this.charge) {
			return this.cost;
		}
		return this.charge;
	}

	@Override
	public String toString() {
		return "VivoSempre [charge=" + charge + ", cost=" + cost + ", totalCost=" + this.getTotalCost() + "]";
	}

	
}
