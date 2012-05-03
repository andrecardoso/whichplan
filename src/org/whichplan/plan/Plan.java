package org.whichplan.plan;

import org.whichplan.call.Call;


public interface Plan {

	double calculate(Call call);

	double getCost();
}
