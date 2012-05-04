package org.whichplan.operator;

public enum LandLineOperator {
	OI("Oi Fixo"), 
	EMBRATEL("Embratel"), 
	GVT("GVT");
	
	private String description;

	private LandLineOperator(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static boolean isLandLine(String operator) {
		for (LandLineOperator landLineOperator : LandLineOperator.values()) {
			if (landLineOperator.getDescription().equals(operator))
				return true;
		}
		return false;
	}
}
