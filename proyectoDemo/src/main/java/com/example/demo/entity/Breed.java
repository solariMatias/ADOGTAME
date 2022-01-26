package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "breedDog")
public class Breed implements Serializable {

	
	
	private static final long serialVersionUID = -2925004668626113286L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	private int BreedId;
	private String Breed;

	public int getId() {
		return BreedId;
	}

	public void setId(int id) {
		this.BreedId = id;
	}

	public String getBreedname() {
		return Breed;
	}

	public void setBreedname(String breedname) {
		this.Breed = breedname;
	}

	@Override
	public String toString() {
		return "Breed [id=" + BreedId + ", breedname=" + Breed + "]";
	}

}
