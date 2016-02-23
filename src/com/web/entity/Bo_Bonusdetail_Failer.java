package com.web.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bo_bonusdetail_failer")
public class Bo_Bonusdetail_Failer {
	@Id
	@Basic(optional = false)
	@Column(name= "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="bo_id")
	private int bo_id;
	
	@Column(name="batch")
	private int batch;
	
	@Column(name="ewid")
	private String ewid;
	
	@Column(name="service_fee")
	private int service_fee;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="failed_time")
	private Date failed_time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getBo_id() {
		return bo_id;
	}

	public void setBo_id(int boId) {
		bo_id = boId;
	}

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	public String getEwid() {
		return ewid;
	}

	public void setEwid(String ewid) {
		this.ewid = ewid;
	}

	public int getService_fee() {
		return service_fee;
	}

	public void setService_fee(int serviceFee) {
		service_fee = serviceFee;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}

	public Date getFailed_time() {
		return failed_time;
	}

	public void setFailed_time(Date failedTime) {
		failed_time = failedTime;
	}

}
