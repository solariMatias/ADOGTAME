package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Breed;
import com.example.demo.repository.BreedRepository;

@Service
public class BreedServiceImplements implements BreedService {
	@Autowired
	private BreedRepository breedRepository;

	@Override
	public List<Breed> listBreed() {
		
		return (List<Breed>) breedRepository.findAll();
	}

}
