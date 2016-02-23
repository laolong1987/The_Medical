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
@Table(name = "Viwepagerdeta")
public class Viwepagerdeta{
 @Id
 @Basic(optional = false)
 @Column(name = "id")
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 @Column(name ="vid")
 private int vid;

 @Column(name ="imgpage")
 private String imgpage;

 @Column(name ="createtime")
 private Date createtime;

 @Column(name ="author")
 private String author;


 public void setId(int id){
   this.id=id;
 }

 public int getId(){
   return this.id;
 }


 public void setVid(int vid){
   this.vid=vid;
 }

 public int getVid(){
   return this.vid;
 }


 public void setImgpage(String imgpage){
   this.imgpage=imgpage;
 }

 public String getImgpage(){
   return this.imgpage;
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

@Override
public String toString() {
	return "Viwepagerdeta [id=" + id + ", vid=" + vid + ", imgpage=" + imgpage
			+ ", createtime=" + createtime + ", author=" + author + "]";
}

}