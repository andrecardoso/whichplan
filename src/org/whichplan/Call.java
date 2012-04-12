package org.whichplan;

import java.util.Date;

public class Call {
	
	private String contactName;
	private Date time;
	private int duration;
	private String phoneNumber;
	private String phoneNumberLabel;
	private CallType type;
	private int numberType;
	
	public Call(String contactName, Date time, int duration,
			String phoneNumber, String phoneNumberLabel, CallType type,
			int numberType) {
		super();
		this.contactName = contactName;
		this.time = time;
		this.duration = duration;
		this.phoneNumber = phoneNumber;
		this.phoneNumberLabel = phoneNumberLabel;
		this.type = type;
		this.numberType = numberType;
	}

	@Override
	public String toString() {
		return "Call [contactName=" + contactName + ", time=" + time
				+ ", duration=" + duration + ", phoneNumber=" + phoneNumber
				+ ", phoneNumberLabel=" + phoneNumberLabel + ", type=" + type
				+ ", numberType=" + numberType + "]";
	}
	
}
