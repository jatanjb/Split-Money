package com.splitmoney.model;

import java.util.ArrayList;

public class GroupExpanseDTO {
	
    private String oweID;
	
    private long lendID;
    
    private float amount;
	
    private String description;

	

	public String getOweID() {
		return oweID;
	}

	public void setOweID(String oweID) {
		this.oweID = oweID;
	}

	public long getLendID() {
		return lendID;
	}

	public void setLendID(long lendID) {
		this.lendID = lendID;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

   
}
