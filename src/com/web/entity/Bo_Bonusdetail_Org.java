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
@Table(name = "bo_bonusdetail_org")
public class Bo_Bonusdetail_Org{
 @Column(name ="position")
 private String position;

 @Id
 @Basic(optional = false)
 @Column(name = "id")
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 @Column(name ="type")
 private int type;

 @Column(name ="price")
 private Double price;

 @Column(name ="taxprice")
 private Double taxprice;

 @Column(name ="realizeprice")
 private Double realizeprice;

 @Column(name ="ewid")
 private String ewid;

 @Column(name ="empid")
 private int empid;

 @Column(name ="reason")
 private String reason;

 @Column(name ="remark")
 private String remark;

 @Column(name ="status")
 private int status;

 @Column(name ="batch")
 private int batch;

 @Column(name ="createtime")
 private Date createtime;

 @Column(name ="updatetime")
 private Date updatetime;

 @Column(name ="dealercode")
 private String dealercode;

 @Column(name ="end_grantmoney")
 private String end_grantmoney;

 @Column(name ="ew_sales")
 private String ew_sales;

 @Column(name ="grantpercent")
 private String grantpercent;

 @Column(name ="grant_people_num")
 private String grant_people_num;

 @Column(name ="h1")
 private String h1;

 @Column(name ="people_num")
 private String people_num;

 @Column(name ="rank")
 private String rank;

 @Column(name ="salesnum")
 private String salesnum;

 @Column(name ="dealer")
 private String dealer;


 public void setPosition(String position){
   this.position=position;
 }

 public String getPosition(){
   return this.position;
 }


 public void setId(int id){
   this.id=id;
 }

 public int getId(){
   return this.id;
 }


 public void setType(int type){
   this.type=type;
 }

 public int getType(){
   return this.type;
 }


 public void setPrice(Double price){
   this.price=price;
 }

 public Double getPrice(){
   return this.price;
 }


 public void setTaxprice(Double taxprice){
   this.taxprice=taxprice;
 }

 public Double getTaxprice(){
   return this.taxprice;
 }


 public void setRealizeprice(Double realizeprice){
   this.realizeprice=realizeprice;
 }

 public Double getRealizeprice(){
   return this.realizeprice;
 }


 public void setEwid(String ewid){
   this.ewid=ewid;
 }

 public String getEwid(){
   return this.ewid;
 }


 public void setEmpid(int empid){
   this.empid=empid;
 }

 public int getEmpid(){
   return this.empid;
 }


 public void setReason(String reason){
   this.reason=reason;
 }

 public String getReason(){
   return this.reason;
 }


 public void setRemark(String remark){
   this.remark=remark;
 }

 public String getRemark(){
   return this.remark;
 }


 public void setStatus(int status){
   this.status=status;
 }

 public int getStatus(){
   return this.status;
 }


 public void setBatch(int batch){
   this.batch=batch;
 }

 public int getBatch(){
   return this.batch;
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


 public void setDealercode(String dealercode){
   this.dealercode=dealercode;
 }

 public String getDealercode(){
   return this.dealercode;
 }


 public void setEnd_grantmoney(String end_grantmoney){
   this.end_grantmoney=end_grantmoney;
 }

 public String getEnd_grantmoney(){
   return this.end_grantmoney;
 }


 public void setEw_sales(String ew_sales){
   this.ew_sales=ew_sales;
 }

 public String getEw_sales(){
   return this.ew_sales;
 }


 public void setGrantpercent(String grantpercent){
   this.grantpercent=grantpercent;
 }

 public String getGrantpercent(){
   return this.grantpercent;
 }


 public void setGrant_people_num(String grant_people_num){
   this.grant_people_num=grant_people_num;
 }

 public String getGrant_people_num(){
   return this.grant_people_num;
 }


 public void setH1(String h1){
   this.h1=h1;
 }

 public String getH1(){
   return this.h1;
 }


 public void setPeople_num(String people_num){
   this.people_num=people_num;
 }

 public String getPeople_num(){
   return this.people_num;
 }


 public void setRank(String rank){
   this.rank=rank;
 }

 public String getRank(){
   return this.rank;
 }


 public void setSalesnum(String salesnum){
   this.salesnum=salesnum;
 }

 public String getSalesnum(){
   return this.salesnum;
 }


 public void setDealer(String dealer){
   this.dealer=dealer;
 }

 public String getDealer(){
   return this.dealer;
 }

}