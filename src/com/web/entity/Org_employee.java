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
@Table(name = "org_employee")
public class Org_employee{
 @Id
 @Basic(optional = false)
 @Column(name = "id")
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 @Column(name ="brand")
 private String brand;

 @Column(name ="ewid")
 private String ewid;

 @Column(name ="username")
 private String username;

 @Column(name ="pwd")
 private String pwd;

 @Column(name ="sex")
 private String sex;

 @Column(name ="birthday")
 private Date birthday;

 @Column(name ="degree")
 private String degree;

 @Column(name ="bankname")
 private String bankname;

 @Column(name ="openbank")
 private String openbank;

 @Column(name ="accountbank")
 private String accountbank;

 @Column(name ="description")
 private String description;

 @Column(name ="inservice")
 private String inservice;

 @Column(name ="member_id")
 private String member_id;

 @Column(name ="head_img")
 private String head_img;

 @Column(name ="card_img")
 private String card_img;

 @Column(name ="age")
 private int age;

 @Column(name ="name")
 private String name;

 @Column(name ="idcard")
 private String idcard;

 @Column(name ="doss_id")
 private String doss_id;

 @Column(name ="dealer")
 private String dealer;

 @Column(name ="dealercode")
 private String dealercode;

 @Column(name ="region")
 private String region;

 @Column(name ="phone")
 private String phone;

 @Column(name ="position")
 private String position;

 @Column(name ="address")
 private String address;

 @Column(name ="mail")
 private String mail;

 @Column(name ="isactived")
 private int isactived;

 @Column(name ="isregist")
 private int isregist;

 @Column(name ="ispass")
 private int ispass;

 @Column(name ="license")
 private String license;

 @Column(name ="get_license_time")
 private Date get_license_time;

 @Column(name ="first_service_time")
 private Date first_service_time;

 @Column(name ="istrained")
 private String istrained;

 @Column(name ="cpat")
 private Date cpat;

 @Column(name ="caet")
 private Date caet;

 @Column(name ="pass_train_time")
 private Date pass_train_time;

 @Column(name ="past")
 private Date past;

 @Column(name ="dimission_time")
 private Date dimission_time;

 @Column(name ="emp_id")
 private String emp_id;

 @Column(name ="createtime")
 private Date createtime;

 @Column(name ="updatetime")
 private Date updatetime;

 @Column(name ="first_service_brand")
 private String first_service_brand;

 @Column(name ="lms_date")
 private String lms_date;

 @Column(name ="sgmid")
 private String sgmid;

 @Column(name ="sgmpwd")
 private String sgmpwd;

 @Column(name ="identityimg")
 private String identityimg;
 
 @Column(name ="role")
 private String role;



 public void setId(int id){
   this.id=id;
 }

 public int getId(){
   return this.id;
 }


 public void setBrand(String brand){
   this.brand=brand;
 }

 public String getBrand(){
   return this.brand;
 }


 public void setEwid(String ewid){
   this.ewid=ewid;
 }

 public String getEwid(){
   return this.ewid;
 }


 public void setUsername(String username){
   this.username=username;
 }

 public String getUsername(){
   return this.username;
 }


 public void setPwd(String pwd){
   this.pwd=pwd;
 }

 public String getPwd(){
   return this.pwd;
 }


 public void setSex(String sex){
   this.sex=sex;
 }

 public String getSex(){
   return this.sex;
 }


 public void setBirthday(Date birthday){
   this.birthday=birthday;
 }

 public Date getBirthday(){
   return this.birthday;
 }


 public void setDegree(String degree){
   this.degree=degree;
 }

