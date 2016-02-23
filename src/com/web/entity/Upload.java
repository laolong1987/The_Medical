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
@Table(name = "upload")
public class Upload{
 @Id
 @Basic(optional = false)
 @Column(name = "id")
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 @Column(name ="name")
 private String name;

 @Column(name ="type")
 private int type;

 @Column(name ="route")
 private String route;

 @Column(name ="status")
 private int status;

 @Column(name ="releasetime")
 private Date releasetime;

 @Column(name ="author")
 private String author;

 @Column(name ="addname")
 private String addname;

// @Column(name ="brandregion")
// private int brandregion;
//
// @Column(name ="yanbaoregion")
// private int yanbaoregion;

 @Column(name ="brand")
 private String brand;


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


 public void setType(int type){
   this.type=type;
 }

 public int getType(){
   return this.type;
 }


 public void setRoute(String route){
   this.route=route;
 }

 public String getRoute(){
   return this.route;
 }


 public void setStatus(int status){
   this.status=status;
 }

 public int getStatus(){
   return this.status;
 }


 public void setReleasetime(Date releasetime){
   this.releasetime=releasetime;
 }

 public Date getReleasetime(){
   return this.releasetime;
 }


 public void setAuthor(String author){
   this.author=author;
 }

 public String getAuthor(){
   return this.author;
 }


 public void setAddname(String addname){
   this.addname=addname;
 }

 public String getAddname(){
   return this.addname;
 }

//
// public void setBrandregion(int brandregion){
//   this.brandregion=brandregion;
// }
//
// public int getBrandregion(){
//   return this.brandregion;
// }
//
//
// public void setYanbaoregion(int yanbaoregion){
//   this.yanbaoregion=yanbaoregion;
// }
//
// public int getYanbaoregion(){
//   return this.yanbaoregion;
// }


 public void setBrand(String brand){
   this.brand=brand;
 }

 public String getBrand(){
   return this.brand;
 }

@Override
public String toString() {
	return "Upload [id=" + id + ", name=" + name + ", type=" + type
			+ ", route=" + route + ", status=" + status + ", releasetime="
			+ releasetime + ", author=" + author + ", addname=" + addname
			+ ", brand=" + brand + "]";
}

}