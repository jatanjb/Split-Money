package com.splitmoney.model;

import javax.validation.constraints.NotBlank;

public class ExpanseDTO {

	private long id;
	
    private long oweID;
	
    private long lendID;
    
    private double amount;
	
    private String description;

    public ExpanseDTO() {
    }

    public ExpanseDTO(@NotBlank long id, @NotBlank long oweID, @NotBlank long lendID, @NotBlank double amount, @NotBlank String description) {
    	this.id = id;
    	this.oweID = oweID;
    	this.lendID = lendID;
        this.amount = amount;
        this.description = description;
    }
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOweID() {
		return oweID;
	}

	public void setOweID(long oweID) {
		this.oweID = oweID;
	}

	public long getLendID() {
		return lendID;
	}

	public void setLendID(long lendID) {
		this.lendID = lendID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ExpanseDTO [id=" + id + ", oweID=" + oweID + ", lendID=" + lendID + ", amount=" + amount
				+ ", description=" + description + "]";
	}
}