 public String getDegree(){
   return this.degree;
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


 public void setDescription(String description){
   this.description=description;
 }

 public String getDescription(){
   return this.description;
 }


 public void setInservice(String inservice){
   this.inservice=inservice;
 }

 public String getInservice(){
   return this.inservice;
 }


 public void setMember_id(String member_id){
   this.member_id=member_id;
 }

 public String getMember_id(){
   return this.member_id;
 }


 public void setHead_img(String head_img){
   this.head_img=head_img;
 }

 public String getHead_img(){
   return this.head_img;
 }


 public void setCard_img(String card_img){
   this.card_img=card_img;
 }

 public String getCard_img(){
   return this.card_img;
 }


 public void setAge(int age){
   this.age=age;
 }

 public int getAge(){
   return this.age;
 }


 public void setName(String name){
   this.name=name;
 }

 public String getName(){
   return this.name;
 }


 public void setIdcard(String idcard){
   this.idcard=idcard;
 }

 public String getIdcard(){
   return this.idcard;
 }


 public void setDoss_id(String doss_id){
   this.doss_id=doss_id;
 }

 public String getDoss_id(){
   return this.doss_id;
 }


 public void setDealer(String dealer){
   this.dealer=dealer;
 }

 public String getDealer(){
   return this.dealer;
 }


 public void setDealercode(String dealercode){
   this.dealercode=dealercode;
 }

 public String getDealercode(){
   return this.dealercode;
 }


 public void setRegion(String region){
   this.region=region;
 }

 public String getRegion(){
   return this.region;
 }


 public void setPhone(String phone){
   this.phone=phone;
 }

 public String getPhone(){
   return this.phone;
 }


 public void setPosition(String position){
   this.position=position;
 }

 public String getPosition(){
   return this.position;
 }


 public void setAddress(String address){
   this.address=address;
 }

 public String getAddress(){
   return this.address;
 }


 public void setMail(String mail){
   this.mail=mail;
 }

 public String getMail(){
   return this.mail;
 }


 public void setIsactived(int isactived){
   this.isactived=isactived;
 }

 public int getIsactived(){
   return this.isactived;
 }


 public void setIsregist(int isregist){
   this.isregist=isregist;
 }

 public int getIsregist(){
   return this.isregist;
 }


 public void setIspass(int ispass){
   this.ispass=ispass;
 }

 public int getIspass(){
   return this.ispass;
 }


 public void setLicense(String license){
   this.license=license;
 }

 public String getLicense(){
   return this.license;
 }


 public void setGet_license_time(Date get_license_time){
   this.get_license_time=get_license_time;
 }

 public Date getGet_license_time(){
   return this.get_license_time;
 }


 public void setFirst_service_time(Date first_service_time){
   this.first_service_time=first_service_time;
 }

 public Date getFirst_service_time(){
   return this.first_service_time;
 }


 public void setIstrained(String istrained){
   this.istrained=istrained;
 }

 public String getIstrained(){
   return this.istrained;
 }


 public void setCpat(Date cpat){
   this.cpat=cpat;
 }

 public Date getCpat(){
   return this.cpat;
 }


 public void setCaet(Date caet){
   this.caet=caet;
 }

 public Date getCaet(){
   return this.caet;
 }


 public void setPass_train_time(Date pass_train_time){
   this.pass_train_time=pass_train_time;
 }

 public Date getPass_train_time(){
   return this.pass_train_time;
 }


 public void setPast(Date past){
   this.past=past;
 }

 public Date getPast(){
   return this.past;
 }


 public void setDimission_time(Date dimission_time){
   this.dimission_time=dimission_time;
 }

 public Date getDimission_time(){
   return this.dimission_time;
 }


 public void setEmp_id(String emp_id){
   this.emp_id=emp_id;
 }

 public String getEmp_id(){
   return this.emp_id;
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


 public void setFirst_service_brand(String first_service_brand){
   this.first_service_brand=first_service_brand;
 }

 public String getFirst_service_brand(){
   return this.first_service_brand;
 }


 public void setLms_date(String lms_date){
   this.lms_date=lms_date;
 }

 public String getLms_date(){
   return this.lms_date;
 }


 public void setSgmid(String sgmid){
   this.sgmid=sgmid;
 }

 public String getSgmid(){
   return this.sgmid;
 }


 public void setSgmpwd(String sgmpwd){
   this.sgmpwd=sgmpwd;
 }

 public String getSgmpwd(){
   return this.sgmpwd;
 }


 public void setIdentityimg(String identityimg){
   this.identityimg=identityimg;
 }

 public String getIdentityimg(){
   return this.identityimg;
 }

public String getRole() {
	return role;
}

public void setRole(String role) {
	this.role = role;
}

}