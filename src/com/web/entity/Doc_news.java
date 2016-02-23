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
@Table(name = "doc_news")
public class Doc_news{
 @Id
 @Basic(optional = false)
 @Column(name = "id")
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 @Column(name ="name")
 private String name;

 @Column(name ="type")
 private int type;

 @Column(name ="content")
 private String content;

 @Column(name ="status")
 private int status;

 @Column(name ="author")
 private String author;

 @Column(name ="createtime")
 private Date createtime;

 @Column(name ="utype")
 private int utype;

 @Column(name ="brand")
 private String brand;

 @Column(name ="sgmregion")
 private String sgmregion;

 @Column(name ="sfrregion")
 private String sfrregion;


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


 public void setContent(String content){
   this.content=content;
 }

 public String getContent(){
   return this.content;
 }


 public void setStatus(int status){
   this.status=status;
 }

 public int getStatus(){
   return this.status;
 }


 public void setAuthor(String author){
   this.author=author;
 }

 public String getAuthor(){
   return this.author;
 }


 public void setCreatetime(Date createtime){
   this.createtime=createtime;
 }

 public Date getCreatetime(){
   return this.createtime;
 }


 public void setUtype(int utype){
   this.utype=utype;
 }

 public int getUtype(){
   return this.utype;
 }


 public void setBrand(String brand){
   this.brand=brand;
 }

 public String getBrand(){
   return this.brand;
 }


 public void setSgmregion(String sgmregion){
   this.sgmregion=sgmregion;
 }

 public String getSgmregion(){
   return this.sgmregion;
 }


 public void setSfrregion(String sfrregion){
   this.sfrregion=sfrregion;
 }

 public String getSfrregion(){
   return this.sfrregion;
 }

@Override
public String toString() {
	return "Doc_news [id=" + id + ", name=" + name + ", type=" + type
			+ ", content=" + content + ", status=" + status + ", author="
			+ author + ", createtime=" + createtime + ", utype=" + utype
			+ ", brand=" + brand + ", sgmregion=" + sgmregion + ", sfrregion="
			+ sfrregion + "]";
}

}