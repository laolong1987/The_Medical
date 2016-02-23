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
@Table(name = "Viwepager")
public class Viwepager{
	
	 @Id
	 @Basic(optional = false)

	
 @Column(name ="vid")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)	
 private int vid;

 @Column(name ="name")
 private String name;

 @Column(name ="type")
 private int type;

 @Column(name ="status")
 private int status;

 @Column(name ="createtime")
 private Date createtime;

 @Column(name ="author")
 private String author;


 public void setVid(int vid){
   this.vid=vid;
 }

 public int getVid(){
   return this.vid;
 }


 public void setName(String name){
   this.name=name;
 }

 public String getName(){
   return this.name;
 }


 public void setType(int type){
   this.type=type;
 }

 public int getType(){
   return this.type;
 }


 public void setStatus(int status){
   this.status=status;
 }

 public int getStatus(){
   return this.status;
 }


 public void setCreatetime(Date createtime){
   this.createtime=createtime;
 }

 public Date getCreatetime(){
   return this.createtime;
 }


 public void setAuthor(String author){
   this.author=author;
 }

 public String getAuthor(){
   return this.author;
 }

}