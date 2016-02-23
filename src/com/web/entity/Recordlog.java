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
@Table(name = "recordlog")
public class Recordlog{


@Id
 @Basic(optional = false)
 @Column(name = "id")
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 @Column(name ="ewid")
 private String ewid;

 @Column(name ="username")
 private String username;

 @Column(name ="ggid")
 private int ggid;

 @Column(name ="name")
 private String name;

 @Column(name ="sysdate")
 private Date sysdate;


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


 public void setUsername(String username){
   this.username=username;
 }

 public String getUsername(){
   return this.username;
 }


 public void setGgid(int ggid){
   this.ggid=ggid;
 }

 public int getGgid(){
   return this.ggid;
 }


 public void setName(String name){
   this.name=name;
 }

 public String getName(){
   return this.name;
 }


 public void setSysdate(Date sysdate){
   this.sysdate=sysdate;
 }

 public Date getSysdate(){
   return this.sysdate;
 }
 @Override
	public String toString() {
		return "Recordlog [id=" + id + ", ewid=" + ewid + ", username="
				+ username + ", ggid=" + ggid + ", name=" + name + ", sysdate="
				+ sysdate + "]";
	}
}