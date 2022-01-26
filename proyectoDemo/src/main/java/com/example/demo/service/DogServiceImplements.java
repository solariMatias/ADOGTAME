package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Dog;
import com.example.demo.repository.DogRepository;

@Service
public class DogServiceImplements implements DogService {
	
	@Autowired
	private DogRepository dogRepository;

	@Override
	public List<Dog> listAll() {
		return (List<Dog>) dogRepository.findAll();
	}

	@Override
	public void save(Dog dog) {
		dogRepository.save(dog);
	}

	@Override
	public Dog searchDogById(Long id) {
		return dogRepository.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		dogRepository.deleteById(id);
	}

}
