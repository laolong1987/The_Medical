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
@Table(name = "upimg")
public class Upimg{
 @Id
 @Basic(optional = false)
 @Column(name = "id")
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 @Column(name ="name")
 private String name;

 @Column(name ="imgpage")
 private String imgpage;

 @Column(name ="xhid")
 private int xhid;

 @Column(name ="brand")
 private String brand;

 @Column(name ="createtime")
 private Date createtime;

 @Column(name ="status")
 private int status;


 public void setId(int id){
   this.id=id;
 }

 public int getId(){
   return this.id;
 }


 public void setName(String name){
   this.name=name;
 }

 public String getName(){
   return this.name;
 }


 public void setImgpage(String imgpage){
   this.imgpage=imgpage;
 }

 public String getImgpage(){
   return this.imgpage;
 }


 public void setXhid(int xhid){
   this.xhid=xhid;
 }

 public int getXhid(){
   return this.xhid;
 }


 public void setBrand(String brand){
   this.brand=brand;
 }

 public String getBrand(){
   return this.brand;
 }


 public void setCreatetime(Date createtime){
   this.createtime=createtime;
 }

 public Date getCreatetime(){
   return this.createtime;
 }


 public void setStatus(int status){
   this.status=status;
 }

 public int getStatus(){
   return this.status;
 }

@Override
public String toString() {
	return "Upimg [id=" + id + ", name=" + name + ", imgpage=" + imgpage
			+ ", xhid=" + xhid + ", brand=" + brand + ", createtime="
			+ createtime + ", status=" + status + "]";
}

}