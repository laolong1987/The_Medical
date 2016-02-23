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
@Table(name = "apply_role")
public class Apply_role{
 @Id
 @Basic(optional = false)
 @Column(name = "id")
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 @Column(name ="ewid")
 private String ewid;

 @Column(name ="dealercode")
 private String dealercode;

 @Column(name ="dealer")
 private String dealer;

 @Column(name ="name")
 private String name;

 @Column(name ="position")
 private String position;

 @Column(name ="isapprove")
 private String isapprove;
 
 @Column(name ="remark")
 private String remark;
 
 @Column(name ="prove_img")
 private String prove_img;
 

 @Column(name ="createtime")
 private Date createtime;

 @Column(name ="updatetime")
 private Date updatetime;


 public void setId(int id){
   this.id=id;
 }

 public int getId(){
   return this.id;
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


 public void setDealer(String dealer){
   this.dealer=dealer;
 }

 public String getDealer(){
   return this.dealer;
 }


 public void setName(String name){
   this.name=name;
 }

 public String getName(){
   return this.name;
 }


 public void setPosition(String position){
   this.position=position;
 }

 public String getPosition(){
   return this.position;
 }


 public void setIsapprove(String isapprove){
   this.isapprove=isapprove;
 }

 public String getIsapprove(){
   return this.isapprove;
 }


 public void setCreatetime(Date createtime){
   this.createtime=createtime;
 }

 public Date getCreatetime(){
   return this.createtime;
 }


 public void setUpdatetime(Date updatetime){
   this.updatetime=updatetime;
 }

 public Date getUpdatetime(){
   return this.updatetime;
 }

public String getProve_img() {
	return prove_img;
}

public void setProve_img(String proveImg) {
	prove_img = proveImg;
}

public String getRemark() {
	return remark;
}

public void setRemark(String remark) {
	this.remark = remark;
}


 
 

}