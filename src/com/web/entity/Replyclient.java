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
@Table(name = "replyclient")
public class Replyclient{
 @Id
 @Basic(optional = false)
 @Column(name = "id")
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 @Column(name ="hfid")
 private int hfid;

 @Column(name ="repcountent")
 private String repcountent;

 @Column(name ="empid")
 private int empid;

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


 public void setHfid(int hfid){
   this.hfid=hfid;
 }

 public int  getHfid(){
   return this.hfid;
 }


 public void setRepcountent(String repcountent){
   this.repcountent=repcountent;
 }

 public String getRepcountent(){
   return this.repcountent;
 }


 public void setEmpid(int empid){
   this.empid=empid;
 }

 public int getEmpid(){
   return this.empid;
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

}