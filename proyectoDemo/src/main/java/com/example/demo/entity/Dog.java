package com.example.demo.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "doggos")
public class Dog implements Serializable {

	private static final long serialVersionUID = -5706448234090067961L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
	private int id;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "birthday")
	@NotNull(message = "Ingrese una fecha válida")
	private LocalDate age;
	@ManyToOne
	@JoinColumn(name = "breed_id")
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

	public LocalDate getAge() {
		return age;
	}

	public void setAge(LocalDate birthday) {
		this.age = birthday;
	}

	public String getFormatedDate() {

		String formattedDate = age.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		return formattedDate;
	}

	@Override
	public String toString() {
		return "Dog [id=" + id + ", birthday=" + age + ", Breed=" + dogBreed + "]";
	}

}
