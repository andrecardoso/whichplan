package org.whichplan.call;

import java.util.Date;

public class Call {
	
	private Date time;
	private int duration;
	private String phoneNumber;
	private String phoneNumberLabel;
	
	public Call(Date time, int duration,
			String phoneNumber, String phoneNumberLabel) {
		super();
		this.time = time;
		this.duration = duration;
		this.phoneNumber = phoneNumber;
		this.phoneNumberLabel = phoneNumberLabel;
	}

	public int getDuration() {
		return duration;
	}
	
	@Override
	public String toString() {
		return "Call [time=" + time
				+ ", duration=" + duration + ", phoneNumber=" + phoneNumber
				+ ", phoneNumberLabel=" + phoneNumberLabel + "]";
	}

	public String getOperator() {
		return this.phoneNumberLabel;
	}

	public Integer getDay() {
		return this.time.getDay();
	}
	
}
