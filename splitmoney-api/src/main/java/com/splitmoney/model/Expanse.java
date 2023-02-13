package com.splitmoney.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "expanse")
public class Expanse {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private long id;
	
	@OneToOne()
    @JoinColumn(name = "oweID")
    private User oweID;
	
	@OneToOne()
    @JoinColumn(name = "lendID")
    private User lendID;
    
	@Column(name = "amount")
    private double amount;
	
	@Column(name = "description")
    private String description;

    public Expanse() {
    }

    public Expanse(@NotBlank long id, @NotBlank User oweID, @NotBlank User lendID, @NotBlank double amount, @NotBlank String description) {
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

	public User getOweID() {
		return oweID;
	}

	public void setOweID(User oweID) {
		this.oweID = oweID;
	}

	public User getLendID() {
		return lendID;
	}

	public void setLendID(User lendID) {
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
        return "{" +
                "id=" + id +
                ", oweID='" + oweID + '\'' +
                ", lendID='" + lendID + '\'' +
                ", amount='" + amount + '\'' +
                ", description=" + description +
                '}';
    }
}
