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
@Table(name = "clientquestion")
public class ClientQuestion {
	@Id
	@Basic(optional = false)
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;  //id
	
	@Column(name = "title")
	private String title;//标题
	
	@Column (name ="type")
	private Integer type;//类型
	
	@Column (name ="status")
	private Integer status;//状态
	
	@Column (name ="countent")
	private String countent;//内容
	
	@Column (name ="empid")
	private Integer empid;//提问人的id
	
	@Column (name ="createtime")
	private Date createtime;//创建时间
	
	@Column (name ="updatetime")
	private Date updatetime;//更新时间
	
	public ClientQuestion() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCountent() {
		return countent;
	}
	public void setCountent(String countent) {
		this.countent = countent;
	}
	public Integer getEmpid() {
		return empid;
	}
	public void setEmpid(Integer empid) {
		this.empid = empid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	@Override
	public String toString() {
		return "ClientQuestion [countent=" + countent + ", createtime="
				+ createtime + ", empid=" + empid + ", id=" + id + ", status="
				+ status + ", title=" + title + ", type=" + type
				+ ", updatetime=" + updatetime + "]";
	}
	
	
	
	
	
}
