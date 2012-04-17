package org.whichplan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Based on http://www.oi.com.br/ArquivosEstaticos/oi/docs/movel/Portif_Atual_Pre_Qualquer_Hora_%20R2.pdf
 */
public class OiCartao implements Plan {

	private static final int ONE_MINUTE_IN_SECONDS = 60;

	private static final double PRICE_PER_MINUTE = 1.43;

	private Map<Integer, Double> dailyBonus;
	
	private double charge = 20;
	
	public OiCartao() {
		this.dailyBonus = new HashMap<Integer, Double>();
	}
	
	@Override
	public double calculate(CallLog callLog) {
		List<Call> calls = callLog.getCalls();
		int durationSum = 0;
		for (Call call : calls) {
			if(isOi(call.getOperator())) {
				boolean bonusSuccess = deduceBonus(call);
				if(!bonusSuccess) {
					durationSum+=call.getDuration();	
				}
			} else {
				durationSum+=call.getDuration();
			}
		}
		
		return ((double)durationSum/ONE_MINUTE_IN_SECONDS)*PRICE_PER_MINUTE;
	}

	private boolean deduceBonus(Call call) {
		double callCost = ((double)call.getDuration()/ONE_MINUTE_IN_SECONDS)*PRICE_PER_MINUTE;
		Double bonus = this.dailyBonus.get(call.getDay());
		if(bonus == null) {
			bonus = this.charge*2;
		}
		bonus = bonus-callCost;
		this.dailyBonus.put(call.getDay(), bonus);
		return bonus >= 0;
	}

	private boolean isOi(String operator) {
		return operator != null && operator.startsWith("Oi");
	}

}
