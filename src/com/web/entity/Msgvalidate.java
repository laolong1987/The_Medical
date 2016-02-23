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
@Table(name = "msgvalidate")
public class Msgvalidate{
 @Id
 @Basic(optional = false)
 @Column(name = "id")
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 @Column(name ="ewid")
 private String ewid;

 @Column(name ="msgcode")
 private String msgcode;

 @Column(name ="timstamp")
 private  Date timstamp;

 @Column(name ="createtime")
 private Date createtime;


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


 public void setMsgcode(String msgcode){
   this.msgcode=msgcode;
 }

 public String getMsgcode(){
   return this.msgcode;
 }


 public void setTimstamp( Date timstamp){
   this.timstamp=timstamp;
 }

 public Date getTimstamp(){
   return this.timstamp;
 }


 public void setCreatetime(Date createtime){
   this.createtime=createtime;
 }

 public Date getCreatetime(){
   return this.createtime;
 }

}
