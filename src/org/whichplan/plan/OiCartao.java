package org.whichplan.plan;

import java.util.HashMap;
import java.util.Map;

import org.whichplan.call.Call;

/**
 * Based on http://www.oi.com.br/ArquivosEstaticos/oi/docs/movel/Portif_Atual_Pre_Qualquer_Hora_%20R2.pdf
 */
public class OiCartao implements Plan {

	private static final int ONE_MINUTE_IN_SECONDS = 60;

	private static final double PRICE_PER_MINUTE = 1.43;

	private Map<Integer, Double> dailyBonus;
	
	private double charge = 0;
	
	private int durationSum = 0;
	
	public OiCartao() {
		this.dailyBonus = new HashMap<Integer, Double>();
	}
	
	public OiCartao(double charge) {
		this();
		this.charge = charge;
	}
	
	@Override
	public double calculate(Call call) {
		if(isOi(call.getOperator())) {
			double bonus = deduceBonus(call);
			if(bonus < 0) {
				this.durationSum += call.getDuration();
			}
		} else {
			durationSum+=call.getDuration();
		}
		return this.getCost();
	}

	private double deduceBonus(Call call) {
		double callCost = ((double)call.getDuration()/ONE_MINUTE_IN_SECONDS)*PRICE_PER_MINUTE;
		Double bonus = this.dailyBonus.get(call.getDay());
		if(bonus == null) {
			bonus = this.charge*2;
		}
		bonus = bonus-callCost;
		this.dailyBonus.put(call.getDay(), bonus);
		return bonus;
	}

	private boolean isOi(String operator) {
		return operator != null && operator.startsWith("Oi");
	}
	
	@Override
	public double getCost() {
		double cost = ((double)durationSum/ONE_MINUTE_IN_SECONDS)*PRICE_PER_MINUTE;
		return cost > this.charge ? cost : this.charge;
	}

	@Override
	public String toString() {
		return "Oi Cartão [charge=" + charge
				+ ", cost=" + this.getCost() + "]";
	}
	
}
