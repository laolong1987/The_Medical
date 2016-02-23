package com.web.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="distributor_bonus")
public class DistributorBonus {
	
	@Id
	@Basic(optional = false)
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column (name="dis_code")
	private String dis_code;
	
	@Column (name = "bonus_batch")
	private String bonus_batch;
	
	@Column (name = "bonus")
	private double bonus;
	
	@Column (name = "bonus_type")
	private int bonus_type;
	
	@Column (name = "status")
	private int status;
	
    @Column (name = "brand")
	private String brand;
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDis_code() {
		return dis_code;
	}

	public void setDis_code(String disCode) {
		dis_code = disCode;
	}

	public String getBonus_batch() {
		return bonus_batch;
	}

	public void setBonus_batch(String bonusBatch) {
		bonus_batch = bonusBatch;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	public int getBonus_type() {
		return bonus_type;
	}

	public void setBonus_type(int bonusType) {
		bonus_type = bonusType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	
}
