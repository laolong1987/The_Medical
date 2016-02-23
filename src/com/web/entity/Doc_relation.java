package com.web.entity;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "doc_relation")
public class Doc_relation{
 @Id
 @Basic(optional = false)
 @Column(name = "id")
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 @Column(name ="did")
 private int did;

 @Column(name ="name")
 private String name;

 @Column(name ="type")
 private int type;


 public void setId(int id){
   this.id=id;
 }

 public int getId(){
   return this.id;
 }


 public void setDid(int did){
   this.did=did;
 }

 public int getDid(){
   return this.did;
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

@Override
public String toString() {
	return "Doc_relation [id=" + id + ", did=" + did + ", name=" + name
			+ ", type=" + type + "]";
}

}