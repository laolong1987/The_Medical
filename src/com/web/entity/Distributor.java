package com.web.entity;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table (name = "distributor")
public class Distributor {
	@Id
	@Basic(optional = false)
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "region")
	private String region;
	
	@Column(name="status")
	private int status;
	
	@Column(name = "name")
	private String name;
	
	@Column (name = "brand")
	private String brand;
	
	@Column (name = "unavailable_date")
	private String unavailable_date;
	
	@Column (name = "create_date")
	private String createDate;
	
	@Column (name = "update_date")
	private String updateDate;
	
	@Column (name = "available_date")
	private String availableDate;
	
	//private Set<Org_employee> org_employee;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getUnavailable_date() {
		return unavailable_date;
	}
	public void setUnavailable_date(String unavailableDate) {
		unavailable_date = unavailableDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getAvailableDate() {
		return availableDate;
	}
	public void setAvailableDate(String availableDate) {
		this.availableDate = availableDate;
	}		
}
