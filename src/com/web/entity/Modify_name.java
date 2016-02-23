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
@Table(name = "modify_name")
public class Modify_name{
 @Id
 @Basic(optional = false)
 @Column(name = "id")
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 @Column(name ="ewid")
 private String ewid;

 @Column(name ="dealercode")
 private String dealercode;

 @Column(name ="before_name")
 private String before_name;

 @Column(name ="after_name")
 private String after_name;

 @Column(name ="before_dossid")
 private String before_dossid;

 @Column(name ="after_dossid")
 private String after_dossid;

 @Column(name ="before_position")
 private String before_position;

 @Column(name ="after_position")
 private String after_position;

 @Column(name ="before_idcard")
 private String before_idcard;

 @Column(name ="after_idcard")
 private String after_idcard;

 @Column(name ="remark")
 private String remark;

 
 @Column(name ="apply_date")
 private Date apply_date;

 @Column(name ="approvedate")
 private Date approvedate;

 @Column(name="isapprove")
 private int isApprove;
 
 @Column(name="prove_img")
 private String prove_img;
 
 @Column (name="after_ewId")
 private String after_ewId;
 
 @Column (name="info_type")
 private int info_type;
 
 @Column (name = "exch_bonus_phone")
 private String ExBonusPhone;
 
 public void setId(int id){
   this.id=id;
 }

 public int getId(){
   return this.id;
 }


 public String getProve_img() {
	return prove_img;
}

public void setProve_img(String proveImg) {
	prove_img = proveImg;
}

public void setEwid(String ewid){
   this.ewid=ewid;
 }

 public String getEwid(){
   return this.ewid;
 }


 public void setDealercode(String dealercode){
   this.dealercode=dealercode;
 }

 public String getDealercode(){
   return this.dealercode;
 }


 public void setBefore_name(String before_name){
   this.before_name=before_name;
 }

 public String getBefore_name(){
   return this.before_name;
 }


 public void setAfter_name(String after_name){
   this.after_name=after_name;
 }

 public String getAfter_name(){
   return this.after_name;
 }


 public void setBefore_dossid(String before_dossid){
   this.before_dossid=before_dossid;
 }

 public String getBefore_dossid(){
   return this.before_dossid;
 }




 public String getAfter_dossid() {
	return after_dossid;
}

public void setAfter_dossid(String afterDossid) {
	after_dossid = afterDossid;
}

public void setBefore_position(String before_position){
   this.before_position=before_position;
 }

 public String getBefore_position(){
   return this.before_position;
 }


 public void setAfter_position(String after_position){
   this.after_position=after_position;
 }

 public String getAfter_position(){
   return this.after_position;
 }


 public void setBefore_idcard(String before_idcard){
   this.before_idcard=before_idcard;
 }

 public String getBefore_idcard(){
   return this.before_idcard;
 }


 public void setAfter_idcard(String after_idcard){
   this.after_idcard=after_idcard;
 }

 public String getAfter_idcard(){
   return this.after_idcard;
 }


 public void setApply_date(Date apply_date){
   this.apply_date=apply_date;
 }

 public Date getApply_date(){
   return this.apply_date;
 }



public String getRemark() {
	return remark;
}

public void setRemark(String remark) {
	this.remark = remark;
}

public Date getApprovedate() {
	return approvedate;
}

public void setApprovedate(Date approvedate) {
	this.approvedate = approvedate;
}

public int getIsApprove() {
	return isApprove;
}

public void setIsApprove(int isApprove) {
	this.isApprove = isApprove;
}

public String getAfter_ewId() {
	return after_ewId;
}

public void setAfter_ewId(String afterEwId) {
	after_ewId = afterEwId;
}

public int getInfo_type() {
	return info_type;
}

public void setInfo_type(int infoType) {
	info_type = infoType;
}

public String getExBonusPhone() {
	return ExBonusPhone;
}

public void setExBonusPhone(String exBonusPhone) {
	ExBonusPhone = exBonusPhone;
}
 
}