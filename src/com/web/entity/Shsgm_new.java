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
@Table(name = "shsgm_new")
public class Shsgm_new{
 @Id
 @Basic(optional = false)
 @Column(name = "id")
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 @Column(name ="batch")
 private String batch;

 @Column(name ="brand")
 private String brand;

 @Column(name ="dealercode")
 private String dealercode;

 @Column(name ="name")
 private String name;

 @Column(name ="salesnum")
 private int salesnum;

 @Column(name ="pname")
 private String pname;

 @Column(name ="position")
 private String position;

 @Column(name ="idcard")
 private String idcard;

 @Column(name ="phonenum")
 private String phonenum;

 @Column(name ="bankname")
 private String bankname;

 @Column(name ="openbank")
 private String openbank;

 @Column(name ="accountbank")
 private String accountbank;

 @Column(name ="remark")
 private String remark;

 @Column(name ="problem")
 private String problem;

 @Column(name ="dossid")
 private String dossid;

 @Column(name ="people_num")
 private String people_num;

 @Column(name ="ew_sales")
 private String ew_sales;

 @Column(name ="h1")
 private String h1;

 @Column(name ="grantpercent")
 private String grantpercent;

 @Column(name ="grantmoney")
 private String grantmoney;

 @Column(name ="grant_people_num")
 private String grant_people_num;

 @Column(name ="end_grantmoney")
 private String end_grantmoney;

 @Column(name ="state")
 private String state;

 @Column(name ="re_pname")
 private String re_pname;

 @Column(name ="createtime")
 private Date createtime;

 @Column(name ="updatetime")
 private Date updatetime;

 @Column(name ="alter_up")
 private String alter_up;

 @Column(name ="reason")
 private String reason;

 @Column(name ="type")
 private int type;

 @Column(name ="rank")
 private String rank;

 @Column(name ="newbatch")
 private String newbatch;


 public void setId(int id){
   this.id=id;
 }

 public int getId(){
   return this.id;
 }


 public void setBatch(String batch){
   this.batch=batch;
 }

 public String getBatch(){
   return this.batch;
 }


 public void setBrand(String brand){
   this.brand=brand;
 }

 public String getBrand(){
   return this.brand;
 }


 public void setDealercode(String dealercode){
   this.dealercode=dealercode;
 }

 public String getDealercode(){
   return this.dealercode;
 }


 public void setName(String name){
   this.name=name;
 }

 public String getName(){
   return this.name;
 }


 public void setSalesnum(int salesnum){
   this.salesnum=salesnum;
 }

 public int getSalesnum(){
   return this.salesnum;
 }


 public void setPname(String pname){
   this.pname=pname;
 }

 public String getPname(){
   return this.pname;
 }


 public void setPosition(String position){
   this.position=position;
 }

 public String getPosition(){
   return this.position;
 }


 public void setIdcard(String idcard){
   this.idcard=idcard;
 }

 public String getIdcard(){
   return this.idcard;
 }


 public void setPhonenum(String phonenum){
   this.phonenum=phonenum;
 }

 public String getPhonenum(){
   return this.phonenum;
 }


 public void setBankname(String bankname){
   this.bankname=bankname;
 }

 public String getBankname(){
   return this.bankname;
 }


 public void setOpenbank(String openbank){
   this.openbank=openbank;
 }

 public String getOpenbank(){
   return this.openbank;
 }


 public void setAccountbank(String accountbank){
   this.accountbank=accountbank;
 }

 public String getAccountbank(){
   return this.accountbank;
 }


 public void setRemark(String remark){
   this.remark=remark;
 }

 public String getRemark(){
   return this.remark;
 }


 public void setProblem(String problem){
   this.problem=problem;
 }

 public String getProblem(){
   return this.problem;
 }


 public void setDossid(String dossid){
   this.dossid=dossid;
 }

 public String getDossid(){
   return this.dossid;
 }


 public void setPeople_num(String people_num){
   this.people_num=people_num;
 }

 public String getPeople_num(){
   return this.people_num;
 }


 public void setEw_sales(String ew_sales){
   this.ew_sales=ew_sales;
 }

 public String getEw_sales(){
   return this.ew_sales;
 }


 public void setH1(String h1){
   this.h1=h1;
 }

 public String getH1(){
   return this.h1;
 }


 public void setGrantpercent(String grantpercent){
   this.grantpercent=grantpercent;
 }

 public String getGrantpercent(){
   return this.grantpercent;
 }


 public void setGrantmoney(String grantmoney){
   this.grantmoney=grantmoney;
 }

 public String getGrantmoney(){
   return this.grantmoney;
 }


 public void setGrant_people_num(String grant_people_num){
   this.grant_people_num=grant_people_num;
 }

 public String getGrant_people_num(){
   return this.grant_people_num;
 }


 public void setEnd_grantmoney(String end_grantmoney){
   this.end_grantmoney=end_grantmoney;
 }

 public String getEnd_grantmoney(){
   return this.end_grantmoney;
 }


 public void setState(String state){
   this.state=state;
 }

 public String getState(){
   return this.state;
 }


 public void setRe_pname(String re_pname){
   this.re_pname=re_pname;
 }

 public String getRe_pname(){
   return this.re_pname;
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


 public void setAlter_up(String alter_up){
   this.alter_up=alter_up;
 }

 public String getAlter_up(){
   return this.alter_up;
 }


 public void setReason(String reason){
   this.reason=reason;
 }

 public String getReason(){
   return this.reason;
 }


 public void setType(int type){
   this.type=type;
 }

 public int getType(){
   return this.type;
 }


 public void setRank(String rank){
   this.rank=rank;
 }

 public String getRank(){
   return this.rank;
 }


 public void setNewbatch(String newbatch){
   this.newbatch=newbatch;
 }

 public String getNewbatch(){
   return this.newbatch;
 }

}
