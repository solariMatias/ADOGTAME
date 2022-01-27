package com.example.demo.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "doggos")
public class Dog implements Serializable {

	private static final long serialVersionUID = -5706448234090067961L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	private int id;
	private Date birthday;
	@ManyToOne
	@JoinColumn(name = "BreedId")
	private Breed dogBreed;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Breed getDogBreed() {
		return dogBreed;
	}

	public void setDogBreed(Breed dogBreed) {
		this.dogBreed = dogBreed;
	}

	public Date getAge() {
		return birthday;
	}

	public void setAge(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "Dog [id=" + id + ", birthday=" + birthday + ", dogBreed=" + dogBreed + "]";
	}

}
