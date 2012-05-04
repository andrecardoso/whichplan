package org.whichplan.operator;

public enum MobileOperator {
	TIM("TIM"), 
	CLARO("Claro"), 
	OI("Oi Móvel"),
	VIVO("Vivo"),
	NEXTEL("NEXTEL - VRD");
	
	private String description;

	private MobileOperator(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
}
