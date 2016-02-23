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
@Table(name = "secretquestion")
public class Secretquestion{
 @Id
 @Basic(optional = false)
 @Column(name = "id")
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 @Column(name ="ewid")
 private String ewid;
 
 @Column(name ="username")
 private String username;

 @Column(name ="secretquestion")
 private String secretquestion;

 @Column(name ="secretanwser")
 private String secretanwser;

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


 public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public void setSecretquestion(String secretquestion){
   this.secretquestion=secretquestion;
 }

 public String getSecretquestion(){
   return this.secretquestion;
 }


 public void setSecretanwser(String secretanwser){
   this.secretanwser=secretanwser;
 }

 public String getSecretanwser(){
   return this.secretanwser;
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