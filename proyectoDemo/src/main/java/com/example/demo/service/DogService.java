package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Dog;

public interface DogService {

	public List<Dog> listAll();

	public void save(Dog dog);

	public Dog searchDogById(Long id);

	public void delete(Long id);
}
